package ru.yandex.practicum.catsgram.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.model.Video;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VideoRowMappers implements RowMapper<Video> {
    @Override
    public Video mapRow(ResultSet rs, int rowNum) throws SQLException {
        Video video = new Video();
        video.setId(rs.getLong("id"));
        video.setOriginalFileName(rs.getString("original_file_name"));
        video.setFilePath(rs.getString("file_path"));
        video.setPostId(rs.getLong("post_id"));
        return video;
    }
}
