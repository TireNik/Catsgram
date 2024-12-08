package ru.yandex.practicum.catsgram.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.catsgram.dal.mappers.VideoRowMappers;
import ru.yandex.practicum.catsgram.model.Video;

import java.util.List;
import java.util.Optional;

@Repository
public class VideoRepository extends BaseRepository<Video> {
    private static final String INSERT_QUERY = "INSERT INTO video_storage(original_name, file_path, post_id) " +
            "VALUES (?, ?, ?) returning id";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM video_storage WHERE id = ?";
    private static final String FIND_BY_POST_ID_QUERY = "SELECT * FROM video_storage where post_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM video_storage WHERE id = ?";

    public VideoRepository(JdbcTemplate jdbc, VideoRowMappers mapper) {
        super(jdbc, mapper, Video.class);
    }

    public Video save(Video video) {
        long id = insert(
                INSERT_QUERY,
                video.getOriginalFileName(),
                video.getFilePath(),
                video.getPostId()
        );
        video.setId(id);
        return video;
    }

    public Optional<Video> findById(long videoId) {
        return findOne(FIND_BY_ID_QUERY, videoId);
    }

    public List<Video> findByPostId(long postId) {
        return findMany(FIND_BY_POST_ID_QUERY, postId);
    }

    public boolean delete(long videoId) {
        return delete(DELETE_QUERY, videoId);
    }
}
