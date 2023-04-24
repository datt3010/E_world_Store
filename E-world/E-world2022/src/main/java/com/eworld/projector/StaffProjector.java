package com.eworld.projector;

import com.eworld.dto.profile.AccountProfileDto;
import com.eworld.dto.staff.StaffDto;
import com.eworld.entity.Account;

import java.util.List;
import java.util.stream.Collectors;

public class StaffProjector {
    public static List<StaffDto> convertToPageDto(List<Account> entities){
        return entities.stream()
                .map(e -> convertToDetailDto(e))
                .collect(Collectors.toList());
    }

    public static StaffDto convertToDetailDto(Account entity) {
        return StaffDto.builder()
                .createdAt(entity.getCreateAt())
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .accountProfileDto(AccountProfileDto.builder()
                        .firstName(entity.getAccountProfile().getFirstName())
                        .lastName(entity.getAccountProfile().getLastName())
                        .address(entity.getAccountProfile().getAddress())
                        .nationality(entity.getAccountProfile().getNationality())
                        .dateOfBirth(entity.getAccountProfile().getDateOfBirth())
                        .email(entity.getAccountProfile().getEmail())
                        .phone(entity.getAccountProfile().getPhone())
                        .logo(entity.getAccountProfile().getImage())
                        .gioitinh(entity.getAccountProfile().getGioitinh())
                        .status(entity.getAccountProfile().getStatus())
                        .build())
                .build();
    }
}
