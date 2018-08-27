package com.tsdj.packets.responces;

import java.util.Date;

public class HelloPacket {
    private String message = "Hello Packet!";
    private String  generatedOn;
    public HelloPacket(){
        Date date = new Date();
        generatedOn = date.toString();
    }
}
