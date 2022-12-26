package com.eworld.controller.admin;

import com.eworld.contstant.OrderStatus;
import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.order.OrderDto;
import com.eworld.filter.OrderFilter;
import com.eworld.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("admin")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@RequestMapping("listorder/page/{pageNum}")
	public String listPage(Model model,
						   @PathVariable("pageNum") int pageNum,
						   @RequestParam(name = "keyword",required = false) String keyword,
						   @RequestParam(name = "accountId", required = false) Integer accountId,
						   @RequestParam(name = "sortField", required = false) String sortField,
						   @RequestParam(name = "status",required = false) OrderStatus status,
						   @RequestParam(name = "sortDir", required = false) String sortDir)
	{

		OrderFilter filter = OrderFilter.builder()
				.keyword(keyword)
				.accountId(accountId)
				.status(status)
				.build();

		Pageable pageable = PageRequest.of(pageNum-1, 5, sortDir.equals("asc")? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

		Page<OrderDto> listOrder = orderService.findpaging(filter,pageable);
		model.addAttribute("status", status);
		model.addAttribute("accountId", accountId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", listOrder.getTotalPages());
		model.addAttribute("totalItems", listOrder.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listOrder", listOrder);

		return "admin/order/ListOrder";
	}

	@RequestMapping("/listorder")
	public String listOrder(Model model){
		return listPage(model,1,null,null,"id",null,"asc");
	}

	@ModelAttribute("listAccount")
	public List<CustomerDto> getListAccount() {
		return  orderService.listAccount();
	}
	@RequestMapping("order/process/{id}")
	public String changeProcess(@PathVariable("id") Integer id){
		orderService.changeStatus(OrderStatus.IN_PROCRESS, id);
		return "redirect:/admin/listorder";
	}
	@RequestMapping("order/delivery/{id}")
	public String changeDelivery(@PathVariable("id") Integer id){
		orderService.changeStatus(OrderStatus.DELIVERING, id);
		return "redirect:/admin/listorder";
	}
	@RequestMapping("order/success/{id}")
	public String changeSucess(@PathVariable("id") Integer id){
		orderService.changeStatus(OrderStatus.SUCESSFULLY, id);
		return "redirect:/admin/listorder";
	}
}
