//package ru.yandex.practicum.catsgram.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
//import ru.yandex.practicum.catsgram.exception.NotFoundException;
//import ru.yandex.practicum.catsgram.exception.VideoFileException;
//import ru.yandex.practicum.catsgram.model.Post;
//import ru.yandex.practicum.catsgram.model.Video;
//import ru.yandex.practicum.catsgram.model.VideoData;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class VideoService {
//    private final PostService postService;
//
//    private final Map<Long, Video> videos = new HashMap<>();
//
//    private final String videoDirectory = "/Users/sviridovnikita/Desktop/saveVideo";
//
//    public VideoData getVideoData(long videoId) {
//        if (!videos.containsKey(videoId)) {
//            throw new NotFoundException("Видео с id = " + videoId + " не найдено");
//        }
//
//        Video video = videos.get(videoId);
//        byte[] data = loadFile(video);
//
//        return new VideoData(data, video.getOriginalFileName());
//    }
//
//    public byte[] loadFile(Video video) {
//        Path path = Paths.get(video.getFilePath());
//        if (Files.exists(path)) {
//            try {
//                return Files.readAllBytes(path);
//            } catch (IOException e) {
//                throw new VideoFileException("Ошибка чтения файла.  Id: " + video.getId()
//                        + ", name: " + video.getOriginalFileName());
//            }
//        } else {
//            throw new VideoFileException("Файл не найден. Id: " + video.getId()
//                    + ", name: " + video.getOriginalFileName());
//        }
//    }
//
//    public List<Video> getPostVideo(long postId) {
//        return videos.values()
//                .stream()
//                .filter(video -> video.getPostId() == postId)
//                .collect(Collectors.toList());
//    }
//
//    public Path saveFile(MultipartFile file, Post post) {
//        try {
//            String uniqueFileName = String.format("%d.%s", Instant.now().toEpochMilli(),
//                    StringUtils.getFilenameExtension(file.getOriginalFilename()));
//            Path uploadPath = Paths.get(videoDirectory, String.valueOf(post.getAuthorId()));
//            Path filePath = uploadPath.resolve(uniqueFileName);
//
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//            file.transferTo(filePath);
//            return filePath;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<Video> saveVideos(long postId, List<MultipartFile> files) {
//        return files.stream().map(file -> saveVideo(postId, file)).collect(Collectors.toList());
//    }
//
//    private Video saveVideo(long postId, MultipartFile file) {
//        Post post = postService.findById(postId)
//                .orElseThrow(() -> new ConditionsNotMetException("Указанный пост не найден"));
//
//        Path filePath = saveFile(file, post);
//
//        long videoId = getNextId();
//        Video video = Video.builder()
//                .filePath(filePath.toString())
//                .id(videoId)
//                .postId(postId)
//                .originalFileName(file.getOriginalFilename())
//                .build();
//        videos.put(videoId, video);
//
//        return video;
//    }
//
//    private Long getNextId() {
//        long currentMaxId = videos.keySet()
//                .stream()
//                .mapToLong(id -> id)
//                .max()
//                .orElse(0);
//        return ++currentMaxId;
//    }
//}
