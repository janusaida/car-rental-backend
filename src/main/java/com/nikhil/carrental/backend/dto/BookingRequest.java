package com.nikhil.carrental.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequest {

    private Long userId;
    private Long carId;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;
}
