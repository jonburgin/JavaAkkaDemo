package com.burgin.javaakka.domain;

import com.burgin.javaakka.domain.VirtualMachineStatus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 9/16/12
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ResultsProcessor {
    public void process(List<VirtualMachineStatus> statuses, List<VirtualMachineError> errors);
}
