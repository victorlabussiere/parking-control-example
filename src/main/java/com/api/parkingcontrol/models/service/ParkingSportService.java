package com.api.parkingcontrol.models.service;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repository.ParkingSportRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSportService {

    final ParkingSportRepository parkingSportRepository;

    public ParkingSportService(ParkingSportRepository parkingSportRepository) {
        this.parkingSportRepository = parkingSportRepository;
    }

    @Transactional // granting a rollback in error cases
    public ParkingSpotModel create(ParkingSpotModel parkingSpotModel) throws ResponseStatusException{
            return this.parkingSportRepository.save(parkingSpotModel);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return this.parkingSportRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return this.parkingSportRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return this.parkingSportRepository.existsByApartmentAndBlock(apartment, block);
    }

    public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return this.parkingSportRepository.findAll(pageable);
    }

    public Optional<ParkingSpotModel> findById(UUID id) {
        return this.parkingSportRepository.findById(id);
    }

    @Transactional // granting a rollback in error cases
    public void delete(ParkingSpotModel parkingSpotModel) {
        this.parkingSportRepository.delete(parkingSpotModel);
    }
}
