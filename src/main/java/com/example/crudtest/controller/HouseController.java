package com.example.crudtest.controller;

import com.example.crudtest.model.House;
import com.example.crudtest.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/secured/house")
public class HouseController {

    private final HouseService houseService;

    @PostMapping("/createHouse")
    public ResponseEntity<House> createHouse(@RequestBody House house) {
        House createdHouse = houseService.createHouse(house);

        if (createdHouse != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHouse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/houses/{houseId}")
    public ResponseEntity<House> getHouseById(@PathVariable Long houseId) {
        House house = houseService.getHouseById(houseId);
        if (house == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(house);
    }

    @PutMapping("/houses/{houseId}")
    public ResponseEntity<House> updateHouse(
            @PathVariable Long houseId,
            @RequestBody House updatedHouse) {
        House house = houseService.updateHouse(houseId, updatedHouse);
        if (house == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(house);
    }

    @DeleteMapping("/houses/{houseId}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long houseId) {
        houseService.deleteHouse(houseId);
        return ResponseEntity.noContent().build();
    }

}
