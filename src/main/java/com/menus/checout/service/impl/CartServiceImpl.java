package com.menus.checout.service.impl;

import com.menus.checout.aop.LogElapsedTime;
import com.menus.checout.dto.AddCartItemRequest;
import com.menus.checout.dto.CartDto;
import com.menus.checout.model.Cart;
import com.menus.checout.model.CartItem;
import com.menus.checout.model.Product;
import com.menus.checout.repository.CartRepository;
import com.menus.checout.repository.ProductRepository;
import com.menus.checout.service.CartService;
import com.menus.checout.util.ModelMapperUtil;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @LogElapsedTime
    @Transactional
    @Override
    public CartDto addCartItem(final AddCartItemRequest request, final Long customerId) {
        final Cart cart = getCart(customerId);

        final Product product = getProduct(request.getProductId());

        isProductAvailable(product);

        CartItem cartItem = createCartItem(cart, product, request.getQuantity());

        isProductQuantityExist(product, cartItem.getQuantity());

        calculateCartSubtotal(cart);

        return ModelMapperUtil.map(cart, CartDto.class);
    }

    private void calculateCartSubtotal(Cart cart) {
        cart.setSubTotal(cart.getCartItems()
            .stream()
            .map(cartItem -> cartItem.getProduct().getPrice().multiply(
                BigDecimal.valueOf(cartItem.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private CartItem createCartItem(final Cart cart, final Product product,
        final Integer quantity) {
        final Optional<CartItem> optional = cart.getCartItems().stream()
            .filter(item -> Objects.equals(item.getProduct().getId(), product.getId()))
            .findAny();

        CartItem cartItem;

        if (optional.isPresent()) {
            cartItem = optional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(cartItem.getUnitPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        } else {
            cartItem = new CartItem(cart, product, quantity);
            cart.addCartItem(cartItem);
        }

        return cartItem;
    }

    private void isProductQuantityExist(Product product, Integer quantity) {
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient Product quantity");
        }
    }

    private void isProductAvailable(Product product) {
        if (!product.isAvailable()) {
            throw new RuntimeException("Product is not available");
        }
    }

    private Product getProduct(Long productId) {
        Optional<Product> optional = productRepository.findById(productId);
        return optional.orElseThrow(() -> new RuntimeException("Product is not exist"));
    }

    @Override
    public Cart getCart(final Long customerId) {
        Optional<Cart> optional = cartRepository.findByCustomer_Id(customerId);
        return optional.orElseThrow(() -> new RuntimeException("Cart is not exist"));
    }

    @Override
    public void removeCartItems(Cart cart) {
        cart.setSubTotal(BigDecimal.ZERO);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

}
