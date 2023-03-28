package com.eworld.dto.staff;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import com.eworld.dto.profile.AccountProfileDto;
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

    private AccountProfileDto accountProfileDto;
    private List<Order> orders;

    private Set<Role> roles;
}
