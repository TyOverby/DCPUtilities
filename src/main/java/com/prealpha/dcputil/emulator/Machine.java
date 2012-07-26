package com.prealpha.dcputil.emulator;

import com.prealpha.dcputil.emulator.testing.EndOfMemoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Machine extends NewBaseMachine{


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

    public void reset(){
        for(int i=0;i<memory.length;i++){
            memory[i] = 0;
        }
        this.load(this.lastProgram);
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
    public List<Character> getTotalModified(){
        return this.modifiedList;
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
}