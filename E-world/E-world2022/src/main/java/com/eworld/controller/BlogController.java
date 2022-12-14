package com.eworld.controller;

import com.eworld.dto.blog.BlogDto;
import com.eworld.dto.blog.BlogInput;
import com.eworld.dto.blog.BlogUpdate;
import com.eworld.filter.BlogFilter;
import com.eworld.service.BlogService;
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
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UploadService uploadService;

    @PostMapping("/blog/insert")
    public String create (@ModelAttribute("blog") @Valid BlogInput input,
                          BindingResult result,
                          Model model,
                          @RequestParam("logo") MultipartFile file) throws IOException {

        if(result.hasErrors()) {
            return "admin/blog/BlogDashBoard";
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        input.setImage(fileName);

        blogService.create(input);
        String uploadDirectory = "src/main/resources/static/images/product/" +input.getId();
        uploadService.save(file, uploadDirectory);
        model.addAttribute("message","Thêm mới thành công ^-^");

        return "admin/blog/BlogDashBoard";
    }

    @RequestMapping("/blog")
    public String crudBrand(Model model) {

        model.addAttribute("blog", new BlogDto());

        return "admin/blog/BlogDashBoard";
    }

    @RequestMapping("/listblog")
    public String listBrand(Model model, @RequestParam(name = "keyword", required = false)String keyword) {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC,"name");

        BlogFilter filter = BlogFilter.builder()
                .keyword(keyword)
                .build();

        Page<BlogDto> listBlog = blogService.findPaging(filter, pageable);
        model.addAttribute("listBlog", listBlog);

        return listPage(model, 1, keyword, "name", "asc", pageable);
    }

    @RequestMapping("listblog/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {

        BlogDto blog = blogService.getDetail(id);

        model.addAttribute("blog",blog);
        return "admin/blog/BlogDashBoard";
    }

    @RequestMapping("blog/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        blogService.deleteById(id);
        return "redirect:/admin/listblog";
    }

    @PostMapping("/blog/{id}")
    public String update(@ModelAttribute("blog") @Valid BlogUpdate input, BindingResult result, Model model, @RequestParam("image") MultipartFile file, @PathVariable("id") Integer id) throws IOException {

        if(result.hasErrors()) {
            return "admin/brand/BrandDashBoard";
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        input.setImage(fileName);

        blogService.update(id,input);
        String uploadDirectory = "src/main/resources/static/images/product/";
        uploadService.save(file, uploadDirectory);
        model.addAttribute("message","Update thành công ^-^");

        return "forward:/admin/blog";
    }

    @RequestMapping("listblog/page/{pageNum}")
    public String listPage(Model model,
                           @PathVariable("pageNum") int pageNum,
                           @RequestParam(name="keyword", required = false) String keyword,
                           @RequestParam(name="sortField", required = false) String sortField,
                           @RequestParam(name = "sortDir", required = false) String sortDir,
                           @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
        pageable = PageRequest.of(pageNum-1, 3, sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
        BlogFilter filter = BlogFilter.builder()
                .keyword(keyword)
                .build();
        Page<BlogDto> listBlog = blogService.findPaging(filter, pageable);
        model.addAttribute("keyword", filter.getKeyword());
        model.addAttribute("listBlog", listBlog);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", listBlog.getTotalPages());
        model.addAttribute("totalItems", listBlog.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        return "admin/blog/ListBlog";
    }

}
