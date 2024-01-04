package com.example.async;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class AsyncService {

    private final Executor taskExecutor;

    public AsyncService(@Qualifier("taskExecutor") Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Async("taskExecutor")
    public CompletableFuture<String> performAdvancedAsyncOperation() {
        try {
            // Simulate long-running operation
            Thread.sleep(5000);
            return CompletableFuture.completedFuture("Advanced Operation Completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        }
    }
}
