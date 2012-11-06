package com.burgin.javaakka.actors;

import com.burgin.javaakka.domain.Pizza;
import com.burgin.javaakka.domain.PizzaQuality;
import com.burgin.javaakka.messages.PizzaMessage;
import com.burgin.javaakka.messages.PizzaRequestMessage;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 9/16/12
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class PizzaMakerActor extends ThreadNamingActor {

    @Override
    public void onReceive(Object message) {
        if(message instanceof PizzaRequestMessage){
            sender().tell(processVmStatusRequestMessage((PizzaRequestMessage) message));
            getContext().stop(self()); //My reason for living is gone, goodbye cruel world, I gave you all I had.
        }else{
            unhandled(message);
        }
    }

    private PizzaMessage processVmStatusRequestMessage(PizzaRequestMessage message) {
        final Pizza pizza = ((PizzaRequestMessage) message).getPizza();
        name = "[" + pizza.getName() + "]";
        setThreadName();
        System.out.println(String.format("%s (PizzaRequest) -> %s",threadName, name));
        simulateComputeTime(pizza.getName());
        System.out.println(String.format("%s %s -> (Pizza)",threadName, name));
        return new PizzaMessage(new PizzaQuality(pizza.getName(), Math.random() < .5));
    }

    private void simulateComputeTime(String name){
        //simulate some time for making the request
        long sleepTime = (long) (Math.random() * 7000L + 100);
        System.out.println(String.format("%s %s starting work",threadName, name));
        try {
            Thread.sleep(sleepTime);
            System.out.println(String.format("%s %s done working",threadName, name));
        } catch (InterruptedException e) {
            //don't care
        }
    }
}
