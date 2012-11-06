package com.burgin.javaakka.domain;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class PizzaQuality {
    final boolean tasty;
    final private String name;

    public PizzaQuality(String name, boolean tasty){
        this.name = name;
        this.tasty = tasty;
    }

    public boolean isTasty() {
        return tasty;
    }

    public String getName() {
        return name;
    }
}
