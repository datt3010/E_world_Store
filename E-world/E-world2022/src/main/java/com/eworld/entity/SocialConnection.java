package com.eworld.entity;

import com.eworld.contstant.Provider;
import lombok.*;

import javax.persistence.*;
@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String accessToken;

    private String refreshToken;

    private String idToken;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "id", updatable = false)
    private Account account;
    @Column(insertable = false, updatable = false)
    int accountId;

}
