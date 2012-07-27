package com.prealpha.dcputil.emulator.devices;

import com.prealpha.dcputil.emulator.Machine;

/**
 * User: Ty
 * Date: 7/21/12
 * Time: 8:11 PM
 */
interface Device {
    public char[] getId();
    public char[] getManufacturer();
    public char   getVersion();
    public void   hwi(Machine machine);
    void update(Machine machine);

}
