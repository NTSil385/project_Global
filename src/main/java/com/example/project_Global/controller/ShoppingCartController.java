package com.example.project_Global.controller;

import com.example.project_Global.model.CartItem;
import com.example.project_Global.model.Product;
import com.example.project_Global.service.ProductService;
import com.example.project_Global.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("shoppingcart")
public class ShoppingCartController {
    @Autowired
    ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/showcart")
    public String viewCart(Model model){
        model.addAttribute("all_items", shoppingCartService.getAllItems());
        model.addAttribute("total_amout", shoppingCartService.getAmount());
        return "View/cart";
    }

    @GetMapping("add/{id}")
    public String addItem(@PathVariable("id")Integer id) {
        Product product=productService.getProductById(id);
        if(product!=null) {
            CartItem item=new CartItem();
            item.setProductId(product.getId());
            item.setName(product.getName());
            item.setPrice(product.getPrice());
            item.setQuantity(1);
            shoppingCartService.addCart(item);
        }
        return "redirect:/shoppingcart/showcart";
    }

    @GetMapping("clear")
    public String clearCart() {
        shoppingCartService.clear();
        return "redirect:/shoppingcart/showcart";
    }

    @GetMapping("remove/{id}")
    public String removeItem(@PathVariable("id")Integer id) {
        shoppingCartService.removeCart(id);
        return "redirect:/shoppingcart/showcart";
    }

    @PostMapping("update")
    public String updateItem(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
        shoppingCartService.updateCart(productId, quantity);
        return "redirect:/shoppingcart/showcart";
    }

}
