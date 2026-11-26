package com.tbdt.booking.service;

import com.tbdt.booking.dto.BookingRequestDto;
import com.tbdt.booking.dto.BookingResponseDto;
import com.tbdt.booking.entity.Booking;
import com.tbdt.booking.entity.DarshanSlot;
import com.tbdt.booking.exception.InsufficientCapacityException;
import com.tbdt.booking.exception.ResourceNotFoundException;
import com.tbdt.booking.repository.BookingRepository;
import com.tbdt.booking.repository.DarshanSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DarshanSlotRepository slotRepository;

    public BookingService(BookingRepository bookingRepository, DarshanSlotRepository slotRepository) {
        this.bookingRepository = bookingRepository;
        this.slotRepository = slotRepository;
    }

    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto requestDto) {
        if (requestDto.getNumberOfTickets() == null || requestDto.getNumberOfTickets() <= 0) {
            throw new IllegalArgumentException("Number of tickets must be greater than 0");
        }

        // Lock slot row to safely prevent concurrent overbooking
        DarshanSlot slot = slotRepository.findByIdWithLock(requestDto.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + requestDto.getSlotId()));

        int remainingCapacity = slot.getCapacity() - slot.getBookedTickets();
        if (requestDto.getNumberOfTickets() > remainingCapacity) {
            throw new InsufficientCapacityException(
                    String.format("Requested %d ticket(s), but only %d remaining for slot id %d",
                            requestDto.getNumberOfTickets(), remainingCapacity, slot.getId())
            );
        }

        // Safely update booked tickets count
        slot.setBookedTickets(slot.getBookedTickets() + requestDto.getNumberOfTickets());
        slotRepository.save(slot);

        // Save new booking record
        Booking booking = new Booking(
                slot,
                requestDto.getDevoteeName(),
                requestDto.getPhoneNumber(),
                requestDto.getNumberOfTickets()
        );
        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponseDto(savedBooking);
    }

    @Transactional(readOnly = true)
    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return mapToResponseDto(booking);
    }

    public BookingResponseDto mapToResponseDto(Booking booking) {
        DarshanSlot slot = booking.getSlot();
        return new BookingResponseDto(
                booking.getId(),
                slot.getId(),
                slot.getTempleName(),
                slot.getSlotDate(),
                slot.getStartTime(),
                booking.getDevoteeName(),
                booking.getPhoneNumber(),
                booking.getNumberOfTickets()
        );
    }
}
