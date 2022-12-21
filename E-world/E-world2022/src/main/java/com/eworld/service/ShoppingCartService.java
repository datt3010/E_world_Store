package com.eworld.service;

import com.eworld.dto.order.OrderDto;
import com.fasterxml.jackson.databind.JsonNode;

public interface ShoppingCartService {
	
/*	public void addProduct(Product product);
	
	public void removeProduct(Product product);
	
	public Map<Product, Integer> getProductInCart();
	
	public void checkout() throws NotEnoughProductsInStockException;		
	
	public Double getTotal();*/

	public OrderDto createOrder(JsonNode orderData);

}
