package com.burgin.javaakka.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Procedure;
import com.burgin.javaakka.domain.Pizza;
import com.burgin.javaakka.domain.PizzaQuality;
import com.burgin.javaakka.domain.ResultsProcessor;
import com.burgin.javaakka.messages.DinnerRequestMessage;
import com.burgin.javaakka.messages.PizzaMessage;
import com.burgin.javaakka.messages.PizzaRequestMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 8:36 PM
 * This actor receives a StatusRequestMessage that contains a url for a server.
 * Then it gets all the vms on that server and returns the result status to the actor that sent the request
 */

public class PizzaManagerActor extends ThreadNamingActor {

    int expectedResults;
    private ResultsProcessor resultsProcessor;

    private ArrayList<PizzaQuality> virtualMachineStatuses = new ArrayList<PizzaQuality>();

    Procedure<Object> configured = new Procedure<Object>() {
        @Override
        public void apply(Object message) {
            if (message instanceof PizzaMessage) {
                handle((PizzaMessage) message);
            } else {
                unhandled(message);
            }
        }
    };

    @Override
    public void onReceive(Object message) {
        if (message instanceof DinnerRequestMessage) {
            handle((DinnerRequestMessage) message);
            getContext().become(configured);
        } else {
            unhandled(message);
        }

    }

    private void handle(PizzaMessage pizzaMessage){
        setThreadName();
        System.out.println(String.format("%s (Pizza(%s)) -> %s", threadName, pizzaMessage.getPizzaQuality().getName(), name));
        virtualMachineStatuses.add(pizzaMessage.getPizzaQuality());
        if(virtualMachineStatuses.size() >= expectedResults){
            runProcessorAndDie();
        }
    }


    private void handle(final DinnerRequestMessage dinnerRequestMessage) {
        setThreadName();
        name =  String.format("[%s]", dinnerRequestMessage.getServer().getName());

        resultsProcessor = dinnerRequestMessage.getResultsProcessor();

        System.out.println(String.format("%s (DinnerRequest) -> %s", threadName, name));

        //simulate some results
        List<Pizza> pizzas = dinnerRequestMessage.getServer().getCustomerOrder();
        expectedResults = pizzas.size();
        System.out.println(String.format("%s %s : has %d pizzas in order", threadName, name, expectedResults ));


        //create processing actors for each of the virtual machines found
        for(Pizza pizza : pizzas){
            System.out.println(String.format("%s %s : Creating Pizza maker", threadName, name));
            ActorRef infoCollector = getContext().actorOf(new Props(dinnerRequestMessage.getWorkerClass()));
            System.out.println(String.format("%s %s -> (PizzaRequest(%s))", threadName,name, pizza.getName()));
            infoCollector.tell(new PizzaRequestMessage(pizza), self());
        }

        resultsProcessor = dinnerRequestMessage.getResultsProcessor();

    }

    private void runProcessorAndDie(){
        resultsProcessor.process(virtualMachineStatuses, null);
        getContext().system().shutdown();
    }

}
