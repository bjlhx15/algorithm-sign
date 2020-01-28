package com.github.bjlhx15.security.changekey003DigitalEnvelopeDH;

import org.junit.Test;

import java.security.KeyPair;

import static org.junit.Assert.*;

public class DigitalEnvelopeTest {

    @Test
    public void generatorKeyPair() {
        KeyPair pair = DigitalEnvelope.generatorKeyPair();
        Thread client = new DigitalEnvelope.Client(pair);
        Thread server = new DigitalEnvelope.Server(pair.getPublic());
        server.start();
        client.start();

    }

    public static void main(String[] args) {
        KeyPair pair = DigitalEnvelope.generatorKeyPair();
        Thread client = new DigitalEnvelope.Client(pair);
        Thread server = new DigitalEnvelope.Server(pair.getPublic());
        server.start();
        client.start();
    }
}