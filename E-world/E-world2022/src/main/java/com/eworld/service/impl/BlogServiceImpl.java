package com.eworld.service.impl;

import com.eworld.contstant.BlogStatus;
import com.eworld.dto.blog.BlogDto;
import com.eworld.dto.blog.BlogInput;
import com.eworld.dto.blog.BlogUpdate;
import com.eworld.entity.Blog;
import com.eworld.filter.BlogFilter;
import com.eworld.projector.BlogProjector;
import com.eworld.repository.blog.BlogRepository;
import com.eworld.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<Blog> findAll(BlogStatus status) {
        return null;
    }

    @Override
    @Transactional
    public BlogDto create(BlogInput input) {

        Blog blog = Blog.builder()
                .description(input.getName())
                .name(input.getName())
                .image(input.getImage())
                .status(input.getStatus())
                .build();

        blogRepository.save(blog);

        return BlogDto.builder()
                .id(blog.getId())
                .build();
    }

    @Override
    @Transactional
    public BlogDto update(Integer id, BlogUpdate input) {
        Blog blog = this.findById(id);

        blog.setName(input.getName());
        blog.setImage(input.getImage());
        blog.setDescription(input.getDescription());
        blog.setStatus(input.getStatus());

        return BlogDto.builder().id(id).build();
    }

    @Override
    public Page<BlogDto> findPaging(BlogFilter filter, Pageable pageable) {
        Page<Blog> page = blogRepository.findPaging(filter, pageable);
        List<BlogDto> content = BlogProjector.convertToPageDto(page.getContent());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
    blogRepository.deleteById(id);
    }

    @Override
    public BlogDto getDetail(Integer id) {
        Blog blog = this.findById(id);
        BlogDto dto = BlogProjector.convertToDetailDto(blog);
        return dto;
    }
    public Blog findById(Integer id) {
        return blogRepository.findById(id).get();
    }
}
