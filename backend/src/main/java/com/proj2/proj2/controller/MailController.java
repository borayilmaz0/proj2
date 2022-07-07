package com.proj2.proj2.controller;

import java.util.List;
import java.util.Optional;

import com.proj2.proj2.request.MailCreateRequest;
import com.proj2.proj2.request.MailUpdateRequest;
import com.proj2.proj2.response.MailResponse;
import com.proj2.proj2.service.MailService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mails")
@CrossOrigin(origins = "http://localhost:4200")
public class MailController {
    
    private MailService mailService;
    
    public MailController(MailService mailService)
    {
        this.mailService = mailService;
    }

    @GetMapping
    public List<MailResponse> getAllMails(@RequestParam Optional<Long> userId, 
        @RequestParam Optional<Long> mailId)
    {
        return mailService.getAllMails(userId, mailId);
    }

    @GetMapping("/event/{eventId}")
    public List<MailResponse> getAllMailsByEventId(@PathVariable long eventId)
    {
        return mailService.getAllMailsByEventId(eventId);
    }

    @PostMapping
    public MailResponse addMail(@RequestBody MailCreateRequest mailCreateRequest)
    {
        return mailService.addMail(mailCreateRequest);
    }

    @PutMapping("/{mailId}")
    public MailResponse updateMailById(@PathVariable long mailId,
        @RequestBody MailUpdateRequest mailUpdateRequest)
    {
        return mailService.updateMailById(mailId, mailUpdateRequest);
    }

    @DeleteMapping("/{mailId}")
    public void deleteMailById(@PathVariable long mailId)
    {
        mailService.deleteMailById(mailId);
    }

    @GetMapping("/getRaletedMails/{userId}")
    public List<MailResponse> getAllRelatedMails(@PathVariable long userId)
    {
        return mailService.getAllRelatedMails(userId);
    }

}
