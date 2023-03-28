package com.eworld.filter;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
@Setter
public class CustomerFilter {
	
	private String keyword;

	private Integer month;

	private Integer years;

	private Integer roleId;
}
