package com.proj2.proj2.model;



import java.time.LocalDateTime;

// import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
// import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User admin;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String title;

    private LocalDateTime sent;

    private LocalDateTime due;
    private String text;
    private boolean isMonthly;
    //int year, int month, int day, int hour, int min,

    public Event(long adminid, String title, String due, String text, int year, int month, int day, int hour, int min, boolean isMonthly)
    {
        
        this.title = title;
        this.sent = LocalDateTime.now();
        this.due = LocalDateTime.of(year, month, day, hour, min, 0);
        this.text = text;
        this.isMonthly = isMonthly;
    }

    public Event(long adminid, String title, String due, String text, int year, int month, int day, int hour, int min)
    {
        
        this.title = title;
        this.sent = LocalDateTime.now();
        this.due = LocalDateTime.of(year, month, day, hour, min, 0);
        this.text = text;
        this.isMonthly = false;
    }

    public Event()
    {

    }
}
