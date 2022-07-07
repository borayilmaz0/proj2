package com.proj2.proj2.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.proj2.proj2.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByAdminId(Long adminId);

    List<Event> findAllByAdminIdOrderByDueAsc(Long adminId);

    List<Event> findAllByDueBefore(LocalDateTime dueBefore);

    List<Event> findAllByOrderByDueAsc();
}
