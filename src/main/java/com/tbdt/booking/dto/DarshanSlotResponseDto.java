package com.tbdt.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class DarshanSlotResponseDto {

    private Long id;
    private String templeName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate slotDate;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    private Integer capacity;
    private Integer bookedTickets;
    private Integer remainingCapacity;

    public DarshanSlotResponseDto() {
    }

    public DarshanSlotResponseDto(Long id, String templeName, LocalDate slotDate, LocalTime startTime, Integer capacity, Integer bookedTickets, Integer remainingCapacity) {
        this.id = id;
        this.templeName = templeName;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.capacity = capacity;
        this.bookedTickets = bookedTickets;
        this.remainingCapacity = remainingCapacity;
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

    public Integer getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(Integer remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }
}
