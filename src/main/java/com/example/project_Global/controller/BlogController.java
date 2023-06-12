package com.example.project_Global.controller;

import com.example.project_Global.model.Blog;
import com.example.project_Global.service.BlogService;
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

@Controller
@RequestMapping("/admin/blog")
public class BlogController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images";
    @Autowired
    private BlogService blogService;
/*
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("listBlog",blogService.getAll() );
        return "blogs/blog_index";
    }
*/
@GetMapping ("")
public String index(Model model) {
    model.addAttribute("listBlog", blogService.getAll());
    return findPaginated(1, "id", "asc", model);

}


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Blog newBlog = new Blog();
        model.addAttribute("blog", newBlog);
        return "blogs/create";
    }
    @PostMapping("/create")
    public String create(@Valid Blog newBlog, @RequestParam("imageBlog") MultipartFile imageBlog, BindingResult result, Model model) throws IOException {
        if(result.hasErrors()){
            model.addAttribute("blog", newBlog);
            return "blogs/create";
        }
        if(imageBlog !=null && imageBlog.getSize()>0){
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, imageBlog.getOriginalFilename());
            fileNames.append(imageBlog.getOriginalFilename());
            Files.write(fileNameAndPath, imageBlog.getBytes());
            newBlog.setImage(fileNames.toString());
        }
        blogService.saveBlog(newBlog);
        return "redirect:/admin/blog";
    }
/*
    @PostMapping("/save")
    public String save(@ModelAttribute("blog") Blog blog) {
        blogService.saveBlog(blog);
        return "redirect:/admin/blog";
    }
*/

    @GetMapping("del/{id}")
    public String removeItem(@PathVariable("id")Integer id) {
        blogService.deleteById(id);
        return "redirect:/admin/blog";
    }

    /*@GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Blog editBlog = blogService.getBlogByID(id);
        if (editBlog != null) {
            model.addAttribute("blog", editBlog);
            return "blogs/edit";
        } else {
            return "not-found";
        }
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("blog") Blog updatedBlog,
                       @RequestParam("imageBlog") MultipartFile imageBlog,
                       BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "blogs/edit";
        }

        if (imageBlog != null && imageBlog.getSize() > 0) {
            String fileName = imageBlog.getOriginalFilename();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fileName);
            Files.write(fileNameAndPath, imageBlog.getBytes());
            updatedBlog.setImage(fileName);
        }
        for(int i = 0; i<blogService.getAll().size();i++){
            Blog blog = blogService.getAll().get(i);
            if(blog.getId() == updatedBlog.getId()){
                blogService.getAll().set(i,updatedBlog);
                blogService.saveBlog(updatedBlog);
                break;
            }
        }

        blogService.saveBlog(updatedBlog);
        return "redirect:/admin/blog";
    }*/

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Blog editBlog = blogService.getBlogByID(id);
        if(editBlog != null){
            model.addAttribute("blog", editBlog);
            model.addAttribute("listBlog",blogService.getAll());
            return "blogs/edit";
        }else {
            return "not-found";
        }
    }
    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("blog") Blog updateBlog
            ,@RequestParam("imageBlog") MultipartFile imageBlog, BindingResult bindingResult, Model model)throws IOException{
        if(bindingResult.hasErrors()){
            return "blogs/edit";
        }
        if(imageBlog != null && imageBlog.getSize() > 0) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, imageBlog.getOriginalFilename());
            fileNames.append(imageBlog.getOriginalFilename());
            Files.write(fileNameAndPath, imageBlog.getBytes());
            updateBlog.setImage(fileNames.toString());
        }
        for(int i = 0; i<blogService.getAll().size();i++){
            Blog blog = blogService.getAll().get(i);
            if(blog.getId() == updateBlog.getId()){
                blogService.getAll().set(i,updateBlog);
                blogService.update(updateBlog);
                break;
            }
        }
        blogService.update(updateBlog);
        return "redirect:/admin/blog";
    }
    @GetMapping("/detail/{id}")
    public String detailForm(@PathVariable ("id") Integer id, Model model){
        Blog blog= blogService.getBlogByID(id);
        if(blog==null){
            return "not-found";
        }else {
            model.addAttribute("blog",blog);
            return "blogs/detail";
        }
    }
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 4;
        Page<Blog> page = blogService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Blog> listBlog = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listBlog", listBlog);
        return "blogs/blog_index";
    }

}
