package com.menus.checkout.controller;

import com.menus.checkout.dto.AddCartItemRequest;
import com.menus.checkout.dto.CartDto;
import com.menus.checkout.dto.Response;
import com.menus.checkout.model.Cart;
import com.menus.checkout.service.CartService;
import com.menus.checkout.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/addItem")
    public Response<CartDto> addCartItem(@RequestParam @NotNull Long customerId,
                                         @Valid @RequestBody AddCartItemRequest request) {
        Cart cart = cartService.addCartItem(request, customerId);
        return Response.of(ModelMapperUtil.map(cart, CartDto.class));
    }

}
