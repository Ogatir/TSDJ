package com.tsdj.packets;

import java.util.Date;

public abstract class AbstractPacket {

    private String sendingTime;
    private String receivingTime;
    private String requestMethod;

    public void setSendingTime() {
        this.sendingTime = new Date().toString();
    }

    public void setReceivingTime() {
        this.receivingTime = new Date().toString();
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public String getReceivingTime() {
        return receivingTime;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

}
