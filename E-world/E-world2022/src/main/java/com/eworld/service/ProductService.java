package com.eworld.service;

import com.eworld.dto.product.ProductDto;
import com.eworld.dto.product.ProductInput;
import com.eworld.dto.product.ProductUpdate;
import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
		
	public  ProductDto create(ProductInput input,List<String> fileNameImages);
	
	public 	ProductDto update(Integer id, ProductUpdate input, List<String> fileNameImages);

	public Page<ProductDto> findPaging(ProductFilter filter, Pageable pageable);
		
	public void changeStatusToInactive(Integer id);

	public void changeStatusToActive(Integer id);

	public void changeStatusToArchive(Integer id);
	
	public ProductDto getDetail(String name);

	public ProductDto getDetailById(Integer id);
	
	public ProductDto findById(Integer id);

	public Page<Product> findProductByCategoryId(Integer categoryId , Pageable pageable);

	public Page<ProductDto> listProductHotSale(Integer month, Pageable pageable);

	public void increaseViews(String name);

}
