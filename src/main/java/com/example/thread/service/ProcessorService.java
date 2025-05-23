package com.example.thread.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ProcessorService {

    @Async
    public CompletableFuture<String> processAsync() {
        log.info("[{}] Mulai kerja async", Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // Simulasi delay
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        log.info("[{}] Selesai kerja async", Thread.currentThread().getName());
        return CompletableFuture.completedFuture("Response from /async");
    }

    @Async
    public CompletableFuture<Void> processAsyncWithCounter(Integer counter) {
        log.info("[{}] Mulai kerja async {}", Thread.currentThread().getName(),counter);
        try {
            Thread.sleep(1000); // Simulasi delay
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("[{}] Selesai kerja async {}", Thread.currentThread().getName(),counter);
        return CompletableFuture.completedFuture(null);
    }
}
