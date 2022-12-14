package com.eworld.service;

import com.eworld.dto.staff.StaffDto;
import com.eworld.dto.staff.StaffInput;
import com.eworld.dto.staff.StaffUpdate;
import com.eworld.entity.Account;
import com.eworld.filter.StaffFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StaffService {
    public Account findById(Integer id);

    public Page<StaffDto> findPaging(StaffFilter filter, Pageable pageable);

    public StaffDto create(StaffInput input);

    public StaffDto getDetails(Integer id);

    public void changeStatus(Integer id);

    public StaffDto update(Integer id, StaffUpdate input);
}
