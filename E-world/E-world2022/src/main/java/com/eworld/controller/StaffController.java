package com.eworld.controller;

import com.eworld.dto.staff.StaffDto;
import com.eworld.dto.staff.StaffInput;
import com.eworld.dto.staff.StaffUpdate;
import com.eworld.filter.StaffFilter;
import com.eworld.service.StaffService;
import com.eworld.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
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
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    UploadService uploadService;

    @RequestMapping("/liststaff")
    public String listByKeyWord(Model model, @RequestParam(name="keyword", required = false) String keyword) {
        return searchPage(model, 1, keyword, "id","asc");
    }

    @PostMapping("/staff/insert")
    public String create(@ModelAttribute("staff") @Valid StaffInput input,
                         BindingResult result,
                         Model model,
                         @RequestParam("image") MultipartFile file) throws IOException {

        if(result.hasErrors()) {
            return "admin/staff/StaffDashBoard";
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        input.setLogo(fileName);

        staffService.create(input);
        String uploadDirectory = "src/main/resources/static/images/staff/";
        uploadService.save(file, uploadDirectory);
        model.addAttribute("message","Insert thành công");

        return "admin/staff/StaffDashBoard";
    }

    @RequestMapping("/staff")
    public String index(Model model) {
        model.addAttribute("staff", new StaffDto());
        return "admin/staff/StaffDashBoard";
    }

    @RequestMapping("/liststaff/{id}")
    public String detail(Model model,@PathVariable("id") Integer id) {
        StaffDto dto = staffService.getDetails(id);
        model.addAttribute("staff",dto);
        return "admin/staff/StaffDashBoard";
    }

    @RequestMapping("/liststaff/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        staffService.changeStatus(id);
        return "redirect:/admin/liststaff";
    }

    @PostMapping("liststaff/{id}")
    public String update(Model model,
                         @PathVariable("id") Integer id,
                         @ModelAttribute("staff") @Valid StaffUpdate input,
                         BindingResult result,
                         @RequestParam("image") MultipartFile file) throws IOException {

        if(result.hasErrors()) {
            return "admin/staff/StaffDashBoard";
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        input.setLogo(fileName);

        staffService.update(id, input);
        String uploadDir = "src/main/resources/static/images/staff/";
        uploadService.save(file, uploadDir);
        model.addAttribute("message", "Update thành công");

        return "forward:/admin/staff";
    }

    @RequestMapping("liststaff/page/{pageNum}")
    public String searchPage(Model model,
                             @PathVariable("pageNum") int pageNum,
                             @RequestParam(name="keyword", required = false) String keyword,
                             @RequestParam(name="sortField", required = false) String sortField,
                             @RequestParam(name = "sortDir", required = false) String sortDir
                           ) {

       Pageable pageable = PageRequest.of(pageNum-1, 3, sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
        StaffFilter filter = StaffFilter.builder()
                .keyword(keyword)
                .build();
        Page<StaffDto> listStaff =  staffService.findPaging(filter, pageable);
        model.addAttribute("keyword", filter.getKeyword());
        model.addAttribute("listStaff", listStaff);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", listStaff.getTotalPages());
        model.addAttribute("totalItems", listStaff.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"asc":"desc");

        return "admin/staff/ListStaff";
    }

}
