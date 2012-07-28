package com.prealpha.dcputil.emulator.devices;

import com.prealpha.dcputil.emulator.Machine;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Ty
 * Date: 7/21/12
 * Time: 8:14 PM
 */
public class DeviceManager {
    private List<Device> devices = new ArrayList<Device>();

    public int add(Device device){
        this.devices.add(device);
        return this.devices.size()-1;
    }

    public char hwn(Machine machine){
        return (char) devices.size();
    }

    public void hwq(Machine machine, char dev){
        Device device = devices.get(dev);

        machine.getRegisters()[0]= device.getId()[0];
        machine.getRegisters()[1]= device.getId()[1];

        machine.getRegisters()[2]= device.getManufacturer()[0];
        machine.getRegisters()[3]= device.getManufacturer()[1];

        machine.getRegisters()[4]= device.getVersion();
    }

    public void hwi(Machine machine, char device){
        if(!(device>=devices.size())){
            devices.get(device).hwi(machine);
        }
        System.err.println("HWI for device larger than the device size."+"size: "+devices.size()+" dev: "+device);
    }

    public void update(Machine machine){
        for(Device device:devices){
            device.update(machine);
        }
    }
}
