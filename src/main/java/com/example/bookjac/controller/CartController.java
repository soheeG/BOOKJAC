package com.example.bookjac.controller;

import com.example.bookjac.domain.BookResult;
import com.example.bookjac.domain.Cart;
import com.example.bookjac.service.CartService;
import com.example.bookjac.service.NaverBookAPIService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    public CartService cartService;

    private final NaverBookAPIService naverBookAPIService;

    @Autowired
    public CartController(NaverBookAPIService naverBookAPIService) {
        this.naverBookAPIService = naverBookAPIService;
    }

    @GetMapping("cart/{id}")
    @PreAuthorize("isAuthenticated()")
    public String cartPageGet(@PathVariable("id") String memberId,
                                  Model model,
                                  Authentication auth){

        /* 현재 사용자의 이름(username) 가져오기 */
        String username = auth.getName();

        List<Cart> cart = cartService.getCartList(memberId, username);

        model.addAttribute("cartInfo", cart);
        return "cart";
    }

   /* @PostMapping("/cart/add")
    @ResponseBody
    public String addCartPOST(Cart cart, Authentication auth){
        cart.setMemberId(auth.getName());
        // 발주 품목 추가
        int result = cartService.addCart(cart);

        return result + "";
    }*/
   @PostMapping("/cart/add")
   @ResponseBody
   public String addCart(@RequestBody Cart cart, Authentication auth) {
       int result = naverBookAPIService.addCart(cart, auth);
       return String.valueOf(result);
   }

}



