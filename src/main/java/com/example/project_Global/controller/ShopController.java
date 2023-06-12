package com.example.project_Global.controller;

import com.example.project_Global.model.Product;
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
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String showshop(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return findPaginated(1, "name", "asc", model);

    }

    @GetMapping("/{id}")
    // Let's return an object with: data, message, status
    public String findById(@PathVariable("id") int id, Model model){
        Product found = productService.getProductById(id);
        if(found != null){
            model.addAttribute("productById",found);
            return "view/details";
        }else{
            return "not-found";
        }
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir, Model model) {
        int pageSize = 9;
        String local = "/shop/page/";
        Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir, local);
        List<Product> listProduct = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("local", local);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("products", listProduct);
        return "View/shop";
    }
}
