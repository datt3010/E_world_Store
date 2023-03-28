package com.eworld.entity;

import lombok.*;

import javax.persistence.*;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private  String image;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name ="productId", referencedColumnName = "id", updatable = false)
    private Product product;
}
