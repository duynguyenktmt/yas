package com.yas.promotion.viewmodel;

import com.yas.promotion.model.Promotion;
import com.yas.promotion.model.PromotionApply;
import com.yas.promotion.validation.PromotionConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@PromotionConstraint
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionPostVm extends PromotionDto {
    @Size(min = 1, max = 450)
    private String name;
    @NotBlank
    private String slug;
    private String description;
    @NotBlank
    private String couponCode;
    Long minimumOrderPurchaseAmount;
    boolean isActive;
    @NotNull ZonedDateTime startDate;
    @NotNull ZonedDateTime endDate;

    public static List<PromotionApply> createPromotionApplies(PromotionPostVm promotionPostVm, Promotion promotion) {
        return switch (promotion.getApplyTo()) {
            case PRODUCT -> promotionPostVm.getProductIds().stream()
                    .map(productId -> PromotionApply.builder().productId(productId).promotion(promotion)
                            .build())
                    .toList();

            case BRAND -> promotionPostVm.getBrandIds().stream()
                    .map(brandId -> PromotionApply.builder().brandId(brandId).promotion(promotion)
                            .build())
                    .toList();

            case CATEGORY -> promotionPostVm.getCategoryIds().stream()
                    .map(categoryId -> PromotionApply.builder().categoryId(categoryId).promotion(promotion)
                            .build())
                    .toList();

        };
    }
}
