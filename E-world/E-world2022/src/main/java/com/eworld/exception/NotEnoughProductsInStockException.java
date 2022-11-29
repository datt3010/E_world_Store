package com.eworld.exception;

import com.eworld.entity.Product;

public class NotEnoughProductsInStockException extends Exception {
	
	private static final String DEFAUL_MESSAGE = "Not enough products in stock";
	
	public NotEnoughProductsInStockException() {
		super(DEFAUL_MESSAGE);
	}
	
	public NotEnoughProductsInStockException(Product product) {
        super(String.format("Not enough %s products in stock. Only %d left", product.getName(), product.getQuantity()));
	}
}
