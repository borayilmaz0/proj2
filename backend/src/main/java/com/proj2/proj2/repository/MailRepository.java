package com.proj2.proj2.repository;

import java.util.List;

import com.proj2.proj2.model.Mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MailRepository extends JpaRepository<Mail, Long> {

    @Query(value = "SELECT * FROM mail WHERE admin_id = :admin_id", nativeQuery = true)
    List<Mail> findAllByAdminId(@Param("admin_id") Long adminId);

    List<Mail> findAllByInvitedUserId(Long invitedUserId);

    List<Mail> findAllByEventId(Long eventId);

    
    public default Mail findByMailUtils(Long adminId, Long invitedUserId, Long eventId)
    {
        List<Mail> result = this.findAll();
        for (Mail m : result)
        {
            if (adminId == m.getAdminUser().getId()
                && invitedUserId == m.getInvitedUser().getId()
                && eventId == m.getEvent().getId())
                {
                    return m;
                }
        }
            
        return null;
    }
}
