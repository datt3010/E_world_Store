package com.eworld.service;

import com.eworld.contstant.BlogStatus;
import com.eworld.dto.blog.BlogDto;
import com.eworld.dto.blog.BlogInput;
import com.eworld.dto.blog.BlogUpdate;
import com.eworld.entity.Blog;
import com.eworld.filter.BlogFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    public List<Blog> findAll(BlogStatus status);

    public BlogDto create(BlogInput input);

    public 	BlogDto update(Integer id, BlogUpdate input);

    public Page<BlogDto> findPaging(BlogFilter filter, Pageable pageable);

    public void deleteById(Integer id);

    public BlogDto getDetail(Integer id);
}
