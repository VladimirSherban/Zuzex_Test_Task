package com.example.crudtest.service.iml;

import com.example.crudtest.model.House;
import com.example.crudtest.model.User;
import com.example.crudtest.repository.HouseDao;
import com.example.crudtest.repository.UserDao;
import com.example.crudtest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceIml implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final HouseDao houseDao;


    @Override
    public User createUser(User user) {
        String nameNewUser = user.getName();
        Optional<User> byName = userDao.findByNameAndDeletedStatusFalse(user.getName());

        if (byName.isPresent() && nameNewUser.equals(byName.get().getName())) {
            log.warn("createUser: Пользователь с именем {} уже существует", nameNewUser);
            return null;
        }

        String encode = passwordEncoder.encode(user.getPassword());
        User result = User.builder()
                .deletedStatus(false)
                .name(nameNewUser)
                .password(encode)
                .age(user.getAge())
                .role(user.getRole()).build();
        if (result == null) {
            log.warn("createUser: Не удалось создать пользователя");
            return null;
        } else {
            userDao.save(result);
            log.info("createUser: Пользователь '{}' '{}' '{}'  создан", nameNewUser, user.getAge(), user.getRole());
            return result;
        }
    }

    @Override
    public User updateUser(String name, User updatedUserData) {
        Optional<User> existingUser = userDao.findByNameAndDeletedStatusFalse(name);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();

            userToUpdate.setName(updatedUserData.getName());
            userToUpdate.setAge(updatedUserData.getAge());
            userToUpdate.setRole(updatedUserData.getRole());

            if (updatedUserData.getPassword() != null) {
                String encodedPassword = passwordEncoder.encode(updatedUserData.getPassword());
                userToUpdate.setPassword(encodedPassword);
            }

            userDao.save(userToUpdate);
            log.info("updateUser: Пользователь с именем  '{}' обновлен", name);
            return userToUpdate;
        } else {
            log.warn("updateUser: Пользователь с именем '{}' не найден", name);
            return null;
        }
    }

    @Override
    public boolean deleteUser(String name) {
        Optional<User> user = userDao.findByNameAndDeletedStatusFalse(name);

        if (user.isPresent()) {
            User userDeleted = user.get();
            List<House> tenantHouses = houseDao.findByTenantsContainsAndDeletedStatusFalse(userDeleted);

            for (House house : tenantHouses) {
                house.getTenants().remove(userDeleted);
            }

            userDeleted.setDeletedStatus(true);
            userDao.save(userDeleted);
            log.info("deleteUser: Пользователь с именем '{}' удален", name);
            return true;
        } else {
            log.warn("deleteUser: Пользователь с именем '{}' не найден", name);
            return false;
        }
    }

    @Override
    public Optional<User> getUserByName(String name) {
        Optional<User> user = userDao.findByNameAndDeletedStatusFalse(name);
        if (user.isPresent()) {
            log.info("getUserByName: Пользователь с именем '{}' найден.", name);
            return user;
        } else {
            log.warn("Пользователь с именем '{}' не найден или удален.", name);
            return Optional.empty();
        }
    }


    @Override
    public List<User> getAllUsers() {
        log.info("Вызван метод getAllUsers для получения активных пользователей.");
        return userDao.findByDeletedStatusFalse();
    }
}
