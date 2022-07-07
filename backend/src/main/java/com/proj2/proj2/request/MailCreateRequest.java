package com.proj2.proj2.request;

import lombok.Data;

@Data
public class MailCreateRequest {
    
    private String mailinfo;
    private long adminId;
    private long invitedUserId;
    private long eventId;
}
