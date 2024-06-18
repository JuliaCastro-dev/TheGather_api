package com.thegather.api.domain.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public int creator;

    @Column(nullable = false)
    public int company;

    @Column(nullable = false)
    public boolean is_private;

    @Column(nullable = false)
    public boolean notification_whats;

    @Column(nullable = false)
    public boolean notification_email;

    @Column(nullable = false)
    public boolean notification_sms;

    @Column(nullable = false, length = 255)
    public String title;

    @Column(nullable = false, length = 255)
    public String image;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    public LocalDate event_date;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    public LocalTime event_time;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    public LocalDate date_start;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    public LocalTime time_start;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    public LocalDate date_end;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    public LocalDate time_end;

    @Column(nullable = false)
    public String link;

    @Column(length = 255)
    public String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public boolean getIs_private() {
        return is_private;
    }

    public void setIs_private(boolean is_private) {
        this.is_private = is_private;
    }

    public boolean isNotification_whats() {
        return notification_whats;
    }

    public void setNotification_whats(boolean notification_whats) {
        this.notification_whats = notification_whats;
    }

    public boolean isNotification_email() {
        return notification_email;
    }

    public void setNotification_email(boolean notification_email) {
        this.notification_email = notification_email;
    }

    public boolean isNotification_sms() {
        return notification_sms;
    }

    public void setNotification_sms(boolean notification_sms) {
        this.notification_sms = notification_sms;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getEvent_date() {
        return event_date;
    }

    public void setEvent_date(LocalDate event_date) {
        this.event_date = event_date;
    }

    public LocalTime getEvent_time() {
        return event_time;
    }

    public void setEvent_time(LocalTime event_time) {
        this.event_time = event_time;
    }

    public LocalDate getDate_start() {
        return date_start;
    }

    public void setDate_start(LocalDate date_start) {
        this.date_start = date_start;
    }

    public LocalTime getTime_start() {
        return time_start;
    }

    public void setTime_start(LocalTime time_start) {
        this.time_start = time_start;
    }

    public LocalDate getDate_end() {
        return date_end;
    }

    public void setDate_end(LocalDate date_end) {
        this.date_end = date_end;
    }

    public LocalDate getTime_end() {
        return time_end;
    }

    public void setTime_end(LocalDate time_end) {
        this.time_end = time_end;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
