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
public class HCDISRecommendationStrategy implements ProductRecommendationStrategy {

    private static final String CATEGORY_NAME = "HCDIS";

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    @Override
    public List<Product> recommendation(
            Long userId, String categoryName, List<FavorRecommendationDTO> dto) {

        List<FavorRecommendationDTO> recommendationDTO = getRecommendationDTO(userId);

        /** 1번 스텝(선호하는 식기 종류?) 객관식 단일이므로 그냥 가져오기 */
        List<Product> productList =
                new ArrayList<>(filterStep(CATEGORY_NAME, recommendationDTO.get(0).getAnswer()));

        /**
         * 2번 스텝(좋아하는 식기 디자인?) 객관식 max 2문항이므로 2가지 경우(답이 1개, 2개인 경우)로 나눔 답이 1개인경우, filterStep을 그냥 적용
         * 2개인 경우 첫 답변으로 우선 상품들을 갖고오고 두번째 답변으로 갖고온 상품 리스트를 첫 답변으로 갖고온 상품에 없는 것들만 갖고오도록 필터
         */
        if (recommendationDTO.get(1).getAnswer().split(", ").length > 1) {
            return productList.stream()
                    .filter(
                            product ->
                                    product.getCharacteristic()
                                            .contains(
                                                    recommendationDTO
                                                            .get(1)
                                                            .getAnswer()
                                                            .split(", ")[0]))
                    .filter(
                            product ->
                                    product.getCharacteristic()
                                            .contains(
                                                    recommendationDTO
                                                            .get(1)
                                                            .getAnswer()
                                                            .split(", ")[1]))
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
        List<FavorAnswer> favorAnswers = new ArrayList<>();
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 2L));
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 5L));
        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    }
}
