package com.app.docto.models.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DoctorReviewReq {
    @NotNull(message = "appointment should be selected")
    private Long appointmentId;

    @NotNull(message = "Review stars must be added")
    private Integer stars;
}
