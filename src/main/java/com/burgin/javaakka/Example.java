package com.burgin.javaakka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.burgin.javaakka.actors.PizzaMakerActor;
import com.burgin.javaakka.actors.PizzaManagerActor;
import com.burgin.javaakka.domain.DefaultServer;
import com.burgin.javaakka.domain.PizzaQuality;
import com.burgin.javaakka.domain.ResultsProcessor;
import com.burgin.javaakka.messages.DinnerRequestMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class Example {

    public static void main(String[] args){
        //create or actorsystem, which keeps track of all the actors, message queues, and threads
        ActorSystem system = ActorSystem.create("PizzaShop");
        //create an actor that will spawn other actors
        ActorRef collectorRunner = system.actorOf(new Props(PizzaManagerActor.class), "PizzaManagerActor");

        ResultsProcessor resultsProcessor = new ResultsProcessor() {
            @Override
            public void process(List<PizzaQuality> statuses, List<VirtualMachineError> errors) {
                System.out.print("\nStatus of Pizzas : ");
                for(PizzaQuality pizzaQuality:statuses){
                    System.out.print(String.format(" %s:%s", pizzaQuality.getName(), pizzaQuality.isTasty() ? "Yum!" : "Meh"));
                }
                System.out.println();
            }
        };
        //send it a message so that it stats doing it's thing.
        collectorRunner.tell(new DinnerRequestMessage(new DefaultServer("Pat"), PizzaMakerActor.class, resultsProcessor));
    }
}
