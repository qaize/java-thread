package com.example.thread.controller;

import com.example.thread.service.ProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.IntStream;

@Slf4j
@RestController
public class ThreadController {
    
    private final ProcessorService processorService;

    public ThreadController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    // Blocking endpoint (simulasi delay)
    @GetMapping("/blocking")
    public String blocking() throws InterruptedException {
        log.info("[{}] Mulai /blocking", Thread.currentThread().getName());
        Thread.sleep(2000); // simulate slow API
        log.info("[{}] Selesai /blocking", Thread.currentThread().getName());
        return "Response from /blocking";
    }

    // Async endpoint using CompletableFuture
    @GetMapping("/async")
    public CompletableFuture<String> async() {
        log.info("[{}] Menerima request /async", Thread.currentThread().getName());
        return processorService.processAsync();
    }

    @GetMapping("/simulate")
    public String simulateThreadPool() {
        try {
            IntStream.rangeClosed(1, 50).forEach(processorService::processAsyncWithCounter);

        } catch (RejectedExecutionException ex) {
            log.warn("[{}] RejectedExecutionException", Thread.currentThread().getName());
        }
        return "Submitted 17 requests using @Async";
    }
}
