package site.chachacha.fitme.review.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.entity.BaseEntity;
import site.chachacha.fitme.member.entity.Member;
import site.chachacha.fitme.product.entity.Product;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReview extends BaseEntity {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Integer score;

    private String content;

    private String imageUrl;

    public ProductReview(Product product, Member member, Integer score, String content, String imageUrl) {
        this.product = product;
        this.member = member;
        this.score = score;
        this.content = content;
        this.imageUrl = imageUrl;
        this.product.getProductReviews().add(this);
    }
}
