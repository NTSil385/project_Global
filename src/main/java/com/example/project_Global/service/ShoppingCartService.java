package com.example.project_Global.service;

import com.example.project_Global.model.CartItem;

import java.util.Collection;

public interface ShoppingCartService {
    void addCart(CartItem newItem);
    CartItem updateCart(int productId, int quantity);
    void removeCart(int productId);
    void clear();
    double getAmount();

    int getCount();

    Collection<CartItem> getAllItems();
}
