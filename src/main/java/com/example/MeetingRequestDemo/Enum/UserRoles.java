package com.example.MeetingRequestDemo.Enum;

public enum UserRoles {
    ADMINISTRATOR, ADMIN, HOD, USER;

    public String getRoleName() {
        return this.name();
    }
}
