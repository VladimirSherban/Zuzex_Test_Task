package com.example.crudtest.service.iml;

import com.example.crudtest.model.House;
import com.example.crudtest.model.User;
import com.example.crudtest.repository.HouseDao;
import com.example.crudtest.service.HouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseDao houseDao;

    @Override
    public House createHouse(House house) {
        Optional<House> byAddress = houseDao.findByAddressAndDeletedStatusFalse(house.getAddress());
        String address = house.getAddress();

        if (byAddress.isPresent() && byAddress.get().getAddress().equals(address)) {
            log.warn("createHouse: Дом с адресом {} уже существует", address);
            return null;
        }


        House result = House.builder()
                .address(house.getAddress())
                .owner(house.getOwner())
                .deletedStatus(false)
                .build();

        if (result == null) {
            log.warn("createUser: Не удалось создать дом");
            return null;
        } else {
            houseDao.save(result);
            log.info("createUser: Дом '{}' '{}' создан", house.getAddress(), house.getOwner());
            return result;
        }
    }

    @Override
    public House addTenantsToHouse(Long houseId, User owner, List<User> tenants) {
        House house = houseDao.findByHouseIdAndDeletedStatusFalse(houseId).orElse(null);

        if (house == null) {
            log.warn("addTenantsToHouse: Дом с id {} не найден", houseId);
            return null;
        }
        if (!house.getOwner().equals(owner)) {
            log.warn("addTenantsToHouse: Пользователь {} не является хозяином дома с id {}", owner.getName(), houseId);
            return null;
        }

        house.getTenants().addAll(tenants);
        houseDao.save(house);
        log.info("addTenantsToHouse: Жильцы добавлены к дому с id {}", houseId);
        return house;
    }

    @Override
    public House updateHouse(Long houseId, House updatedHouse) {
        House house = houseDao.findByHouseIdAndDeletedStatusFalse(houseId).orElse(null);

        if (house == null) {
            log.warn("updateHouse: Дом с id {} не найден", houseId);
            return null;
        }

        house.setAddress(updatedHouse.getAddress());
        house.setOwner(updatedHouse.getOwner());

        houseDao.save(house);
        log.info("updateHouse: Дом с id {} обновлен", houseId);
        return house;
    }

    @Override
    public void deleteHouse(Long houseId) {
        House house = houseDao.findByHouseIdAndDeletedStatusFalse(houseId).orElse(null);

        if (house == null) {
            log.warn("deleteHouse: Дом с id {} не найден", houseId);
            return;
        }
        // Удалите дом из коллекции жильцов, но не удаляйте жильцов самих по средствам JPA
        house.getTenants().clear();
        // Установите флаг deletedStatus в true
        house.setDeletedStatus(true);

        houseDao.save(house);
        log.info("deleteHouse: Дом с id {} удален", houseId);
    }

    @Override
    public House getHouseById(Long houseId) {
        return houseDao.findByHouseIdAndDeletedStatusFalse(houseId).orElse(null);
    }


}
