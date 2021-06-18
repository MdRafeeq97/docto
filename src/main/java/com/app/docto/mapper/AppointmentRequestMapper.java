package com.app.docto.mapper;

import com.app.docto.beans.Appointment;
import com.app.docto.beans.DoctorSlot;
import com.app.docto.dao.DoctorRepository;
import com.app.docto.dao.DoctorSlotRepository;
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

import java.util.concurrent.Callable;


@Component
@Slf4j
public class AppointmentRequestMapper implements Mapper<AppointmentRequest, Appointment> {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorSlotRepository doctorSlotRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Appointment mapOne(AppointmentRequest appointmentRequest) {
        Long doctorId = appointmentRequest.getDoctorId();
        DoctorSlot doctorSlot = doctorSlotRepository
                .findByDoctorDoctorIdAndStartTimeAndEndTime(doctorId, appointmentRequest.getStartTime(), appointmentRequest.getEndTime())
                .orElseThrow(() -> new ValidationException("Slot not available for doctor " + doctorId));
        if(!doctorSlot.getAvailable()) {
            throw new ValidationException(String.format("Doctor %s is not available from %s to %s", doctorSlot.getDoctor().getFirstName(),
                    doctorSlot.getStartTime(), doctorSlot.getEndTime()));
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(doctorSlot.getStartTime());
        //TODO check payment status
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCurrency("$");
        paymentRequest.setAmount(100.0);
        paymentRequest.setToken("1242");
        try {
            PaymentResponse response = this.restTemplate.postForObject("http://payment-service/payment", paymentRequest, PaymentResponse.class);
            log.info(response.getTransactionId());
            if(response.getTransactionStatus() != TransactionStatus.SUCCESSFUL) {
                throw new ValidationException("Could not complete payment");
            }
        } catch (Exception e) {
            log.error("Could not execute payment. Details{}", e.getMessage());
        }

        doctorSlot.setAvailable(false);
        appointment.setCancelled(false);
        appointment.setStatus(AppointmentStatus.CLOSED);
        appointment.setDoctorSlot(doctorSlot);
        return appointment;
    }
}
