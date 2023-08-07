package com.example.mediconnect.CloudGeteway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/token",
            "/eureka",
            "/admin/department",
            "user/getAllDoctors",
            "user/getSingleDoctor",

            "/user/getdepartments",
            "/user/getUserProfile",
            "/doctor/getDoctorProfile"

//            "/user/getDoctorByCategory/{departmentName}",
//            "/user/getDoctorByCategory",
//            "/getSingleDoctor/{docId}",
//            "/getSingleDoctor"
    );
    public static final List<String> securedRoutes = List.of(
            "/admin/add/department",
            "/admin/departments",
            "/admin/delete/department/{id}"
            // Add more secured routes after "/admin" as needed
    );
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
