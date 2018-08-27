package com.tsdj.packets.responces;

import com.tsdj.packets.AbstractResponcePacket;

public class UserRegistrationResponcePacket extends AbstractResponcePacket {

    public UserRegistrationResponcePacket(int responceCode ,String responceMessage)
    {
        this.responceCode=responceCode;
        this.responceMessage=responceMessage;
        setSendingTime();
    }

}
