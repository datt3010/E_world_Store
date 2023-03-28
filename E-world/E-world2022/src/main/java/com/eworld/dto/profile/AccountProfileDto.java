package com.eworld.dto.profile;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AccountProfileDto {

    private  Integer accountId;
    @NotBlank(message = "{Account.email}")
    private String email;
    @NotBlank(message = "{Account.phone}")
    private String phone;
    @NotBlank(message = "{Account.firstName}")
    @Size(max = 100)
    private String firstName;
    @NotBlank(message = "{Account.lastName}")
    @Size(max = 100)
    private String lastName;

    private Gender gioitinh;
    @NotNull(message = "{Account.dateOfBirth}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @NotBlank(message = "{Account.address}")
    @Size(max = 255)
    private String address;

    private String nationality;

    private String logo;
    @NotNull(message = "{Account.status}")
    private UserStatus status;
}
