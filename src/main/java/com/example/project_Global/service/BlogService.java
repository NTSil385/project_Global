package com.example.project_Global.service;

import com.example.project_Global.model.Blog;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogService {
    List<Blog> getAll();
    void saveBlog(Blog blog);
    Blog getBlogByID(Integer id);

    void deleteById(Integer id);

    void update(Blog newBlog);

    Page<Blog> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
