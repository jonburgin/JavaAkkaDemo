package com.burgin.javaakka.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Procedure;
import com.burgin.javaakka.domain.ResultsProcessor;
import com.burgin.javaakka.domain.VirtualMachine;
import com.burgin.javaakka.domain.VirtualMachineStatus;
import com.burgin.javaakka.messages.ServerStatusRequestMessage;
import com.burgin.javaakka.messages.VirtualMachineStatusMessage;
import com.burgin.javaakka.messages.VirtualMachineStatusRequestMessage;

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

    private ArrayList<VirtualMachineStatus> virtualMachineStatuses = new ArrayList<VirtualMachineStatus>();

    Procedure<Object> configured = new Procedure<Object>() {
        @Override
        public void apply(Object message) {
            if (message instanceof VirtualMachineStatusMessage) {
                handle((VirtualMachineStatusMessage) message);
            } else {
                unhandled(message);
            }
        }
    };

    @Override
    public void onReceive(Object message) {
        if (message instanceof ServerStatusRequestMessage) {
            handle((ServerStatusRequestMessage) message);
            getContext().become(configured);
        } else {
            unhandled(message);
        }

    }

    private void handle(VirtualMachineStatusMessage virtualMachineStatusMessage){
        setThreadName();
        System.out.println(String.format("%s (VirtualMachineStatusMessage(%s)) -> %s", threadName, virtualMachineStatusMessage.getVirtualMachineStatus().getName(), name));
        virtualMachineStatuses.add(virtualMachineStatusMessage.getVirtualMachineStatus());
        if(virtualMachineStatuses.size() >= expectedResults){
            runProcessorAndDie();
        }
    }


    private void handle(final ServerStatusRequestMessage serverStatusRequestMessage) {
        setThreadName();
        name =  String.format("[%s]", serverStatusRequestMessage.getServer().getName());

        resultsProcessor = serverStatusRequestMessage.getResultsProcessor();

        System.out.println(String.format("%s (VmStatusRequest) -> %s", threadName, name));

        //simulate some results
        List<VirtualMachine> virtualMachines = serverStatusRequestMessage.getServer().getVirtualMachines();
        expectedResults = virtualMachines.size();
        System.out.println(String.format("%s %s : Found %d virtual machines on remote server", threadName, name, expectedResults ));


        //create processing actors for each of the virtual machines found
        for(VirtualMachine virtualMachine:virtualMachines){
            System.out.println(String.format("%s %s : Creating an Actor for acquiring vm status", threadName, name));
            ActorRef infoCollector = getContext().actorOf(new Props(serverStatusRequestMessage.getWorkerClass()));
            System.out.println(String.format("%s %s -> (VirtualMachineStatusRequestMessage(%s))", threadName,name, virtualMachine.getName()));
            infoCollector.tell(new VirtualMachineStatusRequestMessage(virtualMachine), self());
        }

        resultsProcessor = serverStatusRequestMessage.getResultsProcessor();

    }

    private void runProcessorAndDie(){
        resultsProcessor.process(virtualMachineStatuses, null);
        getContext().stop(self());
    }

}
