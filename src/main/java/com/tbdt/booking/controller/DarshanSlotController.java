package com.tbdt.booking.controller;

import com.tbdt.booking.dto.DarshanSlotRequestDto;
import com.tbdt.booking.dto.DarshanSlotResponseDto;
import com.tbdt.booking.service.DarshanSlotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/slots")
public class DarshanSlotController {

    private final DarshanSlotService slotService;

    public DarshanSlotController(DarshanSlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping
    public ResponseEntity<DarshanSlotResponseDto> createSlot(@Valid @RequestBody DarshanSlotRequestDto requestDto) {
        DarshanSlotResponseDto response = slotService.createSlot(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DarshanSlotResponseDto> getSlotById(@PathVariable Long id) {
        DarshanSlotResponseDto response = slotService.getSlotById(id);
        return ResponseEntity.ok(response);
    }
}
