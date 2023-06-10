package com.example.project_Global.service;

import com.example.project_Global.model.CartItem;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShoppingCartServiceImp implements ShoppingCartService {
    Map<Integer, CartItem> shoppingCart=new HashMap<>();
    @Override
    public void addCart(CartItem newItem) {
        CartItem cartItem=shoppingCart.get(newItem.getProductId());
        if(cartItem == null)
        {
            shoppingCart.put(newItem.getProductId(),newItem);
        }else {
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }
    }

    @Override
    public void removeCart(int productId) {
        shoppingCart.remove(productId);
    }

    @Override
    public CartItem updateCart(int productId, int quantity){
        CartItem cartItem=shoppingCart.get(productId);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    @Override
    public void clear() {
        shoppingCart.clear();
    }

    @Override
    public double getAmount() {
        return shoppingCart.values().stream().mapToDouble(item -> item.getQuantity()*item.getPrice()).sum();
    }

    @Override
    public int getCount() {
        return shoppingCart.values().size();
    }

    @Override
    public Collection<CartItem> getAllItems() {
        return shoppingCart.values();
    }
}
