package com.eworld.controller.admin;

import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.customer.CustomerInput;
import com.eworld.dto.customer.CustomerUpdate;
import com.eworld.filter.CustomerFilter;
import com.eworld.service.CustomerService;
import com.eworld.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("admin")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UploadService uploadService;
	
	@GetMapping("/listcustomer")
	public String listByKeyWord(Model model) {
		return searchPage(model, 1, null, "id","asc");
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
		input.getAccountProfileDto().setLogo(fileName);
		
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
		customerService.changeStatus(id);
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
			if(file==null) {
				input.getAccountProfileDto().setLogo(null);
			}
			if(!file.isEmpty()){
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				input.getAccountProfileDto().setLogo(fileName);
				String uploadDir = "src/main/resources/static/images/staff/";
				uploadService.save(file, uploadDir);
			}
			customerService.update(id, input);
			model.addAttribute("message", "Update thành công");
		
		return "forward:/admin/customer";
	}
	
	@RequestMapping("listcustomer/page/{pageNum}")
	public String searchPage(Model model,
			@PathVariable("pageNum") int pageNum,
			@RequestParam(name="keyword", required = false) String keyword,
			@RequestParam(name="sortField", required = false) String sortField,
			@RequestParam(name = "sortDir", required = false) String sortDir
			) {
		
		Pageable pageable = PageRequest.of(pageNum-1, 3, sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
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
