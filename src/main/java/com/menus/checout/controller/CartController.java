package com.menus.checout.controller;

import com.menus.checout.dto.AddCartItemRequest;
import com.menus.checout.dto.CartDto;
import com.menus.checout.dto.Response;
import com.menus.checout.service.CartService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/addItem")
    public Response<CartDto> addCartItem(@RequestParam @NotNull Long customerId,
        @Valid @RequestBody AddCartItemRequest request) {
        return Response.of(cartService.addCartItem(request, customerId));
    }

}
