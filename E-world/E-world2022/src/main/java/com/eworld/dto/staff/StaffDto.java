package com.eworld.dto.staff;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import com.eworld.entity.Order;
import com.eworld.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffDto {

    private Date createdAt;

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private Gender gioitinh;

    private Integer age;

    private Date dateOfBirth;

    private String address;

    private String nationality;

    private String logo;

    private UserStatus status;

    private List<Order> orders;

    private Set<Role> roles;
}
