This project is a very simple example of how to use akka framework in java.

The Example.java class contains the main method.

This class creates an actor system and a VmStatusCollectorRunner, which is an actor.

The VmStatusCollectorRunner processes messages containing a list of urls to query for vm status

   For each url, a VmStatusCollector actor is created, which simulates a query to that url and returns a result message
   to the actor that sent the request

When the VmStatusCollectorRunner receives the results for all the urls, it terminates the actor system and the application completes.