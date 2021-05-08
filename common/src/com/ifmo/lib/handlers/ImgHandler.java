package com.ifmo.lib.handlers;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * Created by User on 26.04.2021.
 */
public class ImgHandler extends FileHandler {

    public ImgHandler(File file) {
        setFile(file);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getExtension() {
        return Optional.of(file.getName())
                .filter(s -> s.contains("."))
                .map(s -> s.substring(file.getName().lastIndexOf(".") + 1))
                .orElse("png");
    }

    @Override
    public boolean writeToFile(byte[] data) throws IOException {
        // Здесь stream закроется по окончанию try
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            BufferedImage bufferedImage = ImageIO.read(stream);
            return ImageIO.write(bufferedImage, getExtension(), file);
        }
    }

    @Override
    public byte[] readFromFile() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file); // Напрямую нельзя прочитать байты, поэтому используем декоратор ByteArrayOutputStream
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, getExtension(), stream);
        return stream.toByteArray();
    }
}