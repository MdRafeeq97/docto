package com.app.docto.models.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AppointmentRequest {
    @NotNull(message = "doctor id cannt be null")
    private Long doctorId;

    @NotNull(message = "start time cannot be null")
    private Date startTime;

    @NotNull(message = "end time cannot be null")
    private Date endTime;

}
