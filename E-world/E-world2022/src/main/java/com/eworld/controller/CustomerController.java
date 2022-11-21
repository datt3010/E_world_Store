package com.eworld.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eworld.filter.CustomerFilter;
import com.eworld.schema.CustomerDto;
import com.eworld.schema.CustomerInput;
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
		
		return "admin/customer/ListCustomer";
	}
	
	@PostMapping("/customer/insert")
	public String create(@ModelAttribute("customer") CustomerInput input, Model model, @RequestParam("image") MultipartFile file) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		input.setLogo(fileName);
		
		customerService.create(input);
		String uploadDirectory = "src/main/resources/static/images/staff/" +input.getId();
		
		uploadService.save(file, uploadDirectory);
		
		return "admin/customer/CustomerDashBoard";
	}
	
	@RequestMapping("/customer")
	public String index(Model model) {
		model.addAttribute("customer", new CustomerInput());
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
}
