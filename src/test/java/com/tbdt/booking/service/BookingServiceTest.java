package com.tbdt.booking.service;

import com.tbdt.booking.dto.BookingRequestDto;
import com.tbdt.booking.dto.BookingResponseDto;
import com.tbdt.booking.entity.Booking;
import com.tbdt.booking.entity.DarshanSlot;
import com.tbdt.booking.exception.InsufficientCapacityException;
import com.tbdt.booking.exception.ResourceNotFoundException;
import com.tbdt.booking.repository.BookingRepository;
import com.tbdt.booking.repository.DarshanSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private DarshanSlotRepository slotRepository;

    @InjectMocks
    private BookingService bookingService;

    private DarshanSlot slot;

    @BeforeEach
    void setUp() {
        slot = new DarshanSlot(
                1L,
                "Tirupati Balaji Temple",
                LocalDate.of(2026, 12, 1),
                LocalTime.of(9, 0),
                10,
                0
        );
    }

    @Test
    @DisplayName("Should successfully book 3 tickets out of 10 capacity, leaving 7 remaining")
    void shouldSuccessfullyBook3Of10Tickets() {
        BookingRequestDto request = new BookingRequestDto(1L, "Rahul Sharma", "9876543210", 3);

        when(slotRepository.findByIdWithLock(1L)).thenReturn(Optional.of(slot));
        when(slotRepository.save(any(DarshanSlot.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            b.setId(100L);
            return b;
        });

        BookingResponseDto response = bookingService.createBooking(request);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(1L, response.getSlotId());
        assertEquals("Rahul Sharma", response.getDevoteeName());
        assertEquals("9876543210", response.getPhoneNumber());
        assertEquals(3, response.getNumberOfTickets());

        // Verify slot state
        assertEquals(3, slot.getBookedTickets());
        assertEquals(7, slot.getRemainingCapacity());

        verify(slotRepository).findByIdWithLock(1L);
        verify(slotRepository).save(slot);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    @DisplayName("Should reject booking when requested tickets exceed remaining capacity")
    void shouldRejectBookingWhenOverCapacity() {
        // Slot has capacity 10 and 0 booked
        BookingRequestDto request = new BookingRequestDto(1L, "Amit Verma", "9876543210", 11);

        when(slotRepository.findByIdWithLock(1L)).thenReturn(Optional.of(slot));

        InsufficientCapacityException ex = assertThrows(
                InsufficientCapacityException.class,
                () -> bookingService.createBooking(request)
        );

        assertEquals("Requested 11 ticket(s), but only 10 remaining for slot id 1", ex.getMessage());
        assertEquals(0, slot.getBookedTickets());

        verify(slotRepository, never()).save(any(DarshanSlot.class));
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    @DisplayName("Should reject booking when already partially booked and exceeding remaining")
    void shouldRejectBookingWhenPartiallyBookedAndExceedingRemaining() {
        // Slot has capacity 10 and 7 already booked -> 3 remaining
        slot.setBookedTickets(7);
        BookingRequestDto request = new BookingRequestDto(1L, "Priya Patel", "9876543210", 4);

        when(slotRepository.findByIdWithLock(1L)).thenReturn(Optional.of(slot));

        InsufficientCapacityException ex = assertThrows(
                InsufficientCapacityException.class,
                () -> bookingService.createBooking(request)
        );

        assertEquals("Requested 4 ticket(s), but only 3 remaining for slot id 1", ex.getMessage());
        assertEquals(7, slot.getBookedTickets());

        verify(slotRepository, never()).save(any(DarshanSlot.class));
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    @DisplayName("Should reject booking when slot does not exist")
    void shouldRejectWhenSlotNotFound() {
        BookingRequestDto request = new BookingRequestDto(999L, "Neha Gupta", "9876543210", 2);

        when(slotRepository.findByIdWithLock(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> bookingService.createBooking(request)
        );

        assertEquals("Slot not found with id: 999", ex.getMessage());
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    @DisplayName("Should reject booking when numberOfTickets is null or <= 0")
    void shouldRejectWhenInvalidTicketsInput() {
        BookingRequestDto requestZero = new BookingRequestDto(1L, "Vikram Singh", "9876543210", 0);
        BookingRequestDto requestNegative = new BookingRequestDto(1L, "Vikram Singh", "9876543210", -5);
        BookingRequestDto requestNull = new BookingRequestDto(1L, "Vikram Singh", "9876543210", null);

        assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(requestZero));
        assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(requestNegative));
        assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(requestNull));

        verify(slotRepository, never()).findByIdWithLock(any());
        verify(bookingRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should fetch booking by ID successfully")
    void shouldGetBookingByIdSuccessfully() {
        Booking booking = new Booking(10L, slot, "Suresh Kumar", "9988776655", 2);
        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));

        BookingResponseDto response = bookingService.getBookingById(10L);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals(1L, response.getSlotId());
        assertEquals("Suresh Kumar", response.getDevoteeName());
        assertEquals(2, response.getNumberOfTickets());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when booking not found")
    void shouldThrowWhenBookingNotFound() {
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookingService.getBookingById(999L));
    }
}
