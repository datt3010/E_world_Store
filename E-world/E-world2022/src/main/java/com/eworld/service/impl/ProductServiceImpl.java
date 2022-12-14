package com.eworld.service.impl;

import com.eworld.contstant.ProductStatus;
import com.eworld.dto.product.ProductDto;
import com.eworld.dto.product.ProductInput;
import com.eworld.dto.product.ProductUpdate;
import com.eworld.entity.Category;
import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;
import com.eworld.projector.ProductProjector;
import com.eworld.repository.category.CategoryRepository;
import com.eworld.repository.product.ProductRepository;
import com.eworld.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository cateRepo;

	@Override
	@Transactional
	public ProductDto create(ProductInput input) {
		
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
				.image(input.getLogo())
				.urlVideo(input.getUrlVideo())
				.build();

			productRepo.save(product);

		
		return ProductDto.builder().id(product.getId()).build();
	}

	@Override
	@Transactional
	public ProductDto update(Integer id, ProductUpdate input) {
		Instant instant = Instant.now();
		Date date = Date.from(instant);
		Category category = cateRepo.findById(input.getCategoryId()).get(); 

		
		Product product = findbyId(id);
				product.setCreatedAt(date);
				product.setName(input.getName());
				product.setPrice(input.getPrice());
				product.setQuantity(input.getQuantity());
				product.setDescription(input.getDescription());
				product.setNgaybaohanh(input.getNgaybaohanh());
				product.setModels(input.getModels());
				product.setStatus(input.getStatus());
				product.setCategory(category);
				product.setImage(input.getLogo());
				product.setUrlVideo(input.getUrlVideo());

			productRepo.save(product);

		
		return ProductDto.builder().id(id).build();
	}

	@Override
	public Page<ProductDto> findPaging(String keyword, String sortField, String sortDir, int pageNum) {

		ProductFilter filter = ProductFilter.builder()
				.keyword(keyword)
				.build();

		Pageable pageable = PageRequest.of(pageNum-1, 3, sortDir.equals("asc")? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		Page<Product> page = productRepo.findPaging(filter, pageable);
		List<ProductDto> content = ProductProjector.convertToPageDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@Override
	@Transactional
	public void changeStatus(Integer id) {
		 productRepo.changeStatus(ProductStatus.INACTIVE, id);
	}

	@Override
	public ProductDto getDetail(Integer id) {
		
		Product product = productRepo.findById(id).get();
		ProductDto dto = ProductProjector.convertToDetailDto(product);
		
		return dto;
	}

	@Override
	public Product findbyId(Integer id) {
		return productRepo.findById(id).get();
	}
}
