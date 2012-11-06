package com.burgin.javaakka.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import com.burgin.javaakka.domain.Pizza;
import com.burgin.javaakka.domain.PizzaQuality;
import com.burgin.javaakka.domain.ResultsProcessor;
import com.burgin.javaakka.domain.Server;
import com.burgin.javaakka.messages.DinnerRequestMessage;
import com.burgin.javaakka.messages.PizzaMessage;
import com.burgin.javaakka.messages.PizzaRequestMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 10/2/12
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class PizzaManagerActorTest {

    @Mock
    private ResultsProcessor mockResultsProcessor;

    @Mock
    private Server mockServer;

    final ActorSystem system = ActorSystem.create("TestSystem");
    final Props props = new Props(PizzaManagerActor.class);

    @Before
    public void setUpMocks(){
        initMocks(this);
        MessageCollectingWorker.reset();
        when(mockServer.getName()).thenReturn("Bubba");
        when(mockServer.getCustomerOrder()).thenReturn(Arrays.asList(new Pizza("Pepperoni"), new Pizza("Cheese")));
    }

    @After
    public void shutdownActorSystem(){
        system.shutdown();
    }

    @Test
    public void onReceive_withDinnerRequestMessage_createsAndTellsWorkerClass() throws Exception {
        new JavaTestKit(system){{
            final ActorRef pizzaManagerActorRef  = system.actorOf(props);
            new Within(duration("2 seconds")){
                protected void run(){
                    DinnerRequestMessage dinnerRequestMessage = new DinnerRequestMessage(mockServer, MessageCollectingWorker.class, mockResultsProcessor);
                    pizzaManagerActorRef.tell(dinnerRequestMessage, getRef());
                    expectNoMsg();//ensure time for processing to take place
                }
            };
            assertThat("PizzaRequestMessage was sent", MessageCollectingWorker.getMessage(), not(nullValue()));
        }};
    }

    @Test
    public void onReceive_upon2ndServerStatusRequestMessage_throwsException() throws Exception{
        new JavaTestKit(system){{
            final TestActorRef serverStatusActorRef  = TestActorRef.create(system,props,"configured test");
            new Within(duration("3 seconds")){
                protected void run(){
                    DinnerRequestMessage dinnerRequestMessage = new DinnerRequestMessage(mockServer, MessageCollectingWorker.class, mockResultsProcessor);
                    serverStatusActorRef.tell(dinnerRequestMessage, getRef());
                    serverStatusActorRef.tell(dinnerRequestMessage, getRef());
                    expectNoMsg();//ensure time for processing to take place
                }
            };
        }};
    }

    @Test
    public void onReceive_withoutAllPizzaMessages_doesNotCallProcessor(){
        new JavaTestKit(system){{
            final TestActorRef serverStatusActorRef  = TestActorRef.create(system,props,"configured test");
            new Within(duration("3 seconds")){
                protected void run(){
                    DinnerRequestMessage dinnerRequestMessage = new DinnerRequestMessage(mockServer, MessageCollectingWorker.class, mockResultsProcessor);
                    serverStatusActorRef.tell(dinnerRequestMessage, getRef());
                    serverStatusActorRef.tell(new PizzaMessage(new PizzaQuality("Cheese", true)));
                    expectNoMsg();//ensure time for processing to take place
                }
            };
            verify(mockResultsProcessor,times(0)).process(anyList(),anyList());
        }};
    }

    @Test
    public void onReceive_finalPizzaMessage_callsProcessor(){
        new JavaTestKit(system){{
            final TestActorRef serverStatusActorRef  = TestActorRef.create(system,props,"configured test");
            new Within(duration("3 seconds")){
                protected void run(){
                    DinnerRequestMessage dinnerRequestMessage = new DinnerRequestMessage(mockServer, MessageCollectingWorker.class, mockResultsProcessor);
                    serverStatusActorRef.tell(dinnerRequestMessage, getRef());
                    serverStatusActorRef.tell(new PizzaMessage(new PizzaQuality("Cheese", true)));
                    serverStatusActorRef.tell(new PizzaMessage(new PizzaQuality("Cheese", true)));
                    expectNoMsg();//ensure time for processing to take place
                }
            };
            verify(mockResultsProcessor,times(1)).process(anyList(),anyList());
        }};
    }

    public static class MessageCollectingWorker extends UntypedActor{
        static PizzaRequestMessage pizzaRequestMessage;

        @Override
        public void onReceive(Object message) {
            pizzaRequestMessage = (PizzaRequestMessage)message;
        }

       static public PizzaRequestMessage getMessage(){
           return pizzaRequestMessage;
       }

       static public void reset(){
          pizzaRequestMessage = null;
       }
    }
}
