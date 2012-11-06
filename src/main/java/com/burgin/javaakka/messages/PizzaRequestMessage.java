package com.burgin.javaakka.messages;

import com.burgin.javaakka.domain.VirtualMachine;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 9/16/12
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class PizzaRequestMessage {

    private final VirtualMachine virtualMachine;

    public PizzaRequestMessage(VirtualMachine virtualMachine){
        this.virtualMachine = virtualMachine;
    }

    public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }
}
