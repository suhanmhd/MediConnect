package com.example.mediconnect.AdminService.config;//package com.example.mediconnect.AdminService.config;
import com.example.mediconnect.AdminService.dto.Userdto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@Component
public class ResponseHolder<T> {
    private CompletableFuture<T> completableFuture;

    public void setCompletableFuture(CompletableFuture<T> future) {
        completableFuture = future;
    }

    public CompletableFuture<T> getCompletableFuture() {
        return completableFuture;
    }

    public void setResponse(T response) {
        completableFuture.complete(response);
    }
}


//public class ResponseHolder {
//    private static CompletableFuture<List<Userdto>> completableFuture;
//
//    public static void setCompletableFuture(CompletableFuture<List<Userdto>> future) {
//        completableFuture = future;
//    }
//
//    public static void setResponse(List<Userdto> response) {
//        completableFuture.complete(response);
//    }
//}
