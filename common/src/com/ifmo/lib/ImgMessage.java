package com.ifmo.lib;

/**
 * Created by User on 07.05.2021.
 */
public class ImgMessage extends SimpleMessage {
    private byte[] bytes;
    private String extension = "png";

    public ImgMessage(String sender, String text, byte[] bytes) {
        super(sender, text);
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
