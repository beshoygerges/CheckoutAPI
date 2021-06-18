package com.menus.checkout.controller;

import com.menus.checkout.dto.CheckoutRequest;
import com.menus.checkout.dto.OrderDto;
import com.menus.checkout.dto.Response;
import com.menus.checkout.model.Order;
import com.menus.checkout.service.CheckoutService;
import com.menus.checkout.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/placeOrder")
    public Response<OrderDto> addOrder(@RequestParam @NotNull Long customerId, @RequestBody
    @Valid CheckoutRequest request) throws Exception {
        Order order = checkoutService.checkout(customerId, request);
        return Response.of(ModelMapperUtil.map(order, OrderDto.class));
    }

}
