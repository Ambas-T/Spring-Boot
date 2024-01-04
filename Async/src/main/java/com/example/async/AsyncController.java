package com.example.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CompletableFuture;

@RestController
public class AsyncController {

    final private AsyncService asyncService;

    @Autowired
    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping("/async")
    public CompletableFuture<ResponseEntity<String>> advancedAsyncEndpoint() {
        return asyncService.performAdvancedAsyncOperation()
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> ResponseEntity.internalServerError().body("Error: " + e.getMessage()));
    }
}

