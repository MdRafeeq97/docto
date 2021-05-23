package com.app.docto.beans;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class DoctorSlotId implements Serializable {
    @Column(name = "doctorid")
    private Long doctorId;

    @Column(name = "slotid")
    private Long slotId;

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || this.getClass() != o.getClass())
            return false;

        DoctorSlotId doctorSlotId = (DoctorSlotId) o;
        return Objects.equals(this.doctorId, doctorSlotId.getDoctorId()) &&
                Objects.equals(this.slotId, doctorSlotId.getSlotId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, slotId);
    }
}
