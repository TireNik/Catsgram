package ru.yandex.practicum.catsgram.dto;

import lombok.Data;

@Data
public class VideoDto {
    private long id;
    private long postId;
    private String fileName;
    private byte[] data;
}
