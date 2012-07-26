package com.rackspace.javaakka.domain;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class VmStatus {
    boolean on;
    public VmStatus(boolean on){
        this.on = on;
    }

    public boolean isOn() {
        return on;
    }
}
