package com.rackspace.javaakka.actors;

import akka.actor.*;
import com.rackspace.javaakka.messages.VmStatusRequestMessage;
import com.rackspace.javaakka.messages.VmStatusResultMessage;
import com.rackspace.javaakka.messages.VmStatusSourcesMessage;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 8:49 PM
 * This class is an actor running in a thread. When it receives a vm source message, it will create an actor for each source.
 * Those actor will each make a long running query and return the results to this actor.
 * Upon receiving all the results, just kill the actor system.
 */
public class VmStatusCollectorRunner extends UntypedActor{

    int numberOfSources;
    int numberOfResultsReceived;
    int numberOfVmsDiscovered;

    @Override
    public void onReceive(Object message) {
        //here we have receive a message containing urls of vmsphere servers that we need to query
        if(message instanceof VmStatusSourcesMessage){
            VmStatusSourcesMessage vmStatusSourcesMessage = (VmStatusSourcesMessage)message;
            String[] urls = vmStatusSourcesMessage.getUrls();
            numberOfSources += urls.length;
            System.out.println("About to spawn " + numberOfSources + " actors to collect vm data\n");
            for(String url:urls){
                //we create a separate actor (thread) for each url and tell it to gather vm status information
                String name = url.replace("http://", "");
                ActorRef collector = getContext().actorOf(new Props(VmStatusCollector.class),name);
                //tell it to query the source and provide a reference to self so results can be returned to this class.
                collector.tell(new VmStatusRequestMessage(url),self());
            }
        //we have received a result from one of the vmStatusCollectors
        }else if(message instanceof VmStatusResultMessage){
            VmStatusResultMessage vmStatusResultMessage = (VmStatusResultMessage) message;
            System.out.println("\nReceived report from server at url " + vmStatusResultMessage.getUrl() + " it has " + vmStatusResultMessage.getVmStatuses().length + " vms.\n");
            numberOfResultsReceived ++;
            numberOfVmsDiscovered += vmStatusResultMessage.getVmStatuses().length;
            //if we have received a result for each url source, we are done and can shutdown.
            if(numberOfResultsReceived == numberOfSources){
                System.out.println("\nFinished processing " + numberOfResultsReceived + " status result messages, which contained a total of " + numberOfVmsDiscovered + " vms.\n");
                //getContext().stop(self());
                getContext().system().shutdown();
            }
        }
    }
}
