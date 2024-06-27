package com.thegather.api.domain.entities;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "QUIZZES")
public class Quizzes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date_created;

    @Column(nullable = false, length = 255, name = "DESCRIPTION")
    private String description;

    @Column(nullable = false, length = 255, name = "NAME")
    private String name;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private int creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDate date_created) {
        this.date_created = date_created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Quizzes() {
    }

    public Quizzes(Long id, LocalDate date_created, String description, String name, int duration, int creator) {
        this.id = id;
        this.date_created = date_created;
        this.description = description;
        this.name = name;
        this.duration = duration;
        this.creator = creator;
    }
}
