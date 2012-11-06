package com.burgin.javaakka.domain;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 10/2/12
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Server {
    String getName();
    List<Pizza> getCustomerOrder();
}
