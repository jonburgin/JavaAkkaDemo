package com.burgin.javaakka.domain;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class PizzaQuality {
    final boolean deepDish;
    final private String name;

    public PizzaQuality(String name, boolean deepDish){
        this.name = name;
        this.deepDish = deepDish;
    }

    public boolean isDeepDish() {
        return deepDish;
    }

    public String getName() {
        return name;
    }
}
