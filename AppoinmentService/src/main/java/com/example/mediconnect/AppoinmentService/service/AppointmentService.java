package com.example.mediconnect.AppoinmentService.service;

import com.example.mediconnect.AppoinmentService.dto.AppointmentCanceldto;
import com.example.mediconnect.AppoinmentService.dto.AppointmentReq;
import com.example.mediconnect.AppoinmentService.dto.AppointmentStatus;
import com.example.mediconnect.AppoinmentService.entity.Appointment;
import com.example.mediconnect.AppoinmentService.kafka.Producer;
import com.example.mediconnect.AppoinmentService.repository.AppointmentRepository;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.apache.tomcat.util.net.openssl.ciphers.MessageDigest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
     @Autowired
    private RazorpayClient razorpayClient;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private Producer producer;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;


    @Value("${razorpay.secret.id}")
    private String razorpayId;



    public void appointmentBooking(AppointmentReq appointmentReq) {
        System.out.println(appointmentReq.getDocId());
        System.out.println(appointmentReq.getUserId());



         Appointment appointment = Appointment.builder()
                 .userId(appointmentReq.getUserId().toString())
                 .doctorId(appointmentReq.getDocId().toString())
                 .userInfo(appointmentReq.getUserInfo())
                 .doctorInfo(appointmentReq.getDoctorInfo())
                 .status("pending")
                 .amount(appointmentReq.getAmount())
                 .paymentStatus("pending")
                 .date(appointmentReq.getDate())
                 .time(appointmentReq.getTime())
                 .build();

//        appointmentRepository.save(appointment);

        System.out.println(appointment.getUserId()+"  ______"+appointment.getDoctorId());

        Appointment savedAppointment = appointmentRepository.save(appointment);
       long amount = appointment.getAmount();

        Map<String, Object> response = null;
           response = generateRazorpay(savedAppointment.getId(), amount);



        System.out.println(response);
       producer.sendBookingRes(response);

    }


    private Map<String, Object> generateRazorpay(UUID id, long amount) {
        System.out.println("in generate rpay");
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println(id);
            System.out.println(amount);
            Order order = razorpayClient.orders.create(new JSONObject()
                    .put("amount", amount * 100)
                    .put("currency", "INR")
                    .put("receipt", id)
                    .put("notes", new JSONObject()
                            .put("key1", "value3")
                            .put("key2", "value2"))
            );

            response.put("status", true);
            response.put("order", order);

        } catch (RazorpayException e) {
            response.put("status", "Failed");
            response.put("message", e.getMessage());
        }
        System.out.println(response+"_+_+_+_+_+_");
        return response;
    }





    public String verifyPayment(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> res = (Map<String, Object>) requestBody.get("res");
        String paymentId = (String) res.get("razorpay_payment_id");
        String orderId = (String) res.get("razorpay_order_id");
        String signature = (String) res.get("razorpay_signature");

        Map<String, Object> order = (Map<String, Object>) requestBody.get("order");
        int amount = (int) order.get("amount");
        int amountPaid = (int) order.get("amount_paid");
        int amountDue = (int) order.get("amount_due");
        String currency = (String) order.get("currency");
        String receipt = (String) order.get("receipt");
        String orderId2 = (String) order.get("id");
        String entity = (String) order.get("entity");
        String offerId = (String) order.get("offer_id");
        String status = (String) order.get("status");
        int attempts = (int) order.get("attempts");


        try {

            String data = orderId + "|" + paymentId;


            String generatedSignature = generateHmacSHA256Signature(data, razorpayApiKey);
            System.out.println(generatedSignature);
            System.out.println(signature);

            if (generatedSignature.equals(signature)) {
                System.out.println("hello");
                // Signature is valid, process the payment and change status

                 boolean paymentstatus=changePaymentStatus(receipt);
                if (paymentstatus) {
                    System.out.println("sucess of the payment");
                    return "success";
                }else{
                    System.out.println("simply failed");
                    return "failed";

                }
            } else {
                // Signature is invalid
                return "Invalid payment signature";
            }
        } catch (Exception e) {
            return "Error verifying payment";
        }
    }

    private boolean changePaymentStatus( String id) {
        System.out.println("which id"+id);



         Appointment payment =appointmentRepository.getById(UUID.fromString(id));
        System.out.println(payment);
         payment.setPaymentStatus("Completed");
             appointmentRepository.save(payment);
        System.out.println(payment.getPaymentStatus());
       if(payment.getPaymentStatus()=="Completed"){
           return true;
       }

        return false;
        // ...
    }

    private String generateHmacSHA256Signature(String data, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(secretKeySpec);
        byte[] signatureBytes = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(signatureBytes);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            String hexString = Integer.toHexString(0xFF & b);
            if (hexString.length() == 1) {
                hexStringBuilder.append('0');
            }
            hexStringBuilder.append(hexString);
        }
        return hexStringBuilder.toString();
    }


    public void viewAppointments(String userId) {



//
//        System.out.println(userId);
        List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);

        producer.getAppointments(appointments);
        System.out.println("-----");
        System.out.println(appointments);
        System.out.println("------");
    }

    public void cancelAppointment(AppointmentCanceldto appointmentCanceldto) {
         Appointment appointmentdetails = appointmentRepository.findById(appointmentCanceldto.getAppointmentId()).orElse(null);
        System.out.println(appointmentdetails.getPaymentStatus()+""+appointmentdetails.getStatus()+"+++++"+appointmentdetails.getId());
        if (appointmentdetails != null && "pending".equals(appointmentdetails.getStatus()) && "Completed".equals(appointmentdetails.getPaymentStatus())){


         appointmentRepository.delete(appointmentdetails);

             producer.cancelAppointmentRes();

        }

    }

    public void getAppointmentRequest(String doctorId) {
        List<Appointment> appointmentRequests = appointmentRepository.findAllByDoctorId(doctorId);

        producer.getAppointmentRequests(appointmentRequests);
        System.out.println("-----");
        System.out.println(appointmentRequests);

    }

    public String updateAppointmentStatus(AppointmentStatus appointmentStatus) {
        System.out.println("hello");

        UUID id = appointmentStatus.getAppointmentId();
        Appointment appointmentdata=appointmentRepository.findById(appointmentStatus.getAppointmentId()).orElse(null);
        appointmentdata.setStatus(appointmentStatus.getStatus());
        appointmentRepository.save(appointmentdata);
        List<Appointment> appointmentRequests = appointmentRepository.findAll();
        System.out.println("+_+_+_+"+appointmentRequests);
        return "Appointment status updated";

    }


    public void getTodaysAppointment(String doctorId) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println(today);
        List<Appointment> appointmentRequest = appointmentRepository.findAllByDoctorId(doctorId);


        List<Appointment> todaysAppointments =appointmentRequest.stream()
                .filter(appointment -> {
                    LocalDate appointmentDate = LocalDate.parse(appointment.getDate(), formatter);
                    System.out.println(appointmentDate);
                    return  appointmentDate.equals(today);
                })
                .collect(Collectors.toList());
        producer.getTodaysAppointments(todaysAppointments);
    }

    public void getAppointmentTimes(String doctorId) {
        List<Appointment> appointmentTimes = appointmentRepository.findAllByDoctorId(doctorId);

        producer.getAppointmentTime(appointmentTimes);
    }


}
