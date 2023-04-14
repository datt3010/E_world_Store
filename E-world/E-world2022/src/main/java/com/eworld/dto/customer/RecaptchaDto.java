package com.eworld.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecaptchaDto {
    private boolean success;

    private String challenge_ts;

    private String hostName;
}
