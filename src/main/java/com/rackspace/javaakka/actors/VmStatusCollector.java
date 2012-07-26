package com.rackspace.javaakka.actors;

import akka.actor.UntypedActor;
import com.rackspace.javaakka.domain.VmStatus;
import com.rackspace.javaakka.messages.VmStatusRequestMessage;
import com.rackspace.javaakka.messages.VmStatusResultMessage;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 8:36 PM
 * This actor receives a StatusRequestMessage that contains a url for a server.
 * Then it gets all the vms on that server and returns the result status to the actor that sent the request
 */

public class VmStatusCollector extends UntypedActor {

    @Override
    public void onReceive(Object message) {
        if( message instanceof VmStatusRequestMessage){
            final VmStatusRequestMessage vmStatusRequestMessage = (VmStatusRequestMessage) message;
            String offsetString = getActorNumber(vmStatusRequestMessage.getUrl());
            System.out.println(offsetString + vmStatusRequestMessage.getUrl() +" received VmStatusRequest on thread: " + Thread.currentThread().getName());
            //simulate some time for making the request
            long sleepTime = (long)(Math.random() * 5000L + 500);
            System.out.println(offsetString + vmStatusRequestMessage.getUrl() + " call will take " + sleepTime + " milliseconds");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                //don't care
            }

            //simulate some results
            int numberOfVmsFound = (int)(Math.random()*10 + 3);
            System.out.println(offsetString + vmStatusRequestMessage.getUrl() + " simulating finding " + numberOfVmsFound + " vms.");
            VmStatus[] vmStatuses = new VmStatus[numberOfVmsFound];
            for(int i=0; i < vmStatuses.length; i++){
                vmStatuses[i] = new VmStatus(true);//they are all on
            }

            //notify the sender of results (the sender in this example is the instand of VmStatusCollector Runner
            System.out.println(offsetString + vmStatusRequestMessage.getUrl() +  " sending results message to sender");
            sender().tell(new VmStatusResultMessage(vmStatusRequestMessage.getUrl(),vmStatuses));

        }

    }
    
    //You can ignore this function, it just makes the printout prettier
    String getActorNumber(String url){
        final String[] parts = url.split("server");
        return " " + parts[parts.length-1] + ": ";

    }
}
