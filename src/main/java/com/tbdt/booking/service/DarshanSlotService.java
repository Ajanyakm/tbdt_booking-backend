package com.tbdt.booking.service;

import com.tbdt.booking.dto.DarshanSlotRequestDto;
import com.tbdt.booking.dto.DarshanSlotResponseDto;
import com.tbdt.booking.entity.DarshanSlot;
import com.tbdt.booking.exception.ResourceNotFoundException;
import com.tbdt.booking.repository.DarshanSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DarshanSlotService {

    private final DarshanSlotRepository slotRepository;

    public DarshanSlotService(DarshanSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Transactional
    public DarshanSlotResponseDto createSlot(DarshanSlotRequestDto requestDto) {
        if (requestDto.getCapacity() == null || requestDto.getCapacity() <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }

        DarshanSlot slot = new DarshanSlot(
                requestDto.getTempleName(),
                requestDto.getSlotDate(),
                requestDto.getStartTime(),
                requestDto.getCapacity()
        );

        DarshanSlot savedSlot = slotRepository.save(slot);
        return mapToResponseDto(savedSlot);
    }

    @Transactional(readOnly = true)
    public DarshanSlotResponseDto getSlotById(Long id) {
        DarshanSlot slot = slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));
        return mapToResponseDto(slot);
    }

    public DarshanSlotResponseDto mapToResponseDto(DarshanSlot slot) {
        return new DarshanSlotResponseDto(
                slot.getId(),
                slot.getTempleName(),
                slot.getSlotDate(),
                slot.getStartTime(),
                slot.getCapacity(),
                slot.getBookedTickets(),
                slot.getRemainingCapacity()
        );
    }
}
