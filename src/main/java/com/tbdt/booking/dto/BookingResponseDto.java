package com.tbdt.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingResponseDto {

    private Long id;
    private Long slotId;
    private String templeName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate slotDate;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    private String devoteeName;
    private String phoneNumber;
    private Integer numberOfTickets;

    public BookingResponseDto() {
    }

    public BookingResponseDto(Long id, Long slotId, String templeName, LocalDate slotDate, LocalTime startTime,
                              String devoteeName, String phoneNumber, Integer numberOfTickets) {
        this.id = id;
        this.slotId = slotId;
        this.templeName = templeName;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.devoteeName = devoteeName;
        this.phoneNumber = phoneNumber;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
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

    public String getDevoteeName() {
        return devoteeName;
    }

    public void setDevoteeName(String devoteeName) {
        this.devoteeName = devoteeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
