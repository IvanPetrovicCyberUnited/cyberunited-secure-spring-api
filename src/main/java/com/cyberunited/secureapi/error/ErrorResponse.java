package com.cyberunited.secureapi.error;

import java.time.Instant;

public record ErrorResponse(Instant timestamp, String path, String code, String message, String correlationId) {}
