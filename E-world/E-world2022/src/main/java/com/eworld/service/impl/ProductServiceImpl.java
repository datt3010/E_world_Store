package com.eworld.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eworld.dto.ProductDto;
import com.eworld.dto.ProductInput;
import com.eworld.dto.ProductUpdate;
import com.eworld.entity.Category;
import com.eworld.entity.ImagesProduct;
import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;
import com.eworld.projector.ProductProjector;
import com.eworld.repository.CategoryRepository;
import com.eworld.repository.ImagesProductRepository;
import com.eworld.repository.ProductRepository;
import com.eworld.service.ProductService;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository cateRepo;
	
	@Autowired
	private ImagesProductRepository imagesProductRepo;

	@Override
	@Transactional
	public ProductDto create(ProductInput input, String fileName) {
		
		Instant instant = Instant.now();
		Date date = Date.from(instant);
		
		Category category = cateRepo.findById(input.getCategoryId()).get(); 
		Product product = Product.builder()
				.createdAt(date)
				.id(input.getId())
				.name(input.getName())
				.price(input.getPrice())
				.quantity(input.getQuantity())
				.description(input.getDescription())
				.ngaybaohanh(input.getNgaybaohanh())
				.models(input.getModels())
				.status(input.getStatus())
				.category(category)
				.build();
		
			Set<ImagesProduct> imagesProducts = imagesProductRepo.findByProductId(input.getId()).stream()
					.map(e -> ImagesProduct.builder()
							.product(product)
							.productId(input.getId())
							.url(fileName)
							.build())
					.collect(Collectors.toSet());
			
		product.setImagesProducts(imagesProducts);
			productRepo.save(product);
		
		return ProductDto.builder().id(product.getId()).build();
	}

	@Override
	public ProductDto update(Integer id, ProductUpdate input) {
		return null;
	}

	@Override
	public Page<ProductDto> findPaging(ProductFilter filter, Pageable pageable) {
		
		Page<Product> page = productRepo.findPaging(filter, pageable);
		List<ProductDto> content = ProductProjector.convertToPageDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@Override
	public void deleteById(Integer id) {
		 productRepo.deleteById(id);

	}

	@Override
	public ProductDto getDetail(Integer id) {
		
		Product product = productRepo.findById(id).get();
		ProductDto dto = ProductProjector.convertToDetailDto(product);
		
		return dto;
	}

}
