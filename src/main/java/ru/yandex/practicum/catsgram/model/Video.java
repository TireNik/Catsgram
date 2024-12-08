package ru.yandex.practicum.catsgram.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Video {
    Long id;
    long postId;
    String originalFileName;
    String filePath;
}
