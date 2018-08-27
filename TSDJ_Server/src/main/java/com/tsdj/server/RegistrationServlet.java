package com.tsdj.server;

import com.google.gson.Gson;
import com.tsdj.packets.requests.UserRegistrationRequestPacket;
import com.tsdj.packets.requests.TSDJRegistrationRequestPacket;
import com.tsdj.packets.responces.TSDJRegistrationResponcePacket;
import com.tsdj.packets.responces.UserRegistrationResponcePacket;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.stream.Collectors;

public class RegistrationServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

    }
    @Override
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        String requestBody = httpServletRequest.getReader()
                .lines().collect(Collectors.joining(System.lineSeparator()));
        httpServletResponse.setCharacterEncoding("UTF-8");
        if (httpServletRequest.getParameter("dispatcher").equals("TSDJ")) {
            Gson gson = new Gson();
            TSDJRegistrationRequestPacket regPacket = gson.fromJson(requestBody, TSDJRegistrationRequestPacket.class);
            try {
                String searchForSameTSDJQuery = String.format("SELECT * FROM TSDJs WHERE " +
                        "`Region`='%s' AND `Location`='%s' AND `Address`='%s';",
                        regPacket.getRegion(),
                        regPacket.getLocation(),
                        regPacket.getAddress());
                TSDJRegistrationResponcePacket responcePacket;
                ResultSet rs = DatabaseConnector.Execute_SELECT(searchForSameTSDJQuery);
                if (!rs.next()) {
                    String registrationQuery = String.format("INSERT INTO `TSDJs` " +
                                    "(`TSDJ Name`, `Region`, `Location` " +
                                    ",`Address`, `Head`, `Total members`) " +
                                    "VALUES ('%s', '%s', '%s', '%s', '%s', %d);",
                            regPacket.getTSDJ_Name(),
                            regPacket.getRegion(),
                            regPacket.getLocation(),
                            regPacket.getAddress(),
                            regPacket.getHead(),
                            regPacket.getTotalMembers());
                    DatabaseConnector.Execute_INSERT(registrationQuery);
                    responcePacket = new TSDJRegistrationResponcePacket(0,
                            "Успешная регистрация ТСЖ");
                } else {
                    responcePacket = new TSDJRegistrationResponcePacket(2,
                            "Данное ТСЖ уже зарегистрировано");
                }
                httpServletRequest.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json");
                String resp = gson.toJson(responcePacket);
                httpServletResponse.getWriter().print(gson.toJson(responcePacket));
            } catch (Exception e) {
                e.printStackTrace();
                TSDJRegistrationResponcePacket responcePacket
                        = new TSDJRegistrationResponcePacket(1,
                        "Произошла неожиданная ошибка при регистрации");
                httpServletRequest.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().print(gson.toJson(responcePacket));
            }
        } else if (httpServletRequest.getParameter("dispatcher").equals("User")){
            Gson gson = new Gson();
            UserRegistrationRequestPacket regPacket = gson.fromJson(requestBody, UserRegistrationRequestPacket.class);
            try {
                String  DB_request = String.format("SELECT `id_tsdj` FROM `TSDJs` " +
                                "WHERE `Region` = '%s' and " +
                        "`Location` = '%s' AND `Address` = '%s';",
                        regPacket.getTSDJ_Region(),
                        regPacket.getTSDJ_Location(),
                        regPacket.getTSDJ_Address());
                ResultSet rs1 = DatabaseConnector.Execute_SELECT(DB_request);
                UserRegistrationResponcePacket responcePacket;
                if (rs1.next()) {
                    String registrationQuery = String.format("INSERT INTO `Users` " +
                                    "(`Apartment`, `Surname`, `Registrated`, `users_id_tsdj`)" +
                                    "VALUES ('%s', '%s', CURRENT_DATE, '%s')",
                            regPacket.getApartment(),
                            regPacket.getSurname(),
                            rs1.getString("id_tsdj"));
                    DB_request = String.format("SELECT `idUser` FROM `Users` " +
                            "WHERE `Apartment` = '%s';",
                            regPacket.getApartment());
                    rs1 = DatabaseConnector.Execute_SELECT(DB_request);
                    if (!rs1.next()){
                        DatabaseConnector.Execute_INSERT(registrationQuery);
                        responcePacket = new UserRegistrationResponcePacket(0,
                                "Успешная регистрация члена ТСЖ");
                    } else {
                        responcePacket = new UserRegistrationResponcePacket(4,
                                "Жильцы данной квартиры уже зарегистрированы");
                    }
                } else {
                    responcePacket = new UserRegistrationResponcePacket(3,
                            "Данного ТСЖ не существует");
                }
                httpServletRequest.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().print(gson.toJson(responcePacket));
            } catch (Exception e){
                e.printStackTrace();
                UserRegistrationResponcePacket responcePacket
                        = new UserRegistrationResponcePacket(1,
                        "Произошла неожиданная ошибка при регистрации пользователя");
                httpServletRequest.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().print(gson.toJson(responcePacket));
            }
        }

    }


}

