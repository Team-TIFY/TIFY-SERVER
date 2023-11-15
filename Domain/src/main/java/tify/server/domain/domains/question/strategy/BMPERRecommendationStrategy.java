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

        List<FavorRecommendationDTO> recommendationDTO = getRecommendationDTO(userId);
        List<Product> productList = new ArrayList<>();

        /**
         * 1번 스텝(좋아하는 향기의 강도?) favorRecommendationDTO의 첫번째 질문의 답변을 기반으로 한 퍼퓸, 오드퍼퓸....으로 상품 검색하여
         * productList에 addAll
         */
        productList.addAll(filterStep(CATEGORY_NAME, recommendationDTO.get(0).getAnswer()));

        /** 2번 스텝(내가 원하는 나의 이미지?) 객관식 2개까지 가능이기 때문에 답변 개수별로 경우를 나눔 */
        String[] splitAnswer = recommendationDTO.get(1).getAnswer().split(", ");
        if (splitAnswer.length > 1) {
            return productList.stream()
                    .filter(product -> product.getCharacteristic().contains(splitAnswer[0]))
                    .filter(product -> product.getCharacteristic().contains(splitAnswer[1]))
                    .toList();
        } else {
            return productList.stream()
                    .filter(
                            product ->
                                    product.getCharacteristic()
                                            .contains(recommendationDTO.get(1).getAnswer()))
                    .toList();
        }
    }

    private List<Product> filterStep(String categoryName, String answer) {
        return productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, answer);
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
