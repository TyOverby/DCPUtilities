package com.prealpha.dcputil.emulator;

import com.prealpha.dcputil.emulator.devices.Device;
import com.prealpha.dcputil.emulator.devices.DeviceManager;

import java.util.*;

import static com.prealpha.dcputil.emulator.EmulatorHelper.*;
import static com.prealpha.dcputil.emulator.Machine.PointerType.*;
import static com.prealpha.dcputil.emulator.Opcodes.*;
import static com.prealpha.dcputil.emulator.Valuecodes.*;
import static com.prealpha.dcputil.util.PrintUtilities.convertHex;

/**
 * User: Ty
 * Date: 7/19/12
 * Time: 11:46 AM
 */
public class Machine {
    public static enum PointerType{
        POINTER_MEMORY,
        POINTER_REGISTER,
        POINTER_PC,
        POINTER_SP,
        POINTER_EX,
        POINTER_LITERAL,
    }
    private class Pointer {
        private final PointerType type;
        private final char pointer;

        public Pointer(PointerType type){
            this.type    = type;
            this.pointer = 0xffff;
        }
        public Pointer(PointerType type, char pointerValue){
            this.type = type;
            this.pointer = pointerValue;
        }

        public char get(){
            switch(type){
                case POINTER_MEMORY:
                    return memory[pointer];
                case POINTER_REGISTER:
                    return registers[pointer];
                case POINTER_PC:
                    return pc;
                case POINTER_SP:
                    return sp;
                case POINTER_EX:
                    return ex;
                case POINTER_LITERAL:
                    return pointer;
                default:
                    return 0xffff;
            }
        }

        public void set(char data){
            switch(type){
                case POINTER_MEMORY:
                    memory[pointer]   = data;
                    modified[pointer] = true;
                    modifiedSet.add(pointer);
                    justModified.add(pointer);
                    break;
                case POINTER_REGISTER:
                    registers[pointer] = data;
                    break;
                case POINTER_PC:
                    pc = data;
                    break;
                case POINTER_SP:
                    sp = data;
                    break;
                case POINTER_EX:
                    ex = data;
                    break;
                case POINTER_LITERAL:
                    // Silently fail...
            }
        }
    }

    protected boolean isRunning = false;

    private static final char A_SIZE  = 6;
    private static final char B_SIZE  = 5;
    private static final char OP_SIZE = 5;

    protected char[] registers = new char[0x07+1];
    protected char   sp = 0x0;
    protected char   pc = 0x0;
    protected char   ex = 0x0;

    protected char[] lastProgram;
    protected char[] memory = new char[0xffff+1];

    private DeviceManager deviceManager = new DeviceManager();

    protected boolean[] modified = new boolean[memory.length];

    protected Set<Character> justModified = new HashSet<Character>();
    protected Set<Character> modifiedSet = new HashSet<Character>();

    private void reset(){
        for(Character c: modifiedSet){
            modified[c] = false;
        }
        modifiedSet.clear();
        for(int i=0;i<memory.length;i++){
            memory[i] = 0;
        }
        for(int i=0;i<registers.length;i++){
            registers[i] = 0;
        }
        sp = 0;
        pc = 0;
        ex = 0;
    }
    public void load(char[] program){
        reset();
        System.arraycopy(program, 0, memory, 0, program.length);
        lastProgram = program.clone();
        for(char i=0;i<program.length;i++){
            modified[i] = true;
            modifiedSet.add(i);
        }
    }
    public void step() throws EmulatorException {
        this.justModified.clear();
        instruction();
        this.deviceManager.update(this);
    }
    private void instruction() throws EmulatorException {
        char instruction = memory[pc++];
        char opcode   = clear(instruction, A_SIZE+B_SIZE, 0);
        char opA      = clear(instruction, 0, B_SIZE+OP_SIZE);
        char opB      = clear(instruction, A_SIZE, B_SIZE);

        int offset = 0;
        Pointer pa = getPointer(opA, offset, true);
        offset+= getOffset(opA);
        Pointer pb = getPointer(opB, offset, false);
        if(opcode!=0)
            offset+= getOffset(opB);

        pc += offset;


        char a = pa.get();
        char b = pb.get();


        short shortB = (short) b;
        short shortA = (short) a;

        switch (opcode){
            case SET:
                b = a;
                break;

            // Math
            case ADD:
                ex = over(b+a)? (char) 0x0001 : (char) 0x0;
                b += a;
                break;
            case SUB:
                ex = over(b-a)? (char) 0xffff : (char) 0x0;
                b -= a;
                break;
            case MUL:
                ex = (char)(((b*a)>>16)&0xffff);
                b *= a;
                break;
            case MLI:
                ex = (char)(((shortB*shortA)>>16)&0xffff);
                shortB *= shortA;
                b = (char) shortB;
                break;
            case DIV:
                if(a==0){
                    b = 0;
                    ex = 0;
                }
                else{
                    ex = (char) (((b<<16)/a)&0xffff);
                    b /= a;
                }
                break;
            case DVI:
                if(shortA==0){
                    shortB = 0;
                    b = (char)shortB;
                    ex = 0;
                }
                else{
                    ex = (char)(((shortB/shortA)>>16)&0xffff);
                    shortB /= shortA;
                    b = (char) shortB;
                }
                break;
            case MOD:
                if(a==0){
                    b = 0;
                }
                else{
                    b %= a;
                }
                break;
            case MDI:
                if(shortA==0){
                    shortB = 0;
                    b = (char) shortB;
                }
                else{
                    shortB %= shortA;
                    b = (char) shortB;
                }
                break;

            // Bit fiddling
            case AND:
                b &= a;
                break;
            case BOR:
                b|=a;
                break;
            case XOR:
                b^=a;
                break;
            case SHR:
                ex = (char) (((shortB<<16)>>shortA)&0xffff);
                b>>>=a;
                break;
            case ASR:
                ex = (char) (((shortB<<16)>>>shortA)&0xffff);
                shortB>>=shortA;
                b = (char) shortB;
                break;
            case SHL:
                ex = (char) (((b<<a)>>16)&0xffff);
                b <<= a;
                break;


            // IF statements
            case IFB:
                if((b&a)!=0){
                    return;
                }
                else{
                    skipUntilNonConditional();
                }
                break;
            case IFC:
                if((b&a)==0){
                    return;
                }else{
                    skipUntilNonConditional();
                }
                break;
            case IFE:
                if(b==a){
                    return;
                }else{
                    skipUntilNonConditional();
                }
                break;
            case IFN:
                if(b!=a){
                    return;
                }else{
                    skipUntilNonConditional();
                }
                break;
            case IFG:
                if(b>a){
                    return;
                }
                else{
                    skipUntilNonConditional();
                }
                break;
            case IFA:
                if(shortB>shortA){
                    return;
                }
                else{
                    skipUntilNonConditional();
                }
                break;
            case IFL:
                if(b<a){
                    return;
                }
                else{
                    skipUntilNonConditional();
                }
                break;
            case IFU:
                if(shortB<shortA){
                    return;
                }
                else{
                    skipUntilNonConditional();
                }
                break;
            case ADX:
                ex = (char) (over(b+a+ex)? 0x0001 : 0x0);
                b+=a;
                b+=ex;
                break;
            case SBX:
                ex = (char) (over(b-a+ex)? 0xFFFF : 0x0);
                b-=a;
                b+=ex;
                break;

            case STI:
                b = a;
                registers[registers.length-1] = (char) (registers[registers.length-1]+1);
                registers[registers.length-2] = (char) (registers[registers.length-2]+1);
                break;
            case STD:
                b = a;
                registers[registers.length-1] = (char) (registers[registers.length-1]-1);
                registers[registers.length-2] = (char) (registers[registers.length-2]-1);
                break;
            case SPECIAL:
                switch (opB){
                    case JSR:
                        memory[--sp] = pc;
                        modified[sp] = true;
                        modifiedSet.add(sp);
                        pc = a;
                        return;
                    case BRK:
                        this.isRunning = false;
                        return;
                    case INT:
                    case IAG:
                    case IAS:
                    case FRI:
                    case IAQ:
                        throw new EmulatorException("Operation not accepted"+convertHex(opB),pc);
                    case HWN:
                        pa.set(deviceManager.hwn(this));
                        return;
                    case HWQ:
                        deviceManager.hwq(this,a);
                        return;
                    case HWI:
                        System.out.println("HWI");
                        deviceManager.hwi(this,a);
                        return;
                }
        }

        pb.set(b);
    }

    private Pointer getPointer(char input, int offset, boolean isA) throws EmulatorException {
        if(input <= REGISTER_MAX){
            return new Pointer(POINTER_REGISTER, input);
        }
        if(input <= POINTER_REGISTER_MAX){
            return new Pointer(POINTER_MEMORY,registers[input-POINT_A]);
        }
        if(input <= POINTER_REGISTER_NEXT_MAX){
            return new Pointer(POINTER_MEMORY, (char) (registers[input-POINT_A_NEXT]+memory[pc+offset]));
        }
        if(input == PUSH_POP){
            // POP
            if(isA){
                return new Pointer(POINTER_MEMORY, (char) (sp++));
            }
            // PUSH
            else{
                return new Pointer(POINTER_MEMORY, (char) (--sp));
            }
        }
        if(input == PEEK){
            return new Pointer(POINTER_MEMORY, sp);
        }
        if(input==PICK){
            return new Pointer(POINTER_MEMORY, (char)(sp+memory[pc+offset]));

        }
        if(input == SP){
            return new Pointer(POINTER_SP);
        }
        if(input == PC){
            return new Pointer(POINTER_PC);
        }
        if(input == EX){
            return new Pointer(POINTER_EX);
        }
        if(input == POINT_NEXT){
            return new Pointer(POINTER_MEMORY, memory[pc+offset]);
        }
        if(input == NEXT){
            return new Pointer(POINTER_MEMORY, (char) (pc+offset));
        }
        if(input>=LITERAL_MIN && input<=LITERAL_MAX){
            return new Pointer(POINTER_LITERAL, (char) (input-0x21));
        }

        throw new EmulatorException("cant identify ValueCode: "+(int) input,pc);
    }

    private char getOffset(char valueCode){
        switch(valueCode){
            case POINT_A_NEXT:
            case POINT_B_NEXT:
            case POINT_C_NEXT:
            case POINT_X_NEXT:
            case POINT_Y_NEXT:
            case POINT_Z_NEXT:
            case POINT_I_NEXT:
            case POINT_J_NEXT:

            case PICK:
            case POINT_NEXT:
            case NEXT:
                return 1;
            default:
                return 0;
        }
    }

    private void skipUntilNonConditional() throws EmulatorException {
        char instruction = memory[pc++];
        char opcode   = clear(instruction, A_SIZE+B_SIZE, 0);
        char opA      = clear(instruction, 0, B_SIZE+OP_SIZE);
        char opB      = clear(instruction, A_SIZE, B_SIZE);

        boolean cond = isConditional(opcode);


        if(cond){
            pc += (getOffset(opA)+getOffset(opB));
            skipUntilNonConditional();
        }
        else{
            pc += (getOffset(opA)+getOffset(opB));
            return;
        }
    }
















    public void run() throws EmulatorException {
        isRunning = true;
        while(isRunning){
            this.step();
            fireEvents();
        }
    }

    public void runUntilPosition(int codePoint) throws EmulatorException {
        isRunning = true;
        while(isRunning){
            this.step();
            fireEvents();
            if(this.pc >= codePoint){
                this.isRunning = false;
            }
        }
    }

    public void runUntilTime(int millis) throws EmulatorException {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Machine.this.stop();
            }
        },millis);
        this.run();
    }

    public void runUntilOverflow() throws EmulatorException {
        this.runUntilPosition(0xfffa);
    }

    public void stop(){
        isRunning = false;
    }

    private List<StepEvent> stepEvents = new ArrayList<StepEvent>();
    public void addStepEvent(StepEvent stepEvent){
        this.stepEvents.add(stepEvent);
    }
    public void removeStepEvent(StepEvent stepEvent){
        this.stepEvents.remove(stepEvent);
    }
    private void fireEvents(){
        for(StepEvent se:stepEvents){
            se.onStep(this);
        }
    }

    public char[] getRegisters() {
        return registers;
    }
    public char[] getMemory() {
        return memory;
    }
    public boolean[] getMask(){
        return this.modified;
    }
    public Set<Character> getTotalModified(){
        return this.modifiedSet;
    }
    public Set<Character> getJustModified(){
        return this.justModified;
    }
    public boolean isModified(int i) {
        return modified[i];
    }
    public char getSp(){
        return sp;
    }
    public char getPc(){
        return  pc;
    }
    public char getEx(){
        return ex;
    }

    public int addDevice(Device device){
        return this.deviceManager.add(device);
    }
}
