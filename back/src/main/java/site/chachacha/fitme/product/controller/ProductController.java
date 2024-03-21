package site.chachacha.fitme.product.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.annotation.MemberId;
import site.chachacha.fitme.product.dto.ProductDetailResponse;
import site.chachacha.fitme.product.dto.ProductOptionResponse;
import site.chachacha.fitme.product.dto.ProductResponse;
import site.chachacha.fitme.product.dto.ProductSearchRequest;
import site.chachacha.fitme.product.service.ProductService;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@ModelAttribute @Validated ProductSearchRequest request) {
        List<ProductResponse> responses = productService.getProducts(request);
        return ResponseEntity.ok(responses);
    }

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable(name = "productId") Long productId, @MemberId Long memberId) {
        ProductDetailResponse response = productService.getProduct(productId, memberId);
        return ResponseEntity.ok(response);
    }

    // 상품 옵션 목록 조회
    @GetMapping("/{productId}/options")
    public ResponseEntity<List<ProductOptionResponse>> getProductOptions(@PathVariable(name = "productId") Long productId) {
        List<ProductOptionResponse> responses = productService.getProductOptions(productId);
        return ResponseEntity.ok(responses);
    }
}
