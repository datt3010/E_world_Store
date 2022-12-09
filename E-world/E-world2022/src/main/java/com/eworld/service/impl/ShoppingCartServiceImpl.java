package com.eworld.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.eworld.entity.Product;
import com.eworld.exception.NotEnoughProductsInStockException;
import com.eworld.repository.product.ProductRepository;
import com.eworld.service.ProductService;
import com.eworld.service.ShoppingCartService;


@Service("cart")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(readOnly = true)
public class ShoppingCartServiceImpl  implements ShoppingCartService {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepo;
	
    private Map<Product, Integer> products = new HashMap<>();
	
	@Override
	@Transactional
	 public void addProduct(Product product) {
		
		Product p = products.keySet().stream()
				.filter(pp -> pp.getId() == product.getId())
				.findFirst()
				.orElse(product);
		
		Integer cnt = products.get(p);
		if (cnt != null) {
			products.replace(p, cnt + 1);
		}
        else {
            products.put(p, 1);
        }
    }
	@Override
	@Transactional
    public void removeProduct(Product product) {
		
		Product p = products.keySet().stream()
				.filter(pp -> pp.getId() == product.getId())
				.findFirst()
				.orElse(product);
		
		Integer cnt = products.get(p);
		if (cnt > 1) {
			products.replace(p, cnt - 1);
		}
        else {
            products.remove(p);
        }
    }

	@Override
	public Map<Product, Integer> getProductInCart() {
        return Collections.unmodifiableMap(products);
	}

	@Override
	public void checkout() throws NotEnoughProductsInStockException  {
		
        Product product;
	
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
        	product = productService.findbyId(entry.getKey().getId());
				if(product.getQuantity() < entry.getValue()) {
					throw new NotEnoughProductsInStockException(product);
				}
				entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
		}
        productRepo.saveAll(products.keySet());
        productRepo.flush();
        products.clear();
	}

	@Override
	public Double getTotal() {
		double totalPrice = 0;
		for (Entry<Product, Integer> items : products.entrySet()) {
			if (products.containsKey(items.getKey())) {
				totalPrice += items.getKey().getPrice() * items.getValue();
			}
		}
		return totalPrice;

	}
}
