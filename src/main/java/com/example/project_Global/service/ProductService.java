package com.example.project_Global.service;

import com.example.project_Global.model.Category;
import com.example.project_Global.model.Product;
import com.example.project_Global.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    List<Product> getAllProducts();

    void saveProduct(Product product);
    Product getProductById(int id);
    void update(Product product);
    void deleteProductById(int id);
    Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String local);
}
