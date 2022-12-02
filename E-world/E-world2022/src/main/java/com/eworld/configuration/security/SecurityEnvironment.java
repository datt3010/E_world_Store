package com.eworld.configuration.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SecurityEnvironment {

    private String[] nonAuthentication;

    private List<String> listAuthentication = Arrays.asList("Admin", "Staff");

}
