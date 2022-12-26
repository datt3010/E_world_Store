package com.eworld.controller.admin;

import com.eworld.dto.category.CategoryDto;
import com.eworld.dto.customer.CustomerDto;
import com.eworld.dto.product.ProductDto;
import com.eworld.entity.Account;
import com.eworld.filter.CustomerFilter;
import com.eworld.service.CategoryService;
import com.eworld.service.CustomerService;
import com.eworld.service.OrderService;
import com.eworld.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("admin")
@Controller
public class StatisticalController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("statistical")
    public String listAccount(Model model)
    {
        model.addAttribute("sumRevenueBySeptember", orderService.sumRevenueByMonth(9) == null ? 0 : orderService.sumRevenueByMonth(9));
        model.addAttribute("sumRevenueByOctober", orderService.sumRevenueByMonth(10) == null ? 0 :  orderService.sumRevenueByMonth(10));
        model.addAttribute("sumRevenueByNovember",orderService.sumRevenueByMonth(11) == null ? 0 :  orderService.sumRevenueByMonth(11));
        model.addAttribute("sumRevenueByDecember", orderService.sumRevenueByMonth(12) == null ? 0 :  orderService.sumRevenueByMonth(12));
        model.addAttribute("sumRevenueByYear", orderService.sumRevenueByYear(2022));
        return searchPage(model,1,null,"id","asc",null,null);
    }

    @ModelAttribute("listMonth")
    public List<Integer> listMonth(){

        List<Integer> listMonth = new ArrayList<>();
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);

        return listMonth = numbers.stream()
                .filter(e -> e<=12)
                .collect(Collectors.toList());
    }

    @ModelAttribute("listYears")
    public List<Integer> listYears(){

        List<Integer> listYears = new ArrayList<>();
        List<Integer> numbers = Arrays.asList(2022,2023,2024,2025,2026,2027,2028,2029,2030);
        return listYears = numbers.stream()
                .collect(Collectors.toList());
    }

    @RequestMapping("listAccount/page/{pageNum}")
    public String searchPage(Model model,
                             @PathVariable("pageNum") int pageNum,
                             @RequestParam(name="keyword", required = false) String keyword,
                             @RequestParam(name="sortField", required = false) String sortField,
                             @RequestParam(name = "sortDir", required = false) String sortDir,
                             @RequestParam(name = "month", required = false) Integer month,
                             @RequestParam(name = "years", required = false) Integer years
    ) {

        Pageable pageable = PageRequest.of(pageNum-1, 3, sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
        CustomerFilter filter = CustomerFilter.builder()
                .keyword(keyword)
                .month(month)
                .years(years)
                .build();
        Page<CustomerDto> listAccount =  customerService.findPaging(filter, pageable);
        model.addAttribute("keyword", filter.getKeyword());
        model.addAttribute("month", filter.getMonth());
        model.addAttribute("years", filter.getYears());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", listAccount.getTotalPages());
        model.addAttribute("totalItems", listAccount.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"asc":"desc");

        model.addAttribute("listAccount", listAccount);

        return "admin/statistical/ListStatistical";
    }
    @ModelAttribute("listProductHotSale")
    public List<ProductDto> listProductHotSale(@RequestParam(value = "month", required = false)Integer month){
        Pageable pageable = PageRequest.of(0,3,Sort.by("id").ascending());
        Page<ProductDto> listProductHotSale = productService.listProductHotSale(12,pageable);
        return listProductHotSale.getContent();
    }

    @ModelAttribute("listCategoryHotSale")
    public List<CategoryDto> listCategoryHotSale(@RequestParam(value = "month", required = false)Integer month){
        return categoryService.listCategoryHotSale(12);
    }

    @ModelAttribute("listAccountTotalPrice")
    public List<Account> listAccountTotalPrice(@RequestParam(value = "month",required = false) Integer month){
        return customerService.listAccountTotalPrice(12);
    }
}
