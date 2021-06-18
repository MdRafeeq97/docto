package com.app.docto.models.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Slot {
    @NotNull(message = "start time cannot be empty")
    private Date startTime;

    @NotNull(message = "endtime cannot be empty")
    private Date endTime;
}
