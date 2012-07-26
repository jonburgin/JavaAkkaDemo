package com.rackspace.javaakka.messages;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class VmStatusSourcesMessage {
    private String[] urls;

    public VmStatusSourcesMessage(String[] urls){
        this.urls = urls;
    }

    public String[] getUrls() {
        return urls;
    }
}
