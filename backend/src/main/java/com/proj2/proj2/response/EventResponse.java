package com.proj2.proj2.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventResponse {
    
    private long id;
    private String title;
    private LocalDateTime sent;
    private LocalDateTime due;
    private String text;
    private long adminid;
    private String adminUsername;
    private boolean isMonthly;
}