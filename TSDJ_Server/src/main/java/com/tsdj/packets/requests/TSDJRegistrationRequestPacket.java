package com.tsdj.packets.requests;
import com.tsdj.packets.AbstractPacket;

public class TSDJRegistrationRequestPacket extends AbstractPacket {

    private String TSDJ_Name;
    private String region;
    private String location;
    private String address;
    private String head;
    private int totalMembers;

    public TSDJRegistrationRequestPacket(){
        setReceivingTime();
    }

    public String getTSDJ_Name() { return TSDJ_Name; }
    public String getRegion() {
        return region;
    }
    public String getLocation(){
        return location;
    }
    public String getAddress() {
        return address;
    }
    public String getHead() { return head; }
    public int getTotalMembers(){
        return totalMembers;
    }


}
