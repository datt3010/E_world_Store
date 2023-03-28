package com.eworld.dto.staff;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import com.eworld.dto.profile.AccountProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffInput {
    private Integer id;

    private Date createdAt;

    private AccountProfileDto accountProfileDto;

    @NotBlank(message = "{Account.username}")
    @Size(max = 100, min = 8, message = "{Size.Account.username.max}")
    private String username;

    @NotBlank(message = "{Account.password}")
    @Size(max = 100, message = "{Size.Account.password}")
    private String password;


    private Set<Integer> roleIds= new HashSet<>();
}
