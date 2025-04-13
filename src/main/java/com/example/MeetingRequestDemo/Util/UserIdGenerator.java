package com.example.MeetingRequestDemo.Util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class UserIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) {
        String prefix = "U";
        try {
//            PostgreSQL , MySQL
//            String query = "SELECT * from tblUsers ORDER BY userID DESC LIMIT 1";
//            SQL
            String query = "SELECT TOP 1 userID FROM tblUsers ORDER BY userID DESC";
            String lastId = session.createNativeQuery(query).getSingleResult().toString();
            if (lastId != null && lastId.startsWith(prefix)) {
                int nextID = Integer.parseInt(lastId.substring(1)) + 1;
                return prefix + String.format("%03d", nextID);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "U001";
    }
}
