package com.app.docto.mapper;

import com.app.docto.beans.Appointment;
import com.app.docto.beans.DoctorSlot;
import com.app.docto.dao.DoctorRepository;
import com.app.docto.dao.DoctorSlotRepository;
import com.app.docto.dao.SlotRepository;
import com.app.docto.enums.AppointmentStatus;
import com.app.docto.enums.TransactionStatus;
import com.app.docto.exception.ValidationException;
import com.app.docto.models.request.AppointmentRequest;
import com.app.docto.models.request.PaymentRequest;
import com.app.docto.models.response.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@Slf4j
public class AppointmentRequestMapper  implements Mapper<AppointmentRequest, Appointment> {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private DoctorSlotRepository doctorSlotRepository;

    @Autowired
    private RestTemplate restTemplate;

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
        //TODO check payment status
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCurrency("$");
        paymentRequest.setAmount(100.0);
        paymentRequest.setToken("1242");
        PaymentResponse response = restTemplate.postForObject("http://payment-service/payment", paymentRequest, PaymentResponse.class);
        log.info(response.getTransactionId());
        if(response.getTransactionStatus() != TransactionStatus.SUCCESSFUL) {
            throw new ValidationException("Could not complete payment");
        }
        doctorSlot.setAvailable(false);
        appointment.setCancelled(false);
        appointment.setStatus(AppointmentStatus.CLOSED);
        appointment.setDoctorSlot(doctorSlot);
        return appointment;
    }
}
