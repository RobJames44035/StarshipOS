/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.util.Map;

/*
 * A specific SSL/TLS communication test case on two peers.
 */
public class TestCase<U extends UseCase> {

    // The server side use case.
    public final U serverCase;

    // The client side use case.
    public final U clientCase;

    private Status status;

    public TestCase(U serverCase, U clientCase) {
        this.serverCase = serverCase;
        this.clientCase = clientCase;
    }

    public TestCase<U> addServerProp(String prop, String value) {
        serverCase.addProp(prop, value);
        return this;
    }

    public String getServerProp(String prop) {
        return serverCase.getProp(prop);
    }

    public TestCase<U> addAllServerProps(Map<String, String> props) {
        serverCase.addAllProps(props);
        return this;
    }

    public Map<String, String> getAllServerProps() {
        return serverCase.getAllProps();
    }

    public TestCase<U> removeServerProp(String prop) {
        serverCase.removeProp(prop);
        return this;
    }

    public TestCase<U> removeAllServerProp() {
        serverCase.removeAllProps();
        return this;
    }

    public TestCase<U> addClientProp(String prop, String value) {
        clientCase.addProp(prop, value);
        return this;
    }

    public String getClientProp(String prop) {
        return clientCase.getProp(prop);
    }

    public TestCase<U> addAllClientProps(Map<String, String> props) {
        clientCase.addAllProps(props);
        return this;
    }

    public Map<String, String> getAllClientProps() {
        return clientCase.getAllProps();
    }

    public TestCase<U> removeClientProp(String prop) {
        clientCase.removeProp(prop);
        return this;
    }

    public TestCase<U> removeAllClientProp() {
        clientCase.removeAllProps();
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Server: " + serverCase + "\n" + "Client: " + clientCase;
    }
}
