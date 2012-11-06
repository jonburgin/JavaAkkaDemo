package com.burgin.javaakka.domain;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 9/16/12
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ResultsProcessor {
    public void process(List<PizzaQuality> statuses, List<VirtualMachineError> errors);
}
