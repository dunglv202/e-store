package com.example.shopdemo.rest;

import com.example.shopdemo.entity.CartItem;
import com.example.shopdemo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.shopdemo.util.AuthenticationUtils.getUser;

@RestController
@RequestMapping("/api/v1/carts")
public class CartRestController {
    private CartService cartService;

    @Autowired
    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("")
    public List<CartItem> getAllItems(@RequestParam Integer page,
                                      @RequestParam Integer size,
                                      Authentication auth) {
        return cartService.getAllItems(getUser(auth), PageRequest.of(page, size)).toList();
    }

    @GetMapping("/{itemId}")
    public CartItem getItem(@PathVariable Integer itemId,
                            Authentication auth) {
        return cartService.getItem(itemId, getUser(auth));
    }

    @PostMapping("")
    public CartItem addItem(@RequestBody CartItem item,
                            Authentication auth) {
        return cartService.addItem(item, getUser(auth));
    }

    @PutMapping("/{itemId}")
    public CartItem updateItem(@RequestBody CartItem item,
                               @PathVariable Integer itemId,
                               Authentication auth) {
        item.setId(itemId);
        return cartService.updateItem(item, getUser(auth));
    }

    @DeleteMapping("/{itemId}")
    public CartItem deleteItem(@PathVariable Integer itemId,
                               Authentication auth) {
        return cartService.deleteItem(itemId, getUser(auth));
    }
}
