This project is a very simple example of how to use akka framework in java.

This is a very simple model of one way to use the akka framework to dynamically create workers.

The Example.java class contains the main method.

This class creates an actor system and a PizzaManagerActor, which is an actor.

The PizzaManagerActor processes messages two messages:
   --DinnerRequestMessage

   The DinnerRequestMessage has three components, (1)a server to get the pizza order from, (2) a class that is used to make the pizzas,
   and (3) a processor that will evaluate the collective results of all the pizzas made.
   Upon receiving a DinnerRequestMessage the PizzaManagerActor will determine the number of pizzas to create by asking the server.
   For each pizza, a PizzaMakerActor is created and a PizzaRequestMessage is sent to it.
   The ResultsProcessor is stored for later use.
   Upon completion of processing this message it changes its state to receive PizzaResultMessages.

   --PizzaResultMessage
   Each PizzaResultMessage is stored upon arrival.
   Once all the responses (PizzaResultMessages) have arrived, the collected results are given to the resultsProcessor for processing.
   After processing is complete, the PizzaManagerActor terminates the Akka System and the application ends.

   Each PizzaMakerActor is designed to receive only a single PizzaRequestMessage, any other messages are sent to the unhandled queue.
   The PizzaMakerActor will simulate some processing time, and the return a random result (either a tasty or bland pizza).

When the VmStatusCollectorRunner receives the results for all the urls, it terminates the actor system and the application completes.


Notes:
    We use change of processing state to create a simple mechnism of configuration, this is an analog to calling a constructor before using the class.
    Non-default constructors can be called in the Akka system, but it is a bit cumbersome to use, so I use this method instead.

    Errors can and do happen. So for this type of scenario, it is typically handled by having the top level actor count both errors and results.
    Once the sum of the two types equals the number of workers, processing of all workers has been attempted.

    The messages are plain old java objects (POJOs) and don't have to be wrapped in messages.
    I like to wrap the domain objects in messages in practice so that I can put meta data (e.g. processing time) in the message and don't have to pollute my domain objects.
    This keeps nice separation of concerns.

    Actors can be distributed on other computers.

    Creating lots of actors is very cheap (~700 bytes)