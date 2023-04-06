package com.eworld.controller.user;

import com.eworld.configuration.security.UserContext;
import com.eworld.configuration.security.UserContextService;
import com.eworld.dto.category.CategoryDto;
import com.eworld.dto.product.ProductDto;
import com.eworld.filter.CategoryFilter;
import com.eworld.filter.ProductFilter;
import com.eworld.service.CategoryService;
import com.eworld.service.OrderService;
import com.eworld.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("product")
public class WebController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserContextService userContextService;

	@RequestMapping("/{name}")
	public String getDetails(Model model, @PathVariable("name")String name) {
		productService.increaseViews(name);
		ProductDto dto = productService.getDetail(name);
		model.addAttribute("product", dto);

		return "user/product/ProductDetail";
	}

	@RequestMapping
	public String listAll(Model model) {
		return  listPage(model,1,null,"id","asc",null,null);
	}

	@GetMapping("/page/{pageNum}")
	public String listPage(Model model,
			@PathVariable("pageNum") int pageNum,
			@RequestParam(name="keyword", required = false)String keyword,
			@RequestParam(name="sortField", required = false) String sortField,
			@RequestParam(name="sortDir", required = false) String sortDir,
	   		@RequestParam(name = "categoryId", required = false) Integer categoryId,
		   	@RequestParam(name = "brandId",required = false) Integer brandId)
	{
		Pageable pageable = PageRequest.of(pageNum-1, 3, sortDir.equals("asc")? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		ProductFilter filter = ProductFilter.builder()
				.keyword(keyword)
				.categoryId(categoryId)
				.brandId(brandId)
				.build();
		Page<ProductDto> listProduct = productService.findPaging(filter,pageable);
		model.addAttribute("brandId", brandId);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("keyword",keyword);
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", listProduct.getTotalPages());
		model.addAttribute("totalProduct", listProduct.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

		return "user/product/ListProduct.html";
		}

	@RequestMapping("/search")
	public String searchByKey(Model model,
					@RequestParam(value = "keyword",required = false)String keyword,
					@RequestParam(value = "categoryId", required = false) Integer categoryId,
				    @RequestParam(value = "brandId",required = false) Integer brandId) {
		return listPage(model, 1, keyword,"name", "asc",categoryId,brandId);
	}

	@RequestMapping("product/")
	public String listBySelection(Model model) {
		return listPage(model,1, null,"id","asc",null,null);
	}

	@ModelAttribute("categories")
	public Page<CategoryDto> listCategoryByBrandId(@Param(value ="brandId" ) Integer brandId){
		Pageable pageable = PageRequest.of(0,5,Direction.ASC,"name");
		CategoryFilter filter = CategoryFilter.builder()
				.brandId(brandId)
				.build();
		Page<CategoryDto> page = categoryService.findPaging(filter,pageable);
		return page;
	}

	@RequestMapping("/404")
	public String errorPage(){
		return "user/error/404";
	}

	@RequestMapping("/shoppingcart")
	public String viewCart(){
		return "user/product/Cart";
	}
	@RequestMapping("/order/checkout")
	public String viewCheckOut(){
		return "user/order/Order";
	}
	@RequestMapping("/order/detail/{id}")
	public String oderDetail(@PathVariable("id")Integer id, Model model){
		model.addAttribute("order",orderService.findById(id));
		return "user/order/OrderDetail";
	}

	@RequestMapping("/order/list")
	public String listOrder(Model model, HttpServletRequest request){
		UserContext userContext = userContextService.findCurrent().get();
		model.addAttribute("orders",orderService.findByUserName(userContext.getAccount().getUsername()));
		return "user/order/OrderList";
	}
}
