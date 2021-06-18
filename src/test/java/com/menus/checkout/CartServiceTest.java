package com.menus.checkout;

import com.menus.checkout.dto.AddCartItemRequest;
import com.menus.checkout.exception.CartNotFoundException;
import com.menus.checkout.exception.InsufficientProductQuantityException;
import com.menus.checkout.exception.ProductNotAvailableException;
import com.menus.checkout.exception.ProductNotFoundException;
import com.menus.checkout.model.Cart;
import com.menus.checkout.model.CartItem;
import com.menus.checkout.model.CartItemPk;
import com.menus.checkout.model.Product;
import com.menus.checkout.repository.CartRepository;
import com.menus.checkout.repository.ProductRepository;
import com.menus.checkout.service.impl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    public void AddItemToEmptyCartShouldPass() {
        Cart cart = new Cart();
        cart.setId(1L);

        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setQuantity(1);

        Long customerId = 1L;

        Product product = new Product();
        product.setId(request.getProductId());
        product.setQuantity(1);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(1000));

        given(cartRepository.findByCustomer_Id(customerId)).willReturn(Optional.of(cart));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));
        given(cartRepository.save(cart)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Cart savedCart = cartService.addCartItem(request, customerId);

        assertThat(savedCart).isNotNull();
        assertThat(savedCart.getCartItems().size()).isEqualTo(1);
        assertThat(savedCart.getSubTotal()).isEqualTo(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
    }

    @Test
    public void AddItemToCartWithItemsShouldPass() {
        Cart cart = new Cart();
        cart.setId(1L);

        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setQuantity(1);

        Long customerId = 1L;

        Product product = new Product();
        product.setId(request.getProductId());
        product.setQuantity(2);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(1000));

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cartItem.setPk(new CartItemPk(cart, product));

        cart.getCartItems().add(cartItem);

        given(cartRepository.findByCustomer_Id(customerId)).willReturn(Optional.of(cart));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));
        given(cartRepository.save(cart)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Cart savedCart = cartService.addCartItem(request, customerId);

        assertThat(savedCart).isNotNull();

        assertThat(savedCart.getCartItems().size()).isEqualTo(1);

        assertThat(savedCart.getSubTotal()).isEqualTo(savedCart.getCartItems()
                .stream()
                .map(cartItem1 -> cartItem1.getUnitPrice().multiply(BigDecimal.valueOf(cartItem1.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @Test
    public void AddItemToNonExistCartShouldThrow() {

        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setQuantity(1);

        Long customerId = 1L;

        Product product = new Product();
        product.setId(request.getProductId());
        product.setQuantity(1);
        product.setAvailable(false);
        product.setPrice(BigDecimal.valueOf(1000));

        given(cartRepository.findByCustomer_Id(customerId)).willReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.addCartItem(request, customerId));

        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    public void AddNonExistItemToCartShouldThrow() {
        Cart cart = new Cart();
        cart.setId(1L);

        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setQuantity(1);

        Long customerId = 1L;

        given(cartRepository.findByCustomer_Id(customerId)).willReturn(Optional.of(cart));

        given(productRepository.findById(request.getProductId())).willReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> cartService.addCartItem(request, customerId));

        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    public void AddNotAvailableItemToCartShouldThrow() {
        Cart cart = new Cart();
        cart.setId(1L);

        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setQuantity(1);

        Long customerId = 1L;

        Product product = new Product();
        product.setId(request.getProductId());
        product.setQuantity(1);
        product.setAvailable(false);
        product.setPrice(BigDecimal.valueOf(1000));

        given(cartRepository.findByCustomer_Id(customerId)).willReturn(Optional.of(cart));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));

        assertThrows(ProductNotAvailableException.class, () -> cartService.addCartItem(request, customerId));

        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    public void AddQuantityExceededItemToCartShouldThrow() {
        Cart cart = new Cart();
        cart.setId(1L);

        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(1L);
        request.setQuantity(1);

        Long customerId = 1L;

        Product product = new Product();
        product.setId(request.getProductId());
        product.setQuantity(0);
        product.setAvailable(true);
        product.setPrice(BigDecimal.valueOf(1000));

        given(cartRepository.findByCustomer_Id(customerId)).willReturn(Optional.of(cart));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));

        assertThrows(InsufficientProductQuantityException.class, () -> cartService.addCartItem(request, customerId));

        verify(cartRepository, never()).save(any(Cart.class));
    }
}
