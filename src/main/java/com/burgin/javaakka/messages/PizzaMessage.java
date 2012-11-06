package com.burgin.javaakka.messages;

import com.burgin.javaakka.domain.PizzaQuality;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 9/16/12
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class PizzaMessage {
    private PizzaQuality pizzaQuality;

    public PizzaMessage(PizzaQuality pizzaQuality) {
        this.pizzaQuality = pizzaQuality;
    }

    public PizzaQuality getPizzaQuality() {
        return pizzaQuality;
    }
}
