package com.rackspace.javaakka.messages;

import com.rackspace.javaakka.domain.VmStatus;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class VmStatusResultMessage {
    private String url;
    private VmStatus[] vmStatuses;

    public VmStatusResultMessage(String url, VmStatus[] vmStatuses){
        this.url = url;
        this.vmStatuses = vmStatuses;
    }

    public String getUrl() {
        return url;
    }

    public VmStatus[] getVmStatuses() {
        return vmStatuses;
    }
}
