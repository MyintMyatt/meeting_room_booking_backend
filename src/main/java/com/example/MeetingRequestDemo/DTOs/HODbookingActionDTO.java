package com.example.MeetingRequestDemo.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HODbookingActionDTO {

    @JsonProperty("HOD")
    @NotNull(message = "hod name or email must not be null.")
    private String HOD;


    @JsonProperty("HODcomment")
    @NotNull(message = "hod comment must not be null.")
    @Size(min = 3, message = "HOD comment must be 3 characters at least")
    private String HODcomment;

    @JsonProperty("status")
    @NotNull(message = "status must not be null.")
    private String status;
}
