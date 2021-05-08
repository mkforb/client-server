package com.ifmo.lib.handlers;

import java.io.File;
import java.io.IOException;

/**
 * Created by User on 26.04.2021.
 */
abstract public class FileHandler {
    protected File file;

    // Если в родительском классе методы выбрасывют исключения в сигнатуре,
    // то в дочерних классах в реализации этих методов они могут выбрасывать эти исключения,
    // а могут не выбрасывать.
    // Если в сигнатуре родительского класса нет исключения, то и в дочернем его быть не может.
    abstract public boolean writeToFile(byte[] data) throws IOException;
    abstract public byte[] readFromFile() throws IOException;
}