package com.tsdj.packets.responces;

import java.util.Date;

public class PostRespPacket {
    private String message = "Post Packet!";
    private String  generatedOn;
    public PostRespPacket(){
        Date date = new Date();
        generatedOn = date.toString();
    }
}
