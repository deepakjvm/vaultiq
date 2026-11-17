package io.github.deepakjvm.vaultiq.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

import io.github.deepakjvm.vaultiq.dto.GeolocationResponse;

public interface GeolocationService {

    @Async
    public CompletableFuture<GeolocationResponse> getGeolocation(String ip);
}
