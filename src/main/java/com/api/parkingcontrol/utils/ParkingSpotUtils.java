package com.api.parkingcontrol.utils;

import com.api.parkingcontrol.dtos.ParkingSpotDTO;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.models.service.ParkingSportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotUtils {

    final ParkingSportService parkingSportService;

    public ParkingSpotUtils(ParkingSportService parkingSportService) {
        this.parkingSportService = parkingSportService;
    }

    public ResponseEntity<String> validateParkingSpotDTOContent(ParkingSpotDTO parkingSpotDTO) {
        if(parkingSportService.existsByLicensePlateCar(parkingSpotDTO.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License plate car is already in use!");
        }

        if(parkingSportService.existsByParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot number is already in use");
        }

        if(parkingSportService.existsByApartmentAndBlock(parkingSpotDTO.getApartment(), parkingSpotDTO.getBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot already registered for another apartment block");
        }
        return null;
    }

    public static void bindParkingSpotProperties(ParkingSpotDTO parkingSpotDTO, ParkingSpotModel parkingSpotModel) {
        parkingSpotModel.setLicensePlateCar(parkingSpotDTO.getLicensePlateCar());
        parkingSpotModel.setParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber());
        parkingSpotModel.setResponsibleName(parkingSpotDTO.getResponsibleName());
        parkingSpotModel.setApartment(parkingSpotDTO.getApartment());
        parkingSpotModel.setBlock(parkingSpotDTO.getBlock());
        parkingSpotModel.setBrandCar(parkingSpotDTO.getBrandCar());
        parkingSpotModel.setColorCar(parkingSpotDTO.getColorCar());
        parkingSpotModel.setModelCar(parkingSpotDTO.getModelCar());
    }
}
