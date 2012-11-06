package com.burgin.javaakka.messages;

import com.burgin.javaakka.domain.VirtualMachineStatus;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class DinnerResultMessage {
    private String url;
    private VirtualMachineStatus[] virtualMachineStatii;

    public DinnerResultMessage(String url, VirtualMachineStatus[] virtualMachineStatii){
        this.url = url;
        this.virtualMachineStatii = virtualMachineStatii;
    }

    public String getUrl() {
        return url;
    }

    public VirtualMachineStatus[] getVirtualMachineStatii() {
        return virtualMachineStatii;
    }
}
