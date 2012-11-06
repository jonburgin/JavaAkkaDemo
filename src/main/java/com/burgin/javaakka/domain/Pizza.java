package com.burgin.javaakka.domain;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 9/16/12
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pizza {
    final private String name;

    public Pizza(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
