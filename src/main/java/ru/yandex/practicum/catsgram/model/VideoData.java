package ru.yandex.practicum.catsgram.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
public class VideoData {
    private final byte[] data;
    private final String name;
}
