package com.ifmo.lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by User on 28.04.2021.
 */
public class Connection implements AutoCloseable {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        // Последовательность важна. Сначала output, потом input
        // Иначе будет блокировка канала
        output = new ObjectOutputStream(socket.getOutputStream());
        // socket.getOutputStream() -- объект, который может отправлять данные по сокету
        input = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(SimpleMessage message) throws IOException {
        message.setDateTime();
        output.writeObject(message);
        output.flush(); // Чтобы данные ушли, иначе останутся
    }

    public SimpleMessage readMessage() throws IOException, ClassNotFoundException {
        return (SimpleMessage) input.readObject();
    }

    @Override
    public void close() throws Exception {
        input.close();
        output.close();
        socket.close();
    }
}
