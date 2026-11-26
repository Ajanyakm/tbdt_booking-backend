package com.tbdt.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookingRequestDto {

    @NotNull(message = "Slot ID is mandatory")
    private Long slotId;

    @NotBlank(message = "Devotee name is mandatory")
    private String devoteeName;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotNull(message = "Number of tickets is mandatory")
    @Min(value = 1, message = "Number of tickets must be greater than 0")
    private Integer numberOfTickets;

    public BookingRequestDto() {
    }

    public BookingRequestDto(Long slotId, String devoteeName, String phoneNumber, Integer numberOfTickets) {
        this.slotId = slotId;
        this.devoteeName = devoteeName;
        this.phoneNumber = phoneNumber;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
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
