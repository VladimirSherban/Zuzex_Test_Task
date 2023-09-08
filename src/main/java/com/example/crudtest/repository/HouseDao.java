package com.example.crudtest.repository;

import com.example.crudtest.model.House;
import com.example.crudtest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseDao extends JpaRepository<House, Long> {

    Optional<House> findByAddressAndDeletedStatusFalse(String address);

    Optional<House> findByHouseIdAndDeletedStatusFalse(Long id);

    List<House> findByTenantsContainsAndDeletedStatusFalse(User userDeleted);
}
