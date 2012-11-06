package com.burgin.javaakka.messages;

import com.burgin.javaakka.domain.Pizza;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 9/16/12
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class PizzaRequestMessage {

    private final Pizza pizza;

    public PizzaRequestMessage(Pizza pizza){
        this.pizza = pizza;
    }

    public Pizza getPizza() {
        return pizza;
    }
}
