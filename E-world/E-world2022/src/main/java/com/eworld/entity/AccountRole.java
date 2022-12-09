package com.eworld.entity;

import lombok.*;

import javax.persistence.*;
@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "id", updatable = false)
    private Account account;

    @Column(insertable = false, updatable = false)
    private Integer accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", referencedColumnName = "id", updatable = false)
    private Role role;

    @Column(insertable = false, updatable = false)
    private String roleId;
}
