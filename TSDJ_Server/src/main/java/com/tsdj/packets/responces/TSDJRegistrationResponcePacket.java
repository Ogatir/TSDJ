package com.tsdj.packets.responces;

import com.tsdj.packets.AbstractResponcePacket;

public class TSDJRegistrationResponcePacket extends AbstractResponcePacket {

    public TSDJRegistrationResponcePacket(int responceCode ,String responceMessage)
    {
        this.responceCode=responceCode;
        this.responceMessage=responceMessage;
        setSendingTime();
    }


}
