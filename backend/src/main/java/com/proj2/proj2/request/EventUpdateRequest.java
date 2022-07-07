package com.proj2.proj2.request;

import lombok.Data;

@Data
public class EventUpdateRequest {
    
    private int dueYear;
    private int dueMonth;
    private int dueDay;
    private int dueHour;
    private int dueMin;
    private String text;
    private String title;

}