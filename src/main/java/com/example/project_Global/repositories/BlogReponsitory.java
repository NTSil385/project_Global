package com.example.project_Global.repositories;

import com.example.project_Global.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogReponsitory extends JpaRepository<Blog, Integer> {
}
