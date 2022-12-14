package com.eworld.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class StaffFilter {

    private String keyword;
}
