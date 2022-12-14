package com.eworld.repository.blog;

import com.eworld.entity.Blog;
import com.eworld.filter.BlogFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogCustomRepository {
    Page<Blog> findPaging(BlogFilter filter, Pageable pageable);

}
