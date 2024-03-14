package site.chachacha.fitme.product.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.product.dto.ProductResponse;
import site.chachacha.fitme.product.dto.ProductSearchRequest;
import site.chachacha.fitme.product.entity.Product;
import site.chachacha.fitme.product.repository.ProductCustomRepository;
import site.chachacha.fitme.product.repository.ProductRepository;
import site.chachacha.fitme.review.entity.ProductReview;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCustomRepository productCustomRepository;

    //TODO: sortBy 조건 고려해서 바꿔야함, 현재는 최신 등록순으로 상품 가져옴
    public List<ProductResponse> getProducts(ProductSearchRequest request) {

        List<Product> products = productCustomRepository.findAllByProductConditions(
            request.getLastId(), request.getSize(), request.getGender(), request.getAgeRange(), request.getBrandIds(), request.getCategoryIds(),
            request.getStartPrice(), request.getEndPrice(), request.getSortBy());

        return products
            .stream()
            .map(product -> {
                Integer reviewCount = product.getProductReviews().size();
                Double reviewRating = calculateReviewRating(product.getProductReviews());
                return ProductResponse.of(product, reviewRating, reviewCount);
            })
            .collect(Collectors.toList());
    }

    private double calculateReviewRating(List<ProductReview> productReviews) {
        if (productReviews.isEmpty()) {
            return 0.0;
        }

        double averageScore = productReviews.stream()
            .mapToInt(ProductReview::getScore)
            .average()
            .orElse(0.0);

        return Math.round(averageScore * 10.0) / 10.0; // 평균 점수를 반올림하여 반환
    }
}
