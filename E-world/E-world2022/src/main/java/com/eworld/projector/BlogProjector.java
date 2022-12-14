package com.eworld.projector;

import com.eworld.dto.blog.BlogDto;
import com.eworld.entity.Blog;

import java.util.List;
import java.util.stream.Collectors;

public class BlogProjector {

    public static List<BlogDto> convertToPageDto(List<Blog> entities){
        return entities.stream()
                .map(e ->  convertToPageDto(e))
                .collect(Collectors.toList());
    }

    public static BlogDto convertToPageDto(Blog entity) {
        return BlogDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .image(entity.getImage())
                .build();
    }

    public static BlogDto convertToDetailDto(Blog entity) {
        return BlogDto.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .image(entity.getImage())
                .status(entity.getStatus())
                .build();
    }
}
