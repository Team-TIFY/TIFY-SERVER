package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HCCUPRecommendationStrategy implements ProductRecommendationStrategy {

    private static final String CATEGORY_NAME = "HCCUP";

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    @Override
    public List<Product> recommendation(
            Long userId, String categoryName, List<FavorRecommendationDTO> dto) {

        List<FavorRecommendationDTO> recommendationDTO = getRecommendationDTO(userId);
        List<Product> productList = new ArrayList<>();

        /**
         * 1번 스텝(자주 사용하는 컵 종류?) 객관식 중복이긴 한데 컵 종류가 겹치진 않음. productList에 모두 때려박아도 될 것 같다. 다만 답변의 개수에
         * 따른 경우는 분할해야 할듯.
         */
        if (recommendationDTO.get(0).getAnswer().split(", ").length > 1) {
            productList.addAll(
                    filterStep(CATEGORY_NAME, recommendationDTO.get(0).getAnswer().split(", ")[0]));
            productList.addAll(
                    filterStep(CATEGORY_NAME, recommendationDTO.get(0).getAnswer().split(", ")[1]));
        } else {
            productList.addAll(filterStep(CATEGORY_NAME, recommendationDTO.get(0).getAnswer()));
        }

        /** 2번 스텝(컵에서 중요하게 여기는 것?) 객관식 중복이므로 답변의 개수에 따른 경우 분할 */
        List<Product> filteredProductList = new ArrayList<>();
        String[] splitAnswer = recommendationDTO.get(1).getAnswer().split(", ");
        if (splitAnswer.length > 1) {
            filteredProductList =
                    productList.stream()
                            .filter(product -> product.getCharacteristic().contains(splitAnswer[0]))
                            .filter(product -> product.getCharacteristic().contains(splitAnswer[1]))
                            .toList();
        } else {
            filteredProductList =
                    productList.stream()
                            .filter(
                                    product ->
                                            product.getCharacteristic()
                                                    .contains(recommendationDTO.get(1).getAnswer()))
                            .toList();
        }

        /**
         * 3번 스텝(좋아하는 컵의 디자인?) 객관식 중복이므로 답변의 개수에 따른 경우 분할. 만약 recommendationDTO의 두번째 질문에서 디자인이 있다면
         * 생략
         */
        if (isContainsDesign(recommendationDTO)) {
            return filteredProductList;
        } else {
            if (recommendationDTO.get(2).getAnswer().split(", ").length > 1) {
                return filteredProductList.stream()
                        .filter(
                                product ->
                                        product.getCharacteristic()
                                                .contains(
                                                        recommendationDTO
                                                                .get(2)
                                                                .getAnswer()
                                                                .split(", ")[0]))
                        .filter(
                                product ->
                                        product.getCharacteristic()
                                                .contains(
                                                        recommendationDTO
                                                                .get(2)
                                                                .getAnswer()
                                                                .split(", ")[1]))
                        .toList();
            } else {
                return filteredProductList.stream()
                        .filter(
                                product ->
                                        product.getCharacteristic()
                                                .contains(recommendationDTO.get(2).getAnswer()))
                        .toList();
            }
        }
    }

    private List<Product> filterStep(String categoryName, String answer) {
        return productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, answer);
    }

    private List<FavorRecommendationDTO> getRecommendationDTO(Long userId) {
        List<FavorAnswer> favorAnswers = new ArrayList<>();
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 1L));
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 3L));
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 2L));
        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    }

    private boolean isContainsDesign(List<FavorRecommendationDTO> dtos) {
        return dtos.get(1).getAnswer().contains("디자인");
    }
}
