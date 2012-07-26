package com.rackspace.javaakka.messages;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class VmStatusRequestMessage {
    private String url;

    public VmStatusRequestMessage(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
