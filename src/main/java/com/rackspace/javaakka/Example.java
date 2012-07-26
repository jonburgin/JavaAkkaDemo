package com.rackspace.javaakka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.rackspace.javaakka.actors.VmStatusCollectorRunner;
import com.rackspace.javaakka.messages.VmStatusSourcesMessage;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class Example {

    public static void main(String[] args){
        String[] urls = {"http://server1", "http://server2", "http://server3"};

        //create or actorsystem, which keeps track of all the actors
        ActorSystem system = ActorSystem.create("VmStatusAcquisition");
        //create an actor that will spawn other actors
        ActorRef collectorRunner = system.actorOf(new Props(VmStatusCollectorRunner.class), "collectorRunner");
        //send it a message so that it stats doing it's thing.
        collectorRunner.tell(new VmStatusSourcesMessage(urls));
    }
}
