package com.ifmo.clientapp;

/**
 * Created by User on 28.04.2021.
 */
public class ClientApplication {
    public static void main(String[] args) {
        new Client("127.0.0.1", 8999).start();
    }
}