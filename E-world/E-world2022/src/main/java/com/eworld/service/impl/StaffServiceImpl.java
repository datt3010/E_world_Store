package com.eworld.service.impl;

import com.eworld.contstant.UserStatus;
import com.eworld.dto.staff.StaffDto;
import com.eworld.dto.staff.StaffInput;
import com.eworld.dto.staff.StaffUpdate;
import com.eworld.entity.Account;
import com.eworld.entity.AccountRole;
import com.eworld.entity.Role;
import com.eworld.filter.StaffFilter;
import com.eworld.projector.StaffProjector;
import com.eworld.repository.role.AccountRoleRepository;
import com.eworld.repository.role.RoleRepository;
import com.eworld.repository.staff.StaffRepository;
import com.eworld.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Override
    public Account findById(Integer id) {
        return staffRepository.findById(id).get();
    }

    @Override
    public Page<StaffDto> findPaging(StaffFilter filter, Pageable pageable) {
        Page<Account> page = staffRepository.findPaging(filter, pageable);
        List<StaffDto> content = StaffProjector.convertToPageDto(page.getContent());
        return new PageImpl<>(content,pageable,page.getTotalElements());
    }

    @Override
    @Transactional
    public StaffDto create(StaffInput input) {
        Instant instant = Instant.now();
        Date date = Date.from(instant);

        Role role = roleRepository.findById("2").orElseThrow();

        Account account = Account.builder()
                .createAt(date)
                .username(input.getUsername())
                .password(input.getPassword())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .age(input.getAge())
                .address(input.getAddress())
                .nationality(input.getNationality())
                .dateOfBirth(input.getDateOfBirth())
                .email(input.getEmail())
                .phone(input.getPhone())
                .image(input.getLogo())
                .status(input.getStatus())
                .gioitinh(input.getGioitinh())
                .build();

        AccountRole accountRole = accountRoleRepository.findByAccountId(account.getId());
        account.setAccountRoles(Set.of(accountRole = AccountRole.builder()
                .account(account)
                .accountId(input.getId())
                .role(role)
                .roleId(role.getId())
                .build()));

        staffRepository.save(account);
        return StaffDto.builder()
                .id(account.getId())
                .build();
    }

    @Override
    public StaffDto getDetails(Integer id) {

        Account account = staffRepository.findById(id).get();
        StaffDto dto = StaffProjector.convertToDetailDto(account);
        return dto;
    }

    @Override
    @Transactional
    public void changeStatus(Integer id) {
        staffRepository.changeStatus(UserStatus.INACTIVE,id);
    }

    @Override
    @Transactional
    public StaffDto update(Integer id, StaffUpdate input) {
        Instant instant = Instant.now();
        Date date = Date.from(instant);

        Account account = findById(id);
        account.setCreateAt(date);
        account.setUsername(input.getUsername());
        account.setPassword(input.getPassword());
        account.setFirstName(input.getFirstName());
        account.setLastName(input.getLastName());
        account.setAge(input.getAge());
        account.setDateOfBirth(input.getDateOfBirth());
        account.setAddress(input.getAddress());
        account.setEmail(input.getEmail());
        account.setGioitinh(input.getGioitinh());
        account.setStatus(input.getStatus());
        account.setImage(input.getLogo());
        staffRepository.save(account);

        return StaffDto.builder().id(id).build();
    }
}
