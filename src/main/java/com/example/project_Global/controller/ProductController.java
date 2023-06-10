package com.example.project_Global.controller;

import com.example.project_Global.model.Category;
import com.example.project_Global.model.Product;
import com.example.project_Global.repositories.CategoryRepository;
import com.example.project_Global.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ProductController {
    private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/img";
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return findPaginated(1, "name", "asc", model);
    }


    @GetMapping("/{id}")
    // Let's return an object with: data, message, status
    public String findById(@PathVariable("id") int id, Model model){
        Product found = productService.getProductById(id);
        if(found != null){
            model.addAttribute("productById",found);
            return "Admins/details";
        }else{
            return "not-found";
        }
    }


    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("cate", categoryRepository.findAll());
        return "Admins/create";
    }

    @PostMapping("/create")
    public String create(@Valid Product newProduct, @RequestParam("imageProduct") MultipartFile imageProduct, @RequestParam("categoryId") int categoryId, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            model.addAttribute("cate", categoryRepository.findAll());
            return "Admins/create";
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            newProduct.setCategory(category.get());
        }

        if (imageProduct != null && imageProduct.getSize() > 0) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, imageProduct.getOriginalFilename());
            fileNames.append(imageProduct.getOriginalFilename());
            Files.write(fileNameAndPath, imageProduct.getBytes());
            newProduct.setImage(fileNames.toString());
        }

        productService.saveProduct(newProduct);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Product editProduct = null;
        for(Product product : productService.getAllProducts()){
            if(product.getId() == id){
                editProduct = product;
            }
        }
        if(editProduct != null){
            model.addAttribute("product",editProduct);
            return "Admins/edit";
        }else{
            return "not-found";
        }
    }

    @PostMapping ("/edit")
    public String edit(@Valid @ModelAttribute("product") Product updatedProduct, @ModelAttribute("cate") Category updatedCategory
            ,@RequestParam("imageProduct") MultipartFile imageProduct, BindingResult bindingResult, Model model)throws IOException{
        if(bindingResult.hasErrors()){
            return "Admins/edit";
        }
        if(imageProduct != null && imageProduct.getSize() > 0) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, imageProduct.getOriginalFilename());
            fileNames.append(imageProduct.getOriginalFilename());
            Files.write(fileNameAndPath, imageProduct.getBytes());
            updatedProduct.setImage(fileNames.toString());
        }
        for(int i = 0; i<productService.getAllProducts().size();i++){
            Product product = productService.getAllProducts().get(i);
            if(product.getId() == updatedProduct.getId()){
                productService.getAllProducts().set(i,updatedProduct);
                productService.saveProduct(updatedProduct);
                break;
            }
        }
        return "redirect:/admin";
    }



    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir, Model model) {
        int pageSize = 9;
        String local = "/admin/page/";
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
        return "Admins/index";
    }
}
