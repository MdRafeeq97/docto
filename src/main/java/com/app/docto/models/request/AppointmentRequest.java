package com.app.docto.models.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AppointmentRequest {
    @NotNull(message = "doctor id cannt be null")
    private Long doctorId;

    @NotNull(message = "slot id cannot be null")
    private Long slotId;

}
