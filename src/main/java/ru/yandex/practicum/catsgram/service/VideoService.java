package ru.yandex.practicum.catsgram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.catsgram.dal.PostRepository;
import ru.yandex.practicum.catsgram.dal.VideoRepository;
import ru.yandex.practicum.catsgram.dto.VideoDto;
import ru.yandex.practicum.catsgram.dto.VideoUploadResponse;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.exception.VideoFileException;
import ru.yandex.practicum.catsgram.mapper.VideoMapper;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.Video;
import ru.yandex.practicum.catsgram.model.VideoData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final PostRepository postRepository;
    private final VideoRepository videoRepository;

    @Value("${catsgram.image-directory}")
    private final String videoDirectory;

    public VideoData getVideoData(long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException("Видео с id = " + videoId + " не найдено"));
        byte[] data = loadFile(video);

        return new VideoData(data, video.getOriginalFileName());
    }

    public byte[] loadFile(Video video) {
        Path path = Paths.get(video.getFilePath());
        if (Files.exists(path)) {
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                throw new VideoFileException("Ошибка чтения файла.  Id: " + video.getId()
                        + ", name: " + video.getOriginalFileName());
            }
        } else {
            throw new VideoFileException("Файл не найден. Id: " + video.getId()
                    + ", name: " + video.getOriginalFileName());
        }
    }

    public List<VideoDto> getPostVideo(long postId) {
        return videoRepository.findByPostId(postId)
                .stream()
                .map(video -> {
                    byte[] bytes = loadFile(video);
                    return VideoMapper.mapToVideoDto(video, bytes);
                }).collect(Collectors.toList());
    }

    public Path saveFile(MultipartFile file, Post post) {
        try {
            String uniqueFileName = String.format("%d.%s", Instant.now().toEpochMilli(),
                    StringUtils.getFilenameExtension(file.getOriginalFilename()));

            Path uploadPath = Paths.get(videoDirectory,
                    String.valueOf(post.getAuthor().getId()),
                    String.valueOf(post.getId()));

            Path filePath = uploadPath.resolve(uniqueFileName);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            file.transferTo(filePath);
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<VideoUploadResponse> saveVideos(long postId, List<MultipartFile> files) {
        return files.stream().map(file -> saveVideo(postId, file)).collect(Collectors.toList());
    }

    private VideoUploadResponse saveVideo(long postId, MultipartFile file) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ConditionsNotMetException("Указанный пост не найден"));

        Path filePath = saveFile(file, post);

        Video video = videoRepository.save(VideoMapper.mapToVideo(postId, filePath, file.getOriginalFilename()));

        return VideoMapper.mapToVideoUploadResponse(video);
    }

}
