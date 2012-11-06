package com.burgin.javaakka.domain;

import java.util.ArrayList;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 10/2/12
 * Time: 2:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultServer implements Server {
    String name;
    public DefaultServer(String url){
        this.name = url;
    }
    public String getName(){
        return name;
    }

    public List<Pizza> getCustomerOrder(){
        int numberOfPizzasOrdered = (int) (Math.random() * 4 + 3);
        ArrayList list = new ArrayList();
        for(int i = 0; i < numberOfPizzasOrdered; i++){
            list.add(new Pizza("Pizza-" + i));
        }
        return list;
    }
}
