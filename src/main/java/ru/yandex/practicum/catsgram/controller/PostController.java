package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;
import ru.yandex.practicum.catsgram.service.SortOrder;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> findAll(@RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "desc") String sort,
                                    @RequestParam(defaultValue = "0") int from) {
        SortOrder sortOrder = SortOrder.from(sort);
        if (size <= 0) {
            throw new ParameterNotValidException(
                    String.valueOf(size),
                    "Некорректный размер выборки. Размер должен быть больше нуля");
        }
        if (from < 0) {
            throw new ParameterNotValidException(
                    String.valueOf(from),
                    "Некорректное значение начала списка постов. Не может быть меньше нуля");
        }
        return postService.findAll(size, sortOrder, from);
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> findById(@PathVariable Long id) {
        return postService.findById(id);
    }


    @PostMapping
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }
}
