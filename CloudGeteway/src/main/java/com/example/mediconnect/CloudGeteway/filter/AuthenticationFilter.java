package com.example.mediconnect.CloudGeteway.filter;




import com.example.mediconnect.CloudGeteway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

//        @Autowired
//    private RestTemplate template;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CacheManager cacheManager;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

//            ServerWebExchange modifiedExchange = exchange.mutate()
//                    .request(request -> {
//                        // Print the request details
//                        System.out.println("Request Method: " + request);
////                        System.out.println("Request URI: " + request.getURI());
////                        HttpHeaders headers = request.headers();
////                        System.out.println("Request Headers: " + headers);
////                        System.out.println("Request Body: " + request.getBody());
//                    })
//                    .build();


//            ServerWebExchange modifiedExchange = exchange
//                    .mutate()
//                    .request(exchange.getRequest())
//                    .build();
//
//            modifiedExchange.getRequest()
//                    .getBody()
//                    .doOnNext(body -> {
//                        // Print the request details
//                        System.out.println("Request Method: " + modifiedExchange.getRequest().getMethodValue());
//                        System.out.println("Request URI: " + modifiedExchange.getRequest().getURI());
//                        HttpHeaders headers = modifiedExchange.getRequest().getHeaders();
//                        System.out.println("Request Headers: " + headers);
//                        System.out.println("Request Body: " + body);
//                    })
//                    .subscribe();



            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                // Check if the token is already cached
                Boolean isValidToken = cacheManager.getCache("tokenCache").get(authHeader, Boolean.class);
                System.out.println("chach_"+isValidToken);
                if (isValidToken != null && isValidToken) {
                    System.out.println("success cache");
                    System.out.println("_____________");
                    // Token is valid, continue with the request
                    return chain.filter(exchange);
                }


                String roles = null;
                try {
                    //REST call to AUTH service
//                    template.getForObject("http://AUTH-SERVICE//validate?token" + authHeader, String.class);
                    jwtUtil.validateToken(authHeader);


                    System.out.println(authHeader);

//                     Extract the user's roles from the token
                    Claims claims = jwtUtil.extractClaimsFromToken(authHeader);
                    System.out.println(claims);
//

                    roles = (String) claims.get("role");
                    System.out.println(roles);

                    // Check if the user has the required role for accessing the route
                    String routePath = exchange.getRequest().getURI().getPath();
                    if (routePath.startsWith("/user/admin")) {
                        if (!roles.contains("ADMIN")) {
                            throw new RuntimeException("Unauthorized access. Required role: ADMIN");
                        }
                    } else if (routePath.startsWith("/user/doctor")) {
                        if (!roles.contains("DOCTOR")) {
                            throw new RuntimeException("Unauthorized access. Required role: DOCTOR");
                        }
                    } else if (routePath.startsWith("/user")) {
                        if (!roles.contains("USER")) {
                            throw new RuntimeException("Unauthorized access. Required role: DOCTOR");
                        }
                    }
                    cacheManager.getCache("tokenCache").put(authHeader, true);

                } catch (Exception e) {
                    System.out.println("invalid access...!" + roles);
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
