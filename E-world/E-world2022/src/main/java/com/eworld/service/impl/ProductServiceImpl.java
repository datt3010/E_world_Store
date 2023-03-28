package com.eworld.service.impl;

import com.eworld.contstant.ProductStatus;
import com.eworld.dto.product.ProductDto;
import com.eworld.dto.product.ProductInput;
import com.eworld.dto.product.ProductUpdate;
import com.eworld.entity.Category;
import com.eworld.entity.Product;
import com.eworld.entity.ProductImages;
import com.eworld.filter.ProductFilter;
import com.eworld.projector.ProductProjector;
import com.eworld.repository.category.CategoryRepository;
import com.eworld.repository.product.ProductRepository;
import com.eworld.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository cateRepo;

	@Override
	@Transactional
	public ProductDto create(ProductInput input,List<String> fileNameImages) {
		
		Instant instant = Instant.now();
		Date date = Date.from(instant);
		Category category = cateRepo.findById(input.getCategoryId()).orElseThrow();
		Product product = Product.builder()
				.createdAt(date)
				.updatedAt(date)
				.id(input.getId())
				.name(input.getName())
				.price(input.getPrice())
				.quantity(input.getQuantity())
				.description(input.getDescription())
				.ngaybaohanh(input.getNgaybaohanh())
				.status(input.getStatus())
				.category(category)
				.urlVideo(input.getUrlVideo())
				.build();

		Set<ProductImages> productImagesSet = input.getProductImages();
		productImagesSet= fileNameImages.stream()
						.map(fileName ->{
							ProductImages productImage = new ProductImages();
							productImage.setImage(fileName);
							productImage.setProduct(product);
							return productImage;
						}).collect(Collectors.toSet());
		product.setProductImages(productImagesSet);

			productRepo.save(product);

		return ProductDto.builder().id(product.getId()).build();
	}

	@Override
	@Transactional
	public ProductDto update(Integer id, ProductUpdate input, List<String> fileNameImages) {
		Instant instant = Instant.now();
		Date date = Date.from(instant);
		Category category = cateRepo.findById(input.getCategoryId()).get(); 

		
		Product product = productRepo.findById(id).orElseThrow();
				product.setUpdatedAt(date);
				product.setName(input.getName());
				product.setPrice(input.getPrice());
				product.setQuantity(input.getQuantity());
				product.setDescription(input.getDescription());
				product.setNgaybaohanh(input.getNgaybaohanh());
				product.setStatus(input.getStatus());
				product.setCategory(category);
				product.setUrlVideo(input.getUrlVideo());

		Set<ProductImages> existingImages = product.getProductImages();
		Set<ProductImages> uploadImages = new HashSet<>();
		fileNameImages.stream().forEach( filname ->{
			ProductImages productImages = existingImages.stream().filter(i -> i.getImage().equals(filname)).findFirst().orElse(new ProductImages());
			productImages.setImage(filname);
			productImages.setProduct(product);
			uploadImages.add(productImages);
		});
			existingImages.clear();
			existingImages.addAll(uploadImages);
			productRepo.save(product);

		
		return ProductDto.builder().id(id).build();
	}

	@Override
	public Page<ProductDto> findPaging(ProductFilter filter, Pageable pageable) {
		Page<Product> page = productRepo.findPaging(filter, pageable);
		List<ProductDto> content = ProductProjector.convertToPageDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@Override
	@Transactional
	public void changeStatusToInactive(Integer id) {
		 productRepo.changeStatus(ProductStatus.INACTIVE, id);
	}

	@Override
	public void changeStatusToActive(Integer id) {
		productRepo.changeStatus(ProductStatus.ACTIVE, id);
	}

	@Override
	public void changeStatusToArchive(Integer id) {
		productRepo.changeStatus(ProductStatus.ARCHIVED,id);
	}

	@Override
	public ProductDto getDetail(String name) {
		
		Product product = productRepo.getProductByName(name);
		ProductDto dto = ProductProjector.convertToDetailDto(product);
		return dto;
	}

	@Override
	public ProductDto getDetailById(Integer id) {
		Product product = productRepo.findById(id).orElseThrow();
		ProductDto dto = ProductProjector.convertToDetailDto(product);
		return dto;
	}

	@Override
	public ProductDto findById(Integer id) {
		Product product =productRepo.findById(id).orElseThrow();
		ProductDto dto = ProductProjector.convertToDetailDto(product);
		return  dto;
	}

	public Page<Product> findProductByCategoryId(Integer categoryId , Pageable pageable) {
		return productRepo.listProductByCategoryId(categoryId,pageable);
	}

	@Override
	public Page<ProductDto> listProductHotSale(Integer month, Pageable pageable) {
		Page<Product> page = productRepo.listProductHotSale(month,pageable);
		List<ProductDto> listDto = ProductProjector.convertToPageDto(page.getContent());
		return new PageImpl<>(listDto,pageable,page.getTotalElements());
	}

	@Override
	@Transactional
	public void increaseViews(String name) {
		Product product = productRepo.getProductByName(name);
		if(product.getViews() == null){
			product.setViews(1);
		}
		product.setViews(product.getViews()+1);
		productRepo.save(product);
	}

}
