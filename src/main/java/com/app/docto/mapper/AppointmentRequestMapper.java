package com.app.docto.mapper;

import com.app.docto.beans.Appointment;
import com.app.docto.beans.DoctorSlot;
import com.app.docto.dao.DoctorRepository;
import com.app.docto.dao.DoctorSlotRepository;
import com.app.docto.dao.SlotRepository;
import com.app.docto.enums.AppointmentStatus;
import com.app.docto.exception.ValidationException;
import com.app.docto.models.request.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AppointmentRequestMapper  implements Mapper<AppointmentRequest, Appointment> {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private DoctorSlotRepository doctorSlotRepository;

    @Override
    public Appointment mapOne(AppointmentRequest appointmentRequest) {
        Long doctorId = appointmentRequest.getDoctorId();
        Long slotId = appointmentRequest.getSlotId();
        DoctorSlot doctorSlot = doctorSlotRepository.findByDoctorDoctorIdAndSlotSlotId(doctorId, slotId)
                .orElseThrow(() -> new ValidationException("Slot not available for doctor " + doctorId));
        if(!doctorSlot.getAvailable()) {
            throw new ValidationException(String.format("Doctor %s is not available from %s to %s", doctorSlot.getDoctor().getFirstName(),
                    doctorSlot.getSlot().getStartTime(), doctorSlot.getSlot().getEndTime()));
        }
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(doctorSlot.getSlot().getStartTime());
        doctorSlot.setAvailable(false);
        appointment.setCancelled(false);
        //TODO check payment status
        appointment.setStatus(AppointmentStatus.CLOSED);
        appointment.setDoctorSlot(doctorSlot);
        return appointment;
    }
}
