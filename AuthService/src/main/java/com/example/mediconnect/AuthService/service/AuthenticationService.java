package com.example.mediconnect.AuthService.service;


import com.example.mediconnect.AuthService.dto.*;
import com.example.mediconnect.AuthService.entity.UserCredential;
import com.example.mediconnect.AuthService.exception.EmailAlreadyTakenException;
import com.example.mediconnect.AuthService.exception.UsernameAlreadyTakenException;
import com.example.mediconnect.AuthService.kafka.Producer;
import com.example.mediconnect.AuthService.repository.UserCredentialRepository;
import com.example.mediconnect.AuthService.util.EmailUtil;
import com.example.mediconnect.AuthService.util.OtpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class AuthenticationService {
    @Autowired
    private UserCredentialRepository userRepository;
    @Autowired

    private PasswordEncoder passwordEncoder;

    @Autowired
    private  Producer producer;

    @Autowired
    private  JwtService jwtService;
    @Autowired
    private OtpUtil otpUtil;

    @Autowired
    private EmailUtil emailUtil;



     public String saveDoctor(DoctorDetails doctorDetails){
         if (userRepository.existsByEmail(doctorDetails.getEmail())) {
             throw new EmailAlreadyTakenException("Email already taken");
         } else if (userRepository.existsByName(doctorDetails.getName())) {
             throw new UsernameAlreadyTakenException("Username already taken");
         }
          doctorDetails.setIsApproved("pending");

         UserCredential credential = new UserCredential()
                 .builder()
                   .name(doctorDetails.getName())
                    .email(doctorDetails.getEmail())
                      .password(doctorDetails.getPassword())
                      .isApproved(doctorDetails.getIsApproved())
                       .enabled(true)

                      .role(Role.DOCTOR)
                       .build();
//         System.out.println(credential.getRole());

         credential.setPassword(passwordEncoder.encode(credential.getPassword()));
         userRepository.save(credential);
         doctorDetails.setId(credential.getId());
         producer.sendDoctorDetails(doctorDetails);



         return "user Created";
     }

    public OtpDto saveUser(UserData userData) {
        if (userRepository.existsByEmail(userData.getEmail())) {
            throw new EmailAlreadyTakenException("Email already taken");
        }
        if (userRepository.existsByName(userData.getName())) {
            throw new UsernameAlreadyTakenException("Username already taken");
        }
        String otp = otpUtil.generateOtp();
        try {
            System.out.println(otp);
            emailUtil.sendOtpEmail(userData.getEmail(), otp);
        } catch (MessagingException | javax.mail.MessagingException e) {

            throw new RuntimeException("Unable to send otp please try again");
        }


        UserCredential credential = new UserCredential()
                .builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .otp(otp)
                .otpGeneratedTime(LocalDateTime.now())
                .role(Role.USER)
                .build();


        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        userRepository.save(credential);
        userData.setId(credential.getId());
        producer.sendUserData(userData);

        OtpDto otpDto = new OtpDto()
                .builder()
                .id(credential.getId())
                .email(credential.getEmail())
                .status("pending")
                        .build();

        System.out.println(credential.getId());
       return otpDto;
    }

    public boolean verifyAccount(UUID id,OtpDto otpDto) {
        UserCredential user = userRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("User not found with this email: " ));
        System.out.println(user);
        if (user.getOtp().equals(otpDto.getOtp()) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (30 * 60)) {
            System.out.println("otp success");
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }



    public String generateToken(String username,String role) {

        return jwtService.generateToken(username,role);

    }

    public void validateToken(String token) {
         jwtService.validateToken(token);
    }

    public void approveDoctor(ApproveRequest request) {
        Optional<UserCredential> userOptional = userRepository.findById(request.getId());

        if (userOptional.isPresent()) {
            UserCredential user = userOptional.get();
            user.setIsApproved(request.getStatus());
            userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + request.getId());
        }
    }

    public void UnblockDoctorById(DoctorId doctorId) {
         UserCredential userdata =userRepository.findById(doctorId.getId())
                 .orElseThrow(() -> new IllegalArgumentException("User not found with ID: "));
             userdata.setEnabled(true);
             userRepository.save(userdata);
    }

    public void blockDoctorById(DoctorId doctorId) {
        UserCredential userdata =userRepository.findById(doctorId.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: "));
        userdata.setEnabled(false);
        userRepository.save(userdata);

    }

    public void blockUserById(UserId userId) {
        UserCredential userdata =userRepository.findById(userId.getId())

                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: "));
        System.out.println(userdata);
        userdata.setEnabled(false);


        userRepository.save(userdata);
        System.out.println(userRepository.getById(userId.getId()));

    }

    public void UnblockUserById(UserId userId) {
        UserCredential userdata =userRepository.findById(userId.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: "));
        userdata.setEnabled(true);
        userRepository.save(userdata);

    }

//    public void getAllPendingApprovals() {
//        List<UserCredential> doctorCredentials =userRepository.findAll();
//        List<Doctor>doctorResponse = new ArrayList<>();
//
//
//        for(DoctorCredentials doctors:doctorCredentials){
//            Doctor doctor = new Doctor();
//            copyProperties(doctors,doctor);
//            doctorResponse.add(doctor);
//    }
}
