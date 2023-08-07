package com.example.mediconnect.AuthService.controller;



import ch.qos.logback.core.util.CachingDateFormatter;
import com.example.mediconnect.AuthService.dto.*;
import com.example.mediconnect.AuthService.entity.UserCredential;
import com.example.mediconnect.AuthService.exception.EmailAlreadyTakenException;
import com.example.mediconnect.AuthService.exception.InvalidLoginException;
import com.example.mediconnect.AuthService.repository.UserCredentialRepository;
import com.example.mediconnect.AuthService.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.beans.BeanUtils.copyProperties;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Component
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AuthenticationManager authenticationManager;
  @Autowired
    private UserCredentialRepository userCredentialRepository;


    @Autowired
    private JavaMailSender javaMailSender;



    @PostMapping("/doctor/register")
    public String saveDoctor(@RequestBody DoctorDetails doctorDetails){


        return authenticationService.saveDoctor(doctorDetails);

    }

    @PostMapping("/user/register")
    public  ResponseEntity<Map<String, String>> saveUser(@RequestBody UserData userData){
        OtpDto otpDto=authenticationService.saveUser(userData);
        Map<String, String> response = new HashMap<>();
        response.put("status", "pending");
        response.put("id", otpDto.getId().toString());
        response.put("email", otpDto.getEmail().toString());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//@GetMapping("/authenticate")
//    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
//     return jwtService.generateToken(authRequest.getUsername());
//    }
//@PostMapping("/token")
//    public String getToken(UserCredential userCredential){
//    return  authenticationService.generateToken(userCredential.getName());
//    }
@PostMapping("/admin/login")
public ResponseEntity<Map<String, Object>> adminLogin(@RequestBody AuthRequest authRequest) {
    System.out.println(authRequest);
    UserCredential user = userCredentialRepository.findByName(authRequest.getUsername())
            .orElseThrow(() -> new NoSuchElementException("User not found"));



    if (user == null) {
        throw new InvalidLoginException("Invalid user");
    }



    if (user.getRole()!= Role.ADMIN) {
        System.out.println("helo");


        throw new InvalidLoginException("Invalid users");
    }


    String token = null;
    Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    if (authenticate.isAuthenticated()) {
        System.out.println(authenticate.isAuthenticated()+"success");
        token = authenticationService.generateToken(authRequest.getUsername(), user.getRole().toString());
        System.out.println(token);
        UserResponse userResponse = new UserResponse();
        copyProperties(user,userResponse);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("adminExists", userResponse);

        System.out.println(user);


        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    } else {
        System.out.println("sss");

        throw new InvalidLoginException("invalid user");
    }
}





    @PostMapping("/doctor/login")
    public ResponseEntity<Map<String,Object>>doctorLogin(@RequestBody AuthRequest authRequest) {

        UserCredential user = userCredentialRepository.findByName(authRequest.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found"));



        if (user == null) {
            throw new InvalidLoginException("Invalid user");
        }

        if (!user.isEnabled()) {
            throw new InvalidLoginException("Your Account is Blocked");
        }
        if (user.getIsApproved().equals("pending")) {

            throw new InvalidLoginException("User is Not approved");
        }

        if (user.getRole()!= Role.DOCTOR) {


            throw new InvalidLoginException("Invalid users");
        }


        String token = null;
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            token = authenticationService.generateToken(authRequest.getUsername(), user.getRole().toString());

                  UserResponse userResponse = new UserResponse();
                  copyProperties(user,userResponse);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("doctorExists",userResponse);

            System.out.println(user);


            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } else {

             throw new InvalidLoginException("invalid user");
        }
    }



    @PostMapping("/user/login")
    public ResponseEntity<Map<String,Object>> userLogin(@RequestBody AuthRequest authRequest) {

        UserCredential user = userCredentialRepository.findByName(authRequest.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found"));



        if (user == null) {
            throw new InvalidLoginException("Invalid user");
        }

        if (!user.isEnabled()) {
            throw new InvalidLoginException("User is blocked");
        }


        if (user.getRole()!= Role.USER) {


            throw new InvalidLoginException("Invalid users");
        }


        String token = null;
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            token = authenticationService.generateToken(authRequest.getUsername(), user.getRole().toString());
            System.out.println(token);

            UserResponse userResponse = new UserResponse();
            copyProperties(user,userResponse);
            Map<String, Object> response = new HashMap<>();

            response.put("token", token);
            response.put("userExists",userResponse);

            System.out.println(user.toString());


            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } else {

            throw new InvalidLoginException("invalid user");
        }

    }





    @GetMapping("/validate")
    public String validateToken(@RequestParam("token")String token){

     authenticationService.validateToken(token);
     return "token is valid";
    }
//    @GetMapping("/user")
//    public String getUserDetails(@AuthenticationPrincipal UserInfoUserDetails userDetails) {
//        if (userDetails != null) {
//            String username = userDetails.getUsername();
//
//            // Retrieve user details from the repository or perform any necessary actions
//            UserCredential userCredential = userCredentialRepository.findByName(username).orElse(null);
//            if (userCredential != null) {
//                // Return the user details or specific information
//                return "User: " + username;
//            } else {
//                return "User not found";
//            }
//        } else {
//            return "Invalid authentication";
//        }
//    }
//


//    @GetMapping("/user")
//    public String getUserDetails() {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        String authorizationHeader = request.getHeader("Authorization");
//        String token = authorizationHeader.substring(7); // Assuming "Bearer " prefix is present
//
//
//        Claims claims = Jwts.parser().setSigningKey("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437").parseClaimsJws(token).getBody();
//        String username = claims.getSubject();
//        String  role = (String) claims.get("role");
//        System.out.println(role);
//        // Access other user data from the claims
//        // ...
//        Optional<UserCredential> user= userCredentialRepository.findByName(username);
//        System.out.println(user.get().getId());
//
//        return "User: " + username;
//    }
//    @GetMapping("/user")
//    public String getUserDetails(HttpServletRequest request) {
//        System.out.println(request.getCookies());
//
//        String authorizationHeader = request.getHeader("Authorization");
//        String token = authorizationHeader.substring(7); // Assuming "Bearer " prefix is present
//
//        Claims claims = Jwts.parser().setSigningKey("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437").parseClaimsJws(token).getBody();
//        String username = claims.getSubject();
//        // Access other user data from the claims
//        // ...
//        Optional<UserCredential> user= userCredentialRepository.findByName(username);
//        System.out.println(user.get().getId());
//
//        return "User: " + username;
//    }

//    @GetMapping("/user")
//    public String getUserDetails(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader("Authorization");
//        String token = authorizationHeader.substring(7); // Assuming "Bearer " prefix is present
//
//        Claims claims = Jwts.parser().setSigningKey("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437").parseClaimsJws(token).getBody();
//        String username = claims.getSubject();
//        // Access other user data from the claims
//        // ...
//
//        Optional<UserCredential> user = userCredentialRepository.findByName(username);
//        if (user.isPresent()) {
//            UserCredential userCredential = user.get();
//            int userId = userCredential.getId();
//
//            // Perform any necessary actions with the user ID
//            System.out.println(userId);
//
//            return "User: " + username;
//        } else {
//            // Handle the case when the user is not found
//            return "User not found";
//        }
//    }


    @PostMapping("/verifyotp/{id}")
    public ResponseEntity<Map<String, Boolean>> verifyAccount(@PathVariable("id") UUID id,@RequestBody OtpDto otpDto) {

        boolean res=authenticationService.verifyAccount(id, otpDto);
        Map<String, Boolean> response = new HashMap<>();

        response.put("verified", res);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
