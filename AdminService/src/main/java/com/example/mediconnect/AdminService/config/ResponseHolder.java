package com.example.mediconnect.AdminService.config;//package com.example.mediconnect.AdminService.config;
import com.example.mediconnect.AdminService.dto.Userdto;

import java.util.List;
import java.util.concurrent.CompletableFuture;
//public class ResponseHolder {
//
//    private static CompletableFuture<Userdto> completableFuture;
//
//    public static void setCompletableFuture(CompletableFuture<Userdto> future) {
//        completableFuture = future;
//    }
//
//    public static void setResponse(Userdto response) {
//        completableFuture.complete(response);
//    }
//}
public class ResponseHolder {
    private static CompletableFuture<List<Userdto>> completableFuture;

    public static void setCompletableFuture(CompletableFuture<List<Userdto>> future) {
        completableFuture = future;
    }

    public static void setResponse(List<Userdto> response) {
        completableFuture.complete(response);
    }
}
