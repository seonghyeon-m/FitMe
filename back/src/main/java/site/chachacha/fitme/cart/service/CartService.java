package site.chachacha.fitme.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.cart.dto.CartOptionRequest;
import site.chachacha.fitme.cart.dto.CartRequest;
import site.chachacha.fitme.cart.entity.Cart;
import site.chachacha.fitme.cart.exception.DuplicateCartException;
import site.chachacha.fitme.cart.repository.CartRepository;
import site.chachacha.fitme.member.entity.Member;
import site.chachacha.fitme.member.exception.NoSuchMemberException;
import site.chachacha.fitme.member.repository.MemberRepository;
import site.chachacha.fitme.product.entity.Product;
import site.chachacha.fitme.product.entity.ProductOption;
import site.chachacha.fitme.product.exception.ProductNotFoundException;
import site.chachacha.fitme.product.exception.ProductOptionNotFoundException;
import site.chachacha.fitme.product.repository.ProductOptionRepository;
import site.chachacha.fitme.product.repository.ProductRepository;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    @Transactional
    public void createCartProduct(CartRequest request, Long productId, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchMemberException::new);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        request.getOptions().forEach(option -> createSingleCartProduct(member, product, option));
    }

    private void createSingleCartProduct(Member member, Product product, CartOptionRequest option) {
        ProductOption productOption = productOptionRepository.findById(option.getProductOptionId())
            .orElseThrow(() -> new ProductOptionNotFoundException(option.getProductOptionId()));

        validateDuplicateCart(member, product, productOption);

        Cart cart = Cart.builder()
            .product(product)
            .member(member)
            .productOption(productOption)
            .quantity(option.getQuantity())
            .build();

        cartRepository.save(cart);
    }

    private void validateDuplicateCart(Member member, Product product, ProductOption productOption) {
        if (cartRepository.existsByMemberAndProductAndProductOption(member, product, productOption)) {
            throw new DuplicateCartException(productOption.getId());
        }
    }
}
