package com.eworld.repository.blog;

import com.eworld.contstant.BlogStatus;
import com.eworld.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogRepository extends JpaRepository<Blog, Integer>,BlogCustomRepository {

    @Query("UPDATE Blog SET status= :status WHERE id=:blogId")
    @Modifying
    public void changeStatus(@Param(value = "status") BlogStatus status, @Param(value = "blogId") Integer id);
}
