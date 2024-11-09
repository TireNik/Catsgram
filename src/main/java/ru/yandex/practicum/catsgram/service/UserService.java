package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> getUsers() {
        return users.values();
    }

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public User addUser(User user) {
        if (user.getEmail() == null) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        if (users.containsKey(user.getEmail())) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }

        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }

        User oldUser = users.get(user.getId());

        if (isEmailUsed(user.getEmail(), user.getId())){
            throw new DuplicatedDataException("Этот имейл уже используется");
        }

        if (user.getUsername() != null) {
            oldUser.setUsername(user.getUsername());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            oldUser.setPassword(user.getPassword());
        }

        users.put(user.getId(), oldUser);
        return oldUser;
    }

    private boolean isEmailUsed(String email, Long id) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equals(email)
                        && !user.getId().equals(id));
    }

    private Long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
