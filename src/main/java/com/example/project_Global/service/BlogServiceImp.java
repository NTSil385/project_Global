package com.example.project_Global.service;

import com.example.project_Global.model.Blog;
import com.example.project_Global.reponsitory.BlogReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImp implements BlogService{
    @Autowired
    private BlogReponsitory blogReponsitory;
    @Override
    public List<Blog> getAll(){
        return blogReponsitory.findAll();
    }
    @Override
    public void saveBlog(Blog blog){
        this.blogReponsitory.save(blog);
    }
    @Override
    public Blog getBlogByID(Integer id) {
        Optional<Blog> optional=blogReponsitory.findById(id);
        Blog blog=null;
        if(optional.isPresent()) {
            blog=optional.get();
        }else {
            throw new RuntimeException("Product not found for id::"+id);
        }
        return blog;
    }
    @Override
    public void deleteById(Integer id){
        this.blogReponsitory.deleteById(id);
    }

    @Override
    public void update(Blog newBlog) {
        this.blogReponsitory.save(newBlog);
    }

/*    @Override
    public Page<Blog> findPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize);
        return this.blogReponsitory.findAll(pageable);
    }*/
    @Override
    public Page<Blog> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.blogReponsitory.findAll(pageable);
    }
}
