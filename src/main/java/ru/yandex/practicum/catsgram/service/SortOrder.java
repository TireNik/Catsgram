package ru.yandex.practicum.catsgram.service;

import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;

public enum SortOrder {
    ASCENDING, DESCENDING;

    public static SortOrder from(String order) {
        return switch (order.toLowerCase()) {
            case "ascending", "asc" -> ASCENDING;
            case "descending", "desc" -> DESCENDING;
            default -> throw new ParameterNotValidException(order, "Не корректное значение для сортировки");
        };
    }
}
