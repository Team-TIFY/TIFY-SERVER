package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

@RequiredArgsConstructor
public class BMPERRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    private static final String CATEGORY_NAME = "BMPER";

    @Override
    public List<Product> recommendation(
            Long userId, String categoryName, List<FavorRecommendationDTO> dto) {
        return null;
    }

    private List<FavorRecommendationDTO> getRecommendationDTO(Long userId) {
        List<FavorRecommendationDTO> favorRecommendationDTOs = new ArrayList<>();
        String firstAnswer =
                favorAnswerAdaptor
                        .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 1L)
                        .getAnswerContent();
        if (firstAnswer.equals("진한") || firstAnswer.equals("깊은")) {
            favorRecommendationDTOs.add(new FavorRecommendationDTO(1L, "퍼퓸, 오드퍼퓸"));
        } else if (firstAnswer.equals("은은한") || firstAnswer.equals("가벼운")) {
            favorRecommendationDTOs.add(new FavorRecommendationDTO(1L, "오드뚜왈렛, 오드코롱, 샤워코롱"));
        }

        favorRecommendationDTOs.add(
                FavorRecommendationDTO.from(
                        favorAnswerAdaptor.searchByCategoryNameAndNumber(
                                userId, CATEGORY_NAME, 4L)));

        return favorRecommendationDTOs;
    }
}
