package com.example.MeetingRequestDemo.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminBookingActionDTO {
    @JsonProperty("admin")
    @NotNull(message = "admin name or email must not be null.")
    private String admin;

    @JsonProperty("adminComment")
    @NotNull(message = "comment must not be null.")
    @Size(min = 3, message = "comment must be 3 characters at least")
    private String adminComment;

    @JsonProperty("status")
    @NotNull(message = "status must not be null.")
    private String status;
}

