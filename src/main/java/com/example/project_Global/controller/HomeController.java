package com.example.project_Global.controller;

import com.example.project_Global.model.Blog;
import com.example.project_Global.model.Product;
import com.example.project_Global.service.BlogService;
import com.example.project_Global.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping ("")
public class HomeController {

    @Autowired
    private BlogService blogService;
    @GetMapping("/")
    public String showhome(Model model) {
        model.addAttribute("index", blogService.getAll());
        return "View/index";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("listBlog", blogService.getAll());
        return findPaginatedBlogPage(1, "id", "asc", model);
    }


    @GetMapping("/blog/page/{pageNo}")
    public String findPaginatedBlogPage(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 6;
        Page<Blog> page = blogService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Blog> listBlog = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listBlog", listBlog);
        return "View/blog";
    }


}
