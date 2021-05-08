package com.ifmo.serverapp;

import com.ifmo.lib.CommandsNames;
import com.ifmo.lib.Connection;
import com.ifmo.lib.ImgMessage;
import com.ifmo.lib.SimpleMessage;
import com.ifmo.lib.handlers.ImgHandler;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by User on 28.04.2021.
 */
public class Server {
    private int port;
    private Connection connection;
    private HashSet<String> clients;

    public Server(int port) {
        this.port = port;
        clients = new HashSet<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) { // Запускается и ждет
            System.out.println("Сервер запущен");
            while (true) {
                // Ожидание
                Socket newClient = serverSocket.accept(); // Установка соединения с клиентом
                connection = new Connection(newClient);
                SimpleMessage message = connection.readMessage();
                clients.add(message.getSender());
                System.out.println(message);
                if (message.getText().equalsIgnoreCase(CommandsNames.HELP)) {
                    connection.sendMessage(SimpleMessage.getMessage("сервер", getCommands()));
                } else if (message.getText().equalsIgnoreCase(CommandsNames.COUNT)) {
                    connection.sendMessage(SimpleMessage.getMessage("сервер", getClientsCount()));
                } else if (message.getText().equalsIgnoreCase(CommandsNames.IMG)) {
                    connection.sendMessage(SimpleMessage.getMessage("сервер", getLoadResult(message)));
                } else {
                    // Если все же пришла невалидная команда
                    connection.sendMessage(SimpleMessage.getMessage("сервер", "Такой команды не существует"));
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка сервера");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка чтения сообщения");
        }
    }

    private String getLoadResult(SimpleMessage message) {
        String result;
        ImgMessage imgMessage = (ImgMessage) message;
        // если сервер загружает картинку, нужно знать путь и имена файлов должны быть уникальными
        ImgHandler handler = new ImgHandler(new File(UUID.randomUUID() + "." + imgMessage.getExtension()));
        try {
            handler.writeToFile(imgMessage.getBytes());
            result = "Файл успешно сохранен";
        } catch (IOException e) {
            result = "Не удалось сохранить файл";
        }
        return result;
    }

    private String getClientsCount() {
        return "Количество клиентов " + clients.size();
    }

    private String getCommands() {
        return "help - список доступных команд\n" +
                "count - количество клиентов";
    }
}