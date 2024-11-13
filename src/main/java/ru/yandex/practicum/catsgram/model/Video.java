package ru.yandex.practicum.catsgram.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Video {
    Long id;
    long postId;
    String originalFileName;
    String filePath;
}
