package com.cyberunited.secapi.model;

public class EchoResponse {
    private final String q;

    public EchoResponse(String q) {
        this.q = q;
    }

    public String getQ() { return q; }
}
