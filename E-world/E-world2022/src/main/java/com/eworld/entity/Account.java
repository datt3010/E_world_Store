package com.eworld.entity;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {

    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Order> orders;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<AccountRole> accountRoles;

    @CreatedDate
    @Column(updatable = false)
    private Date createAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private Integer age;

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
