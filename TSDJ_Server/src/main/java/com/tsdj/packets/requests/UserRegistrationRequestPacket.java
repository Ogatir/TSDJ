package com.tsdj.packets.requests;

import com.tsdj.packets.AbstractPacket;

public class UserRegistrationRequestPacket extends AbstractPacket {

    private String surname;
    private int apartment;
    private String TSDJ_Name;
    private String TSDJ_Region;
    private String TSDJ_Location;
    private String TSDJ_Address;

    public UserRegistrationRequestPacket(){
        setReceivingTime();
    }

    public String getSurname(){ return surname; }
    public String getTSDJ_Name() { return TSDJ_Name; }
    public String getTSDJ_Region() { return TSDJ_Region; }
    public String getTSDJ_Location() { return TSDJ_Location; }
    public String getTSDJ_Address() { return TSDJ_Address; }
    public int getApartment(){ return apartment; }



}
