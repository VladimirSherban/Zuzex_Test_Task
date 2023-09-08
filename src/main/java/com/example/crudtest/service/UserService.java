package com.example.crudtest.service;

import com.example.crudtest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Создает нового пользователя.
     *
     * @param user новый пользователь для создания.
     * @return созданный пользователь или null, если не удалось создать.
     */
    User createUser(User user);

    /**
     * Обновляет информацию о пользователе.
     *
     * @param name            имя пользователя для обновления.
     * @param updatedUserData обновленные данные пользователя.
     * @return обновленный пользователь или null, если не удалось обновить.
     */
    User updateUser(String name, User updatedUserData);

    /**
     * Удаляет пользователя по имени.
     *
     * @param name имя пользователя для удаления.
     * @return true, если пользователь успешно удален, иначе false.
     */
    boolean deleteUser(String name);

    /**
     * Получает пользователя по имени.
     *
     * @param name имя пользователя для поиска.
     * @return Optional с пользователем, если найден, или пустой Optional, если не найден.
     */
    Optional<User> getUserByName(String name);

    /**
     * Возвращает список всех активных пользователей.
     *
     * @return список активных пользователей.
     */
    List<User> getAllUsers();
}
