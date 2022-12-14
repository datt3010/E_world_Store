package com.eworld.entity;

import com.eworld.contstant.BlogStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private String image;
    @Enumerated(EnumType.STRING)
    private BlogStatus status;
}
