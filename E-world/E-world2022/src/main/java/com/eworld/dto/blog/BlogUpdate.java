package com.eworld.dto.blog;

import com.eworld.contstant.BlogStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdate {
    @NotBlank(message = "{Blog.isBlank.name}")

    private String name;
    @NotBlank(message = "{Blog.isBlank.description}")
    private String description;
    private String image;
    @NotNull(message = "{Blog.notNull.status}")
    private BlogStatus status;
}
