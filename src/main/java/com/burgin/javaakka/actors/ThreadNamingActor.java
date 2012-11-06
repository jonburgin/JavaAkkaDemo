package com.burgin.javaakka.actors;

import akka.actor.UntypedActor;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 11/5/12
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
abstract public class ThreadNamingActor extends UntypedActor {

    protected String name;

    protected String threadName;

    protected void setThreadName(){
      String[] parts = Thread.currentThread().getName().split("-");
      int n = 0;
      try{
        n  = Integer.parseInt(parts[parts.length - 1]) - 1;
      }catch (NumberFormatException nfe){
      }         //intentionally left blank
      threadName = String.format("%"+(n>0?n*38:1) + "s{%d}"," ", n );
    }
}
