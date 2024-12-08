package ru.yandex.practicum.catsgram.mapper;

import ru.yandex.practicum.catsgram.dto.VideoDto;
import ru.yandex.practicum.catsgram.dto.VideoUploadResponse;
import ru.yandex.practicum.catsgram.model.Video;

import java.nio.file.Path;

public class VideoMapper {
    public static Video mapToVideo(long postId, Path filePath, String originalFileName) {
        Video video = new Video();
        video.setOriginalFileName(originalFileName);
        video.setFilePath(filePath.toString());
        video.setId(postId);
        return video;
    }

    public static VideoDto mapToVideoDto(Video video, byte[] data) {
        VideoDto dto = new VideoDto();
        dto.setId(video.getId());
        dto.setPostId(video.getPostId());
        dto.setFileName(video.getOriginalFileName());
        dto.setData(data);
        return dto;
    }

    public static VideoUploadResponse mapToVideoUploadResponse(Video video) {
        VideoUploadResponse dto = new VideoUploadResponse();
        dto.setId(video.getId());
        dto.setPostId(video.getPostId());
        dto.setFileName(video.getOriginalFileName());
        dto.setFilePath(video.getFilePath());
        return dto;
    }
}
