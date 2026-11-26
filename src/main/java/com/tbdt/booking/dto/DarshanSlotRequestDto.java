package com.tbdt.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class DarshanSlotRequestDto {

    @NotBlank(message = "Temple name is mandatory")
    private String templeName;

    @NotNull(message = "Slot date is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate slotDate;

    @NotNull(message = "Start time is mandatory")
    @JsonFormat(pattern = "HH:mm[:ss]")
    private LocalTime startTime;

    @NotNull(message = "Capacity is mandatory")
    @Min(value = 1, message = "Capacity must be greater than 0")
    private Integer capacity;

    public DarshanSlotRequestDto() {
    }

    public DarshanSlotRequestDto(String templeName, LocalDate slotDate, LocalTime startTime, Integer capacity) {
        this.templeName = templeName;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.capacity = capacity;
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
}
