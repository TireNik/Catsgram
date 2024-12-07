//package ru.yandex.practicum.catsgram.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import ru.yandex.practicum.catsgram.model.Video;
//import ru.yandex.practicum.catsgram.model.VideoData;
//import ru.yandex.practicum.catsgram.service.VideoService;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class VideoController {
//    private final VideoService videoService;
//
//    @GetMapping("/posts/{postId}/videos")
//    public List<Video> getPostVideo(@PathVariable("postId") long postId) {
//        return videoService.getPostVideo(postId);
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/posts/{postId}/videos")
//    public List<Video> addPostVideo(@PathVariable("postId") long postId,
//                                    @RequestParam("video")List<MultipartFile> files) {
//        return videoService.saveVideos(postId, files);
//    }
//
//    @GetMapping(value = "/videos/{videoId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<byte[]> downloadVideo(@PathVariable long videoId) {
//        VideoData videoData = videoService.getVideoData(videoId);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDisposition(
//                ContentDisposition.attachment()
//                        .filename(videoData.getName())
//                        .build()
//        );
//        return new ResponseEntity<>(videoData.getData(), headers, HttpStatus.OK);
//    }
//
//}
