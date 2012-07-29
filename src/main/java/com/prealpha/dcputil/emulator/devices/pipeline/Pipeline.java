package com.prealpha.dcputil.emulator.devices.pipeline;

import com.prealpha.dcputil.emulator.Machine;
import com.prealpha.dcputil.emulator.devices.Device;

import java.util.LinkedList;
import java.util.Queue;

/**
 * User: Ty
 * Date: 7/28/12
 * Time: 11:33 PM
 */
public class Pipeline implements Device {
    private boolean isConnected = false;
    private final Queue<Character> queue = new LinkedList<Character>();

    @Override
    public char[] getId() {
        return new char[]{0,0};
    }

    @Override
    public char[] getManufacturer() {
        return new char[]{0x00c0,0xffee};  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public char getVersion() {
        return 0;
    }

    @Override
    public void hwi(Machine machine) {
        this.isConnected = true;
        char a = machine.getRegisters()[0];
        char b = machine.getRegisters()[1];

        switch(a){
            case 0:
                this.queue.add(b);
                break;
            default:
                System.err.println("HWI to Pipeline does not work with value: "+(int)a);

        }
    }

    @Override
    public void update(Machine machine) {
    }

    public Queue<Character> getQueue(){
        return this.queue;
    }
}
