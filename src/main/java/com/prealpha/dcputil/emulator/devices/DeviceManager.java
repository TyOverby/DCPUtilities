package com.prealpha.dcputil.emulator.devices;

import com.prealpha.dcputil.emulator.BaseMachine;
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

    public void hwn(BaseMachine machine){
        machine.registers()[0]= (char) devices.size();
    }

    public void hwq(Machine machine, char dev){
        Device device = devices.get(dev);

        machine.getRegisters()[0]= device.getId()[0];
        machine.getRegisters()[1]= device.getId()[1];

        machine.getRegisters()[2]= device.getManufacturer()[0];
        machine.getRegisters()[3]= device.getManufacturer()[1];

        machine.getRegisters()[4]= device.getVersion();
    }

    public void hwi(BaseMachine machine, char device){
        devices.get(device).hwi(machine);
    }

    public void update(BaseMachine machine){
        for(Device device:devices){
            device.update(machine);
        }
    }
}
