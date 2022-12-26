package com.eworld.service.impl;

import com.eworld.dto.order.OrderDto;
import com.eworld.entity.Order;
import com.eworld.entity.OrderDetail;
import com.eworld.repository.order.OrderDetailRepository;
import com.eworld.repository.order.OrderRepository;
import com.eworld.service.ShoppingCartService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service("cart")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(readOnly = true)
public class ShoppingCartServiceImpl  implements ShoppingCartService {
/*
	@Autowired
	private ProductRepository productRepo;
	
    private Map<Product, Integer> products = new HashMap<>();
	@Autowired
	private CustomerRepository customerRepository;*/

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

/*
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
        	product = productRepo.findById(entry.getKey().getId()).orElseThrow();
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
*/

	@Override
	public OrderDto createOrder(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.convertValue(orderData, Order.class);
		orderRepository.save(order);
		TypeReference<Set<OrderDetail>> type = new TypeReference<Set<OrderDetail>>(){};
		List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"),type)
				.stream()
				.peek(d -> d.setOrder(order)).collect(Collectors.toList());
		orderDetailRepository.saveAll(details);

		return OrderDto.builder().id(order.getId()).build();
	}

}
