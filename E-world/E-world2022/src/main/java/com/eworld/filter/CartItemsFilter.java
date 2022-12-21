package com.eworld.filter;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
@Setter
public class CartItemsFilter {

    private String keyword;

    private Integer accountId;
}
