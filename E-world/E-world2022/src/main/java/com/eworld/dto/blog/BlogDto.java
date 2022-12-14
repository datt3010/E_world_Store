package com.eworld.dto.blog;

import com.eworld.contstant.BlogStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogDto {
    private Integer id;
    private String name;
    private String description;
    private BlogStatus status;
    private String image;

}
