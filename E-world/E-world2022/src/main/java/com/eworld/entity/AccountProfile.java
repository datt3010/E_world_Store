package com.eworld.entity;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class AccountProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int accountId;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gioitinh;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String address;

    private String nationality;

    private String image;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
