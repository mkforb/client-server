package com.ifmo.clientapp;

import com.ifmo.lib.CommandsNames;
import com.ifmo.lib.Connection;
import com.ifmo.lib.ImgMessage;
import com.ifmo.lib.SimpleMessage;
import com.ifmo.lib.handlers.ImgHandler;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by User on 28.04.2021.
 */
public class Client {
    private String ip;
    private int port;
    private Scanner scanner;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        scanner = new Scanner(System.in);
    }

    public void start() {
        // Д/з: Подумать как можно изменить метод start
        System.out.println("Введите имя");
        String userName = scanner.nextLine();
        String text;
        while (true) {
            System.out.println("Введите команду");
            text = scanner.nextLine();
            if (text.equals("exit")) break;
            if (text.equalsIgnoreCase(CommandsNames.HELP) || text.equalsIgnoreCase(CommandsNames.COUNT)) {
                // Отправляем только в том случае, если команда допустимая. Чтобы сервер зря на тревожить
                sendAndPrintMessage(SimpleMessage.getMessage(userName, text));
            } else if (text.toLowerCase().startsWith(CommandsNames.IMG)) {
                String commandName = text.split(" ")[0];
                String fileName = text.split(" ")[1];
                ImgHandler handler = new ImgHandler(new File(fileName));
                ImgMessage imgMessage = null;
                try {
                    imgMessage = new ImgMessage(userName, commandName, handler.readFromFile());
                    imgMessage.setExtension(handler.getExtension());
                    sendAndPrintMessage(imgMessage);
                } catch (IOException e) {
                    System.out.println("Не удалось прочитать файл");
                }
            } else {
                System.out.println("Такой команды не существует");
            }
        }
    }

    private void sendAndPrintMessage(SimpleMessage message) {
        try (Connection connection = new Connection(new Socket(ip, port))) {
            connection.sendMessage(message);

            SimpleMessage fromServer = connection.readMessage();
            System.out.println("от сервера: " + fromServer);
        } catch (IOException e) {
            System.out.println("Ошибка отправки-получения сообщения");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка чтения сообщения");
        } catch (Exception e) {
            System.out.println("Ошибка соединения");
        }
    }
}