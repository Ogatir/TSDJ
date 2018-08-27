package com.tsdj.server;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.stream.Collectors;
import javax.servlet.http.*;

import com.google.gson.*;
import com.tsdj.packets.responces.HelloPacket;
import com.tsdj.packets.responces.PostRespPacket;

public class HelloServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        Gson gson = new Gson();
        HelloPacket packet = new HelloPacket();
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().print(gson.toJson(packet));
        try {
            /*InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/TSDJ");
            Connection c = ds.getConnection();
            Statement stmt = c.createStatement();
            String query = "select * from TSDJs where id_tsdj=1 ;" ;*/
            ResultSet rs = DatabaseConnector.Execute_SELECT("select * from TSDJs where id_tsdj=1 ;");
            rs.next();
            System.out.println("Success!");
            System.out.println(rs.getString("address"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        Gson gson = new Gson();
        PostRespPacket packet = new PostRespPacket();
        String body = httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().print(gson.toJson(packet)+body);
    }
}
