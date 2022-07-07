package com.proj2.proj2.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailResponse {
    long id;
    long adminId;
    String adminUsername;
    long invitedUserId;
    String invitedUsername;
    long eventId;
    String mailinfo;
}
