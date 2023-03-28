package com.eworld.controller.admin;

import com.eworld.dto.customer.CustomerDto;
import com.eworld.filter.CustomerFilter;
import com.eworld.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("listuser/page/{pageNum}")
    public String searchPage(Model model,
                             @PathVariable("pageNum") int pageNum,
                             @RequestParam(name="keyword", required = false) String keyword,
                             @RequestParam(name="sortField", required = false) String sortField,
                             @RequestParam(name = "sortDir", required = false) String sortDir
    ) {

        Pageable pageable = PageRequest.of(pageNum-1, 3, sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
        CustomerFilter filter = CustomerFilter.builder()
                .keyword(keyword)
                .roleId(1)
                .build();
        Page<CustomerDto> listCustomer =  roleService.listUser(filter, pageable);
        model.addAttribute("keyword", filter.getKeyword());
        model.addAttribute("listCustomer", listCustomer);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", listCustomer.getTotalPages());
        model.addAttribute("totalItems", listCustomer.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        return "admin/role/ListUser";
    }
    @GetMapping("/listuser")
    public String listByKeyWord(Model model) {
        return searchPage(model, 1, null, "id","asc");
    }

}
