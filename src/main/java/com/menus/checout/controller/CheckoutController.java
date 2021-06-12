package com.menus.checout.controller;

import com.menus.checout.dto.CheckoutRequest;
import com.menus.checout.dto.OrderDto;
import com.menus.checout.dto.Response;
import com.menus.checout.service.OrderService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final OrderService orderService;

    @PostMapping("/placeOrder")
    public Response<OrderDto> addOrder(@RequestParam @NotNull Long customerId, @RequestBody
    @Valid CheckoutRequest request) throws Exception {
        return Response.of(orderService.addOrder(customerId, request));
    }

}
