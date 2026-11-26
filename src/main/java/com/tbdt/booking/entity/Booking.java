package com.tbdt.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    private DarshanSlot slot;

    @Column(nullable = false)
    private String devoteeName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Integer numberOfTickets;

    public Booking() {
    }

    public Booking(DarshanSlot slot, String devoteeName, String phoneNumber, Integer numberOfTickets) {
        this.slot = slot;
        this.devoteeName = devoteeName;
        this.phoneNumber = phoneNumber;
        this.numberOfTickets = numberOfTickets;
    }

    public Booking(Long id, DarshanSlot slot, String devoteeName, String phoneNumber, Integer numberOfTickets) {
        this.id = id;
        this.slot = slot;
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

    public DarshanSlot getSlot() {
        return slot;
    }

    public void setSlot(DarshanSlot slot) {
        this.slot = slot;
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
