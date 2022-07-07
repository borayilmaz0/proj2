package com.proj2.proj2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.proj2.proj2.model.Event;
import com.proj2.proj2.model.Mail;
import com.proj2.proj2.model.User;
import com.proj2.proj2.repository.EventRepository;
import com.proj2.proj2.repository.MailRepository;
import com.proj2.proj2.repository.UserRepository;
import com.proj2.proj2.request.MailCreateRequest;
import com.proj2.proj2.request.MailUpdateRequest;
import com.proj2.proj2.response.MailResponse;

import org.springframework.stereotype.Service;

@Service
public class MailService {
    
    private MailRepository mailRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;

    public MailService(MailRepository mailRepository, 
        UserRepository userRepository,
        EventRepository eventRepository)
    {
        this.mailRepository = mailRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public List<MailResponse> getAllMails(Optional<Long> userId, Optional<Long> mailId) 
    {
        if (!userId.isPresent() && !mailId.isPresent())
        {
            return toResponse(mailRepository.findAll());
        }
        else if (userId.isPresent() && !mailId.isPresent())
        {
            return toResponse(mailRepository.findAllByAdminId(userId.get()));
        }
        else 
        {
            List<Mail> mails = new ArrayList<>();
            Mail mail = mailRepository.findById(mailId.get()).orElse(null);
            if (!userId.isPresent())
                mails.add(mail);
            else
            {
                if (userRepository.findById(userId.get()).orElse(null) == null 
                        && mail != null
                        && mail.getAdminUser().getId() == userId.get())
                    mails.add(mail);
            }

            return toResponse(mails); 
        }
    }

    public List<MailResponse> getAllRelatedMails(long userId)
    {
        List<Mail> mails = mailRepository.findAll();
        List<Mail> result = new ArrayList<>();
        if (mails.isEmpty())
        {
            return toResponse(mails);
        }
        for (Mail m : mails)
        {
            if (m.getAdminUser().getId() == userId
                || m.getInvitedUser().getId() == userId)
            {
                result.add(m);
            }
        }

        return toResponse(result);
    }

    public List<MailResponse> getAllMailsByInvitedUserId(long invitedUserId)
    {
        return toResponse(mailRepository.findAllByInvitedUserId(invitedUserId));
    }

    public List<MailResponse> getAllMailsByEventId(long eventId)
    {
        return toResponse(mailRepository.findAllByEventId(eventId));
    }

    public MailResponse addMail(MailCreateRequest mailCreateRequest)
    {
        User admin = userRepository.findById(mailCreateRequest.getAdminId()).orElse(null);
        User invitedUser = userRepository.findById(mailCreateRequest.getInvitedUserId()).orElse(null);
        Event event = eventRepository.findById(mailCreateRequest.getEventId()).orElse(null);

        if (admin == null || invitedUser == null || event == null
            || (admin.getId() == invitedUser.getId()))
        {
            return null;
        }
        else
        {
            if (mailRepository.findByMailUtils(
                mailCreateRequest.getAdminId(), 
                mailCreateRequest.getInvitedUserId(), 
                mailCreateRequest.getEventId()) != null)
            {
                return null;
            }

            Mail mailToAdd = new Mail();
            mailToAdd.setAdminUser(admin);
            mailToAdd.setInvitedUser(invitedUser);
            mailToAdd.setEvent(event);
            mailToAdd.setMailinfo(mailCreateRequest.getMailinfo());

            return toResponse(mailRepository.save(mailToAdd));
        }

    }

    public MailResponse updateMailById(long mailId, MailUpdateRequest mailUpdateRequest)
    {
        Optional<Mail> mailToFind = mailRepository.findById(mailId);

        if (!mailToFind.isPresent())
        {
            return null;
        }
        else
        {
            Mail mailToUpdate = mailToFind.get();
            mailToUpdate.setMailinfo(mailUpdateRequest.getMailinfo());

            return toResponse(mailRepository.save(mailToUpdate));
        }
    }

    public void deleteMailById(long mailId)
    {
        mailRepository.deleteById(mailId);
    }

    private MailResponse toResponse(Mail mail)
    {
        return new MailResponse(
            mail.getId(),
            mail.getAdminUser().getId(),
            mail.getAdminUser().getUsername(),
            mail.getInvitedUser().getId(),
            mail.getInvitedUser().getUsername(),
            mail.getEvent().getId(),
            mail.getMailinfo());
    }

    private List<MailResponse> toResponse(List<Mail> mails)
    {
        List<MailResponse> result = new ArrayList<>();
        for (Mail m : mails)
            result.add(toResponse(m));
        return result;
    }
}
