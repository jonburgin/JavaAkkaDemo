package com.burgin.javaakka.messages;

import com.burgin.javaakka.domain.ResultsProcessor;
import com.burgin.javaakka.domain.Server;

/**
 * Created with IntelliJ IDEA.
 * User: jonburgin
 * Date: 7/25/12
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class DinnerRequestMessage {
    private Server server;
    Class workerClass;
    private ResultsProcessor resultsProcessor;

    public DinnerRequestMessage(Server server, Class workerClass, ResultsProcessor resultsProcessor){
        this.server = server;
        this.workerClass = workerClass;
        this.resultsProcessor = resultsProcessor;
    }

    public Server getServer() {
        return server;
    }

    public Class getWorkerClass() {
        return workerClass;
    }

    public ResultsProcessor getResultsProcessor() {
        return resultsProcessor;
    }
}
