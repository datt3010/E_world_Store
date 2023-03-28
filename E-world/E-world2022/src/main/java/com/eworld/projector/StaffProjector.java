package com.eworld.projector;

import com.eworld.dto.profile.AccountProfileDto;
import com.eworld.dto.staff.StaffDto;
import com.eworld.entity.Account;

import java.util.List;
import java.util.stream.Collectors;

public class StaffProjector {
    public static StaffDto convertToPageDto(Account entity) {
        return StaffDto.builder()
                .createdAt(entity.getCreateAt())
                .id(entity.getId())
                .accountProfileDto(AccountProfileDto.builder()
                        .accountId(entity.getId())
                        .firstName(entity.getAccountProfile().getFirstName())
                        .lastName(entity.getAccountProfile().getLastName())
                        .address(entity.getAccountProfile().getAddress())
                        .email(entity.getAccountProfile().getEmail())
                        .address(entity.getAccountProfile().getAddress())
                        .dateOfBirth(entity.getAccountProfile().getDateOfBirth())
                        .gioitinh(entity.getAccountProfile().getGioitinh())
                        .phone(entity.getAccountProfile().getPhone())
                        .nationality(entity.getAccountProfile().getNationality())
                        .status(entity.getAccountProfile().getStatus())
                        .logo(entity.getAccountProfile().getImage())
                        .build())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }

    public static List<StaffDto> convertToPageDto(List<Account> entities){
        return entities.stream()
                .map(e -> convertToPageDto(e))
                .collect(Collectors.toList());
    }

    public static StaffDto convertToDetailDto(Account entity) {
        return StaffDto.builder()
                .createdAt(entity.getCreateAt())
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
