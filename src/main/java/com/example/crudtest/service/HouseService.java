package com.example.crudtest.service;

import com.example.crudtest.model.House;
import com.example.crudtest.model.User;

import java.util.List;

public interface HouseService {
    /**
     * Создает новый дом.
     *
     * @param house новый дом для создания.
     * @return созданный дом или null, если не удалось создать.
     */
    House createHouse(House house);

    /**
     * Добавляет жильцов к дому.
     *
     * @param houseId идентификатор дома.
     * @param owner   пользователь (хозяин дома).
     * @param tenants список жильцов для добавления.
     * @return обновленный объект дома или null в случае ошибки.
     */
    House addTenantsToHouse(Long houseId, User owner, List<User> tenants);

    /**
     * Обновляет информацию о доме.
     *
     * @param houseId      идентификатор дома.
     * @param updatedHouse обновленные данные о доме.
     * @return обновленный объект дома или null в случае ошибки.
     */
    House updateHouse(Long houseId, House updatedHouse);

    /**
     * Удаляет дом по его идентификатору.
     *
     * @param houseId идентификатор дома для удаления.
     */
    void deleteHouse(Long houseId);

    /**
     * Возвращает дом по его идентификатору, если он существует и не помечен как удаленный.
     *
     * @param houseId идентификатор дома.
     * @return дом или null, если дом не найден или удален.
     */
    House getHouseById(Long houseId);
}
