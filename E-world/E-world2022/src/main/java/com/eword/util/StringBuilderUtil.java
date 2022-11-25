package com.eword.util;

public class StringBuilderUtil {
	
	public static StringBuilder append(StringBuilder builder, Object... objs) {
		for (Object object : objs) {
			builder.append(object);
		}
		return builder;
	}
}
