package ru.yandex.practicum.catsgram.dto;

import lombok.Data;

@Data
public class VideoUploadResponse {
    private long id;
    private long postId;
    private String fileName;
    private String filePath;
}
