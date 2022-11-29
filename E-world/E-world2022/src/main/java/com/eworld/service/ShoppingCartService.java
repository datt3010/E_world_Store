package com.eworld.service;

import java.util.Map;

import com.eworld.entity.Product;
import com.eworld.exception.NotEnoughProductsInStockException;

public interface ShoppingCartService {
	
	public void addProduct(Product product);
	
	public void removeProduct(Product product);
	
	public Map<Product, Integer> getProductInCart();
	
	public void checkout() throws NotEnoughProductsInStockException;		
	
	public Double getTotal();
	
}
