package com.burgin.javaakka.messages;

import com.burgin.javaakka.domain.VirtualMachineStatus;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 9/16/12
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class PizzaMessage {
    private VirtualMachineStatus virtualMachineStatus;

    public PizzaMessage(VirtualMachineStatus virtualMachineStatus) {
        this.virtualMachineStatus = virtualMachineStatus;
    }

    public VirtualMachineStatus getVirtualMachineStatus() {
        return virtualMachineStatus;
    }
}
