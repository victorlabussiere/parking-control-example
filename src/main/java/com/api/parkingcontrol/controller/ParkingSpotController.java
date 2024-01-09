package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dtos.ParkingSpotDTO;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.models.service.ParkingSportService;
import com.api.parkingcontrol.utils.ParkingSpotUtils;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSportService parkingSportService;
    final ParkingSpotUtils parkingSpotUtils;

    public ParkingSpotController(ParkingSportService parkingSportService, ParkingSpotUtils parkingSpotUtils) {
        this.parkingSportService = parkingSportService;
        this.parkingSpotUtils = parkingSpotUtils;
    }

    @PostMapping("/")
    public ResponseEntity<?> create (@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        ResponseEntity<String> CONFLICT = this.parkingSpotUtils.validateParkingSpotDTOContent(parkingSpotDTO);
        if (CONFLICT != null) return CONFLICT;

        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel); // dto to database model assimilation
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC"))); // setting registration date
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSportService.create(parkingSpotModel));// saving data on database
    }

    @GetMapping("/")
    public ResponseEntity<?> retrieveAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSportService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveOne(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSportService.findById(id);
        if(parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found: Parking spot not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(value = "id")UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSportService.findById(id);
        if(parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found: Parking spot not found");
        }

        parkingSportService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking spot deleted successfully: " + parkingSpotModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSportService.findById(id);
        if(parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found: Parking spot number not found");
        }

        ParkingSpotModel parkingSpotModel = parkingSpotModelOptional.get();
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModel);
    }

}
