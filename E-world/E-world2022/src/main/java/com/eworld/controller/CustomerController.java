package com.eworld.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eworld.dto.CustomerDto;
import com.eworld.dto.CustomerInput;
import com.eworld.dto.CustomerUpdate;
import com.eworld.filter.CustomerFilter;
import com.eworld.service.CustomerService;
import com.eworld.service.UploadService;

@Controller
@RequestMapping("admin")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UploadService uploadService;
	
	@GetMapping("/listcustomer")
	public String listByKeyWord(Model model, @RequestParam(name="keyword", required = false) String keyword) {
		
		Pageable pageable = PageRequest.of(0, 10, Direction.ASC,"id");
		
		CustomerFilter filter= CustomerFilter.builder()
		.keyword(keyword)
		.build();
		
		Page<CustomerDto> listCustomer = customerService.findPaging(filter, pageable);
		
		model.addAttribute("listCustomer", listCustomer);
		
		return searchPage(model, 1, keyword, "id","asc", pageable);
	}
	
	@PostMapping("/customer/insert")
	public String create(@ModelAttribute("customer") @Valid CustomerInput input,
			BindingResult result,
			Model model,
			@RequestParam("image") MultipartFile file) throws IOException {
		
		if(result.hasErrors()) {
			return "admin/customer/CustomerDashBoard";
		}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		input.setLogo(fileName);
		
		customerService.create(input);
		String uploadDirectory = "src/main/resources/static/images/staff/";
		uploadService.save(file, uploadDirectory);
		model.addAttribute("message","Insert thành công");
		
		return "admin/customer/CustomerDashBoard";
	}
	
	@RequestMapping("/customer")
	public String index(Model model) {
		model.addAttribute("customer", new CustomerDto());
		return "admin/customer/CustomerDashBoard";
	}
	
	@RequestMapping("/listcustomer/{id}")
	public String detail(Model model,@PathVariable("id") Integer id) {
		CustomerDto dto = customerService.getDetails(id);
		model.addAttribute("customer",dto);	
		return "admin/customer/CustomerDashBoard";
	}
	
	@RequestMapping("/listcustomer/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		customerService.delete(id);
		return "redirect:/admin/listcustomer";
	}
	
	@PostMapping("listcustomer/{id}")
	public String update(Model model,
			@PathVariable("id") Integer id, 
			@ModelAttribute("customer") @Valid CustomerUpdate input,
			BindingResult result,
			@RequestParam("image") MultipartFile file) throws IOException {
			
			if(result.hasErrors()) {
				return "admin/customer/CustomerDashBoard";
			}
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			input.setLogo(fileName);
			
			customerService.update(id, input);
			String uploadDir = "src/main/resources/static/images/staff/";
			uploadService.save(file, uploadDir);
			model.addAttribute("message", "Update thành công");
		
		return "forward:/admin/customer";
	}
	
	@RequestMapping("listcustomer/page/{pageNum}")
	public String searchPage(Model model,
			@PathVariable("pageNum") int pageNum,
			@RequestParam(name="keyword", required = false) String keyword,
			@RequestParam(name="sortField", required = false) String sortField,
			@RequestParam(name = "sortDir", required = false) String sortDir,
			@SortDefault(sort = "id", direction = Direction.ASC) Pageable pageable) {
		
		pageable = PageRequest.of(pageNum-1, 3, sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
		CustomerFilter filter = CustomerFilter.builder()
				.keyword(keyword)
				.build();
		Page<CustomerDto> listCustomer =  customerService.findPaging(filter, pageable);
		model.addAttribute("keyword", filter.getKeyword());
		model.addAttribute("listCustomer", listCustomer);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", listCustomer.getTotalPages());
		model.addAttribute("totalItems", listCustomer.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");
		
		return "admin/customer/ListCustomer";
	}
}
