package com.tbdt.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "darshan_slots")
public class DarshanSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String templeName;

    @Column(nullable = false)
    private LocalDate slotDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer bookedTickets = 0;

    @Version
    private Long version;

    public DarshanSlot() {
    }

    public DarshanSlot(String templeName, LocalDate slotDate, LocalTime startTime, Integer capacity) {
        this.templeName = templeName;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.capacity = capacity;
        this.bookedTickets = 0;
    }

    public DarshanSlot(Long id, String templeName, LocalDate slotDate, LocalTime startTime, Integer capacity, Integer bookedTickets) {
        this.id = id;
        this.templeName = templeName;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.capacity = capacity;
        this.bookedTickets = bookedTickets != null ? bookedTickets : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTempleName() {
        return templeName;
    }

    public void setTempleName(String templeName) {
        this.templeName = templeName;
    }

    public LocalDate getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(LocalDate slotDate) {
        this.slotDate = slotDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getBookedTickets() {
        return bookedTickets;
    }

    public void setBookedTickets(Integer bookedTickets) {
        this.bookedTickets = bookedTickets;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public int getRemainingCapacity() {
        int cap = this.capacity != null ? this.capacity : 0;
        int booked = this.bookedTickets != null ? this.bookedTickets : 0;
        return Math.max(0, cap - booked);
    }
}
