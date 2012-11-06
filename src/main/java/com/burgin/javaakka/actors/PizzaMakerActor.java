package com.burgin.javaakka.actors;

import akka.actor.UntypedActor;
import com.burgin.javaakka.domain.VirtualMachine;
import com.burgin.javaakka.domain.VirtualMachineStatus;
import com.burgin.javaakka.messages.VirtualMachineStatusMessage;
import com.burgin.javaakka.messages.VirtualMachineStatusRequestMessage;

import javax.management.Query;

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
        if(message instanceof VirtualMachineStatusRequestMessage){
            sender().tell(processVmStatusRequestMessage((VirtualMachineStatusRequestMessage) message));
            getContext().stop(self()); //My reason for living is gone, goodbye cruel world, I gave you all I had.
        }else{
            unhandled(message);
        }
    }

    private VirtualMachineStatusMessage processVmStatusRequestMessage(VirtualMachineStatusRequestMessage message) {
        final VirtualMachine virtualMachine = ((VirtualMachineStatusRequestMessage) message).getVirtualMachine();
        name = "[" + virtualMachine.getName() + "]";
        setThreadName();
        System.out.println(String.format("%s (VirtualMachineStatusRequestMessage) -> %s",threadName, name));
        simulateComputeTime(virtualMachine.getName());
        System.out.println(String.format("%s %s -> (VirtualMachineStatus)",threadName, name));
        return new VirtualMachineStatusMessage(new VirtualMachineStatus(virtualMachine.getName(), Math.random() < .5));
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
