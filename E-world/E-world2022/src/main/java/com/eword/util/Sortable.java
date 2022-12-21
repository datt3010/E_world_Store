package com.eword.util;

import java.util.Arrays;
import java.util.List;

public class Sortable {

	public static final List<String> CUSTOMER = Arrays.asList(
			"id", "firstName", "lastName", "age", "gioitinh");
	
	public static final List<String> CATEGORY = Arrays.asList(
			"id", "name", "status");
	
	public static final List<String> PRODUCT = Arrays.asList(
			"id", "name", "status", "price", "category");
	
	public static final List<String> BRAND = Arrays.asList(
			"id", "name");
	
	public static final List<String> ORDER = Arrays.asList(
			"id", "totalPrice");

	public static  final List<String> STAFF = Arrays.asList(
			"id","lastName", "age", "gioitinh"
	);
	public static final List<String> BLOG = Arrays.asList(
			"id", "name", "status"
	);
	public static final List<String> CARTITEMS = Arrays.asList(
			"id"
	);
}
