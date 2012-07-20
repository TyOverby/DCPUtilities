package com.prealpha.dcputil.emulator;

import java.util.ArrayList;
import java.util.List;

import static com.prealpha.dcputil.emulator.EmulatorHelper.clear;
import static com.prealpha.dcputil.emulator.EmulatorHelper.isConditional;
import static com.prealpha.dcputil.emulator.EmulatorHelper.over;
import static com.prealpha.dcputil.emulator.Opcodes.*;
import static com.prealpha.dcputil.emulator.Valuecodes.*;
import static com.prealpha.dcputil.util.PrintUtilities.convertHex;

/**
 * User: Ty
 * Date: 7/19/12
 * Time: 11:46 AM
 */
class BaseMachine {
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
    protected boolean[] modified = new boolean[memory.length];

    public void load(char[] program){
        System.arraycopy(program, 0, memory, 0, program.length);
        lastProgram = program.clone();
    }

    public void step() throws EmulatorException {
        char instruction = memory[pc++];
        char opcode   = clear(instruction, A_SIZE+B_SIZE, 0);
        char opA      = clear(instruction, 0, B_SIZE+OP_SIZE);
        char opB      = clear(instruction, A_SIZE, B_SIZE);

        char valA = get(opA, true);
        char valB = get(opB, false);
        short sValA = (short) valA;
        short sValB = (short) valB;

        switch (opcode){
            case SET:
                set(opB, valA);
                break;

            // Math
            case ADD:
                set(opB,valB+valA);
                ex = over(valB+valA)? (char) 0x0001 : (char) 0x0;
                break;
            case SUB:
                set(opB,valB-valA);
                ex = over(valB-valA)? (char) 0xffff : (char) 0x0;
                break;
            case MUL:
                set(opB,valB*valA);
                ex = (char)(((valB*valA)>>16)&0xffff);
                break;
            case MLI:
                char result1 = (char) (sValA*sValB);
                set(opB, result1);
                ex = (char)(((sValA*sValB)>>16)&0xffff);
                break;
            case DIV:
                if(valA==0){
                    set(opB,0);
                    ex = 0;
                }
                else{
                    set(opB,valB/valA);
                    ex = (char) (((valB<<16)/valA)&0xffff);
                }
                break;
            case DVI:
                if(sValA==0){
                    set(opB,0);
                    ex = 0;
                }
                else{
                    char result2 = (char) (sValB/sValA);
                    set(opB, result2);
                    ex = (char)(((sValB/sValA)>>16)&0xffff);
                }
                break;
            case MOD:
                if(valA==0){
                    set(opB,0);
                }
                else{
                    set(opB, valB%valA);
                }
                break;
            case MDI:
                if(sValA==0){
                    set(opB,0);
                }
                else{
                    set(opB, sValB%sValA);
                }
                break;

            // Bit fiddling
            case AND:
                set(opB, valB&valA);
                break;
            case BOR:
                set(opB, valB|valA);
                break;
            case XOR:
                set(opB, valB^valA);
                break;
            case SHR:
                set(opB, valB>>>valA);
                ex = (char) (((valB<<16)>>valA)&0xffff);
                break;
            case ASR:
                set(opB, valB>>valA);
                ex = (char) (((valB<<16)>>>valA)&0xffff);
                break;
            case SHL:
                set(opB, valB<<valA);
                ex = (char) (((valB<<valA)>>16)&0xffff);
                break;


            // IF statements
            case IFB:
                if((valB&valA)!=0){
                    break;
                }
                else{
                    skipUntilNonConditional(0);
                }
                break;
            case IFC:
                if((valB&valA)==0){
                    break;
                }else{
                    skipUntilNonConditional(0);
                }
                break;
            case IFE:
                if(valB==valA){
                    break;
                }else{
                    skipUntilNonConditional(0);
                }
                break;
            case IFN:
                if(valB!=valA){
                    break;
                }else{
                    skipUntilNonConditional(0);
                }
                break;
            case IFG:
                if(valB>valA){
                    break;
                }
                else{
                    skipUntilNonConditional(0);
                }
                break;
            case IFA:
                if(sValB>sValA){
                    break;
                }
                else{
                    skipUntilNonConditional(0);
                }
                break;
            case IFL:
                if(valB>valA){
                    break;
                }
                else{
                    skipUntilNonConditional(0);
                }
                break;
            case IFU:
                if(sValB<sValA){
                    break;
                }
                else{
                    skipUntilNonConditional(0);
                }
                break;
            case ADX:
                set(opB,valB+valA+ex);
                ex = (char) (over(valB+valA+ex)? 0x0001 : 0x0);
                break;
            case SBX:
                set(opB,valB-valA+ex);
                ex = (char) (over(valB-valA+ex)? 0xFFFF : 0x0);
                break;

            case STI:
                set(opB, valA);
                registers[registers.length-1] = (char) (registers[registers.length-1]+1);
                registers[registers.length-2] = (char) (registers[registers.length-2]+1);
                break;
            case STD:
                set(opB, valA);
                registers[registers.length-1] = (char) (registers[registers.length-1]-1);
                registers[registers.length-2] = (char) (registers[registers.length-2]-1);
                break;
            case SPECIAL:
                switch (opB){
                    case JSR:
                        set(PUSH_POP,pc);
                        pc = valA;
                        break;
                    case BRK:
                        this.isRunning = false;
                        break;
                    case INT:
                    case IAG:
                    case IAS:
                    case FRI:
                    case IAQ:
                    case HWN:
                    case HWQ:
                    case HWI:
                        throw new EmulatorException("Operation not accepted"+convertHex(opB),pc);
                }

        }
    }


    private void set(char input, int data) throws EmulatorException {
        set(input, (char) data);
    }
    private void set(char input, char data) throws EmulatorException {
        if(input <= REGISTER_MAX){
            registers[input] = data;
            return;
        }
        if(input <= POINTER_REGISTER_MAX){
            memory[registers[input-POINT_A]] = data;
            return;
        }
        if(input <= POINTER_REGISTER_NEXT_MAX){
            memory[registers[input-POINT_A_NEXT]+memory[pc++]] = data;
            return;
        }
        if(input == PUSH_POP){
            // SP already got evaluated once as POP, so we
            // have to decrement it one extra time
            //sp--;
            memory[--sp] = data;
            return;
        }
        if(input == PEEK){
            memory[sp] = data;
            return;
        }
        if(input==PICK){
            char place = (char) (sp+memory[pc++]);
            memory[place] = data;
            return;
        }
        if(input == SP){
            sp = data;
            return;
        }
        if(input == PC){
            pc = data;
            return;
        }
        if(input == EX){
            ex = data;
            return;
        }
        if(input == POINT_NEXT){
            memory[memory[pc++]] = data;
        }
        if(input == NEXT){
            memory[pc++] = data;
        }

        throw new EmulatorException("Can't process value: "+input,pc);
    }

    private char get(char input, boolean modifyStack) throws EmulatorException {
        if(input <= REGISTER_MAX){
            return registers[input];
        }
        if(input <= POINTER_REGISTER_MAX){
            return memory[registers[input-POINT_A]];
        }
        if(input <= POINTER_REGISTER_NEXT_MAX){
            return memory[registers[input-POINT_A_NEXT]+memory[pc++]];
        }
        if(input == PUSH_POP){
            if(modifyStack){
                return memory[sp++];
            }
            else{
                return 0;
            }
        }
        if(input == PEEK){
            return memory[sp];
        }
        if(input==PICK){
            char place = (char)(sp+memory[pc++]);
            return memory[place];
        }
        if(input == SP){
            return sp;
        }
        if(input == PC){
            return pc;
        }
        if(input == EX){
            return ex;
        }
        if(input == POINT_NEXT){
            return memory[memory[pc++]];
        }
        if(input == NEXT){
            return memory[pc++];
        }
        if(input>=LITERAL_MIN && input<=LITERAL_MAX){
            return (char)(input-0x21);
        }
        throw new EmulatorException("Can't process value: "+input,pc);
    }

    private void skipUntilNonConditional(int runs) throws EmulatorException {
        char instruction = memory[pc++];
        char opcode   = clear(instruction, A_SIZE+B_SIZE, 0);
        char opA      = clear(instruction, 0, B_SIZE+OP_SIZE);
        char opB      = clear(instruction, A_SIZE, B_SIZE);

        boolean cond = isConditional(opcode);

        if(cond){
            char valA = get(opA, false);
            char valB = get(opB, false);
            skipUntilNonConditional(runs+1);
        }
        else{
            if(runs==0){
                char valA = get(opA, false);
                char valB = get(opB, false);
            }
            else{
                pc--;
            }
        }

    }

}
