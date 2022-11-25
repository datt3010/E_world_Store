package com.eword.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;

public class SorttableUtil {
	
	public static void validate(Sort sort, Collection<String> sortableFields) {
		List<String> inputFields = sort.stream().map(e -> e.getProperty()).collect(Collectors.toList());
		
		Collection<String> unsupportFields = CollectionUtils.removeAll(inputFields, sortableFields);
		
		if(CollectionUtils.isNotEmpty(unsupportFields)) {
			return ;
		}
	}
}
