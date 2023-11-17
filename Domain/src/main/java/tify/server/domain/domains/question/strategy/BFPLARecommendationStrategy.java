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
public class BFPLARecommendationStrategy implements ProductRecommendationStrategy {

    private static final String CATEGORY_NAME = "BFPLA";

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    @Override
    public List<Product> recommendation(
            Long userId, String categoryName, List<FavorRecommendationDTO> dto) {

        List<FavorRecommendationDTO> recommendationDTO = getRecommendationDTO(userId);
        List<Product> productList = new ArrayList<>();

        /**
         * 1번 스텝(자주 사용하는 공간향 관련 제품은?) 객관식 max 2문항이므로 2가지 경우(답이 1개, 2개인 경우)로 나눔 답이 1개인경우, filterStep을
         * 그냥 적용 2개인 경우 첫 답변으로 우선 상품들을 갖고오고 두번째 답변으로 갖고온 상품 리스트를 첫 답변으로 갖고온 상품에 없는 것들만 갖고오도록 필터
         */
        FavorRecommendationDTO firstFavorRecommendationDTO = recommendationDTO.get(0);
        String[] splitAnswer = firstFavorRecommendationDTO.getAnswer().split(", ");
        if (splitAnswer.length > 1) {
            productList.addAll(filterStep(CATEGORY_NAME, splitAnswer[0]));
            productList.addAll(
                    filterStep(CATEGORY_NAME, splitAnswer[1]).stream()
                            .filter(product -> !productList.contains(product))
                            .toList());
        } else {
            productList.addAll(filterStep(CATEGORY_NAME, firstFavorRecommendationDTO.getAnswer()));
        }

        /**
         * 2번 스텝(원하는 우리집의 이미지?) 1번 스텝과 거의 동일함 answer가 최대 2개 선택이므로 경우를 나눈다 1개인 경우 productList를 답변으로
         * 필터한다 2개인 경우 필터를 두번 거친다
         */
        FavorRecommendationDTO secondFavorRecommendationDTO = recommendationDTO.get(1);
        List<Product> resultProductList = new ArrayList<>();
        if (secondFavorRecommendationDTO.getAnswer().split(", ").length > 1) {
            return productList.stream()
                    .filter(
                            product ->
                                    product.getCharacteristic()
                                            .contains(
                                                    secondFavorRecommendationDTO.getAnswer()
                                                            .split(", ")[0]))
                    .filter(
                            product ->
                                    product.getCharacteristic()
                                            .contains(
                                                    secondFavorRecommendationDTO.getAnswer()
                                                            .split(", ")[1]))
                    .toList();
        } else {
            return productList.stream()
                    .filter(
                            product ->
                                    product.getCharacteristic()
                                            .contains(secondFavorRecommendationDTO.getAnswer()))
                    .toList();
        }
    }

    private List<Product> filterStep(String categoryName, String answer) {
        return productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, answer);
    }

    private List<FavorRecommendationDTO> getRecommendationDTO(Long userId) {
        List<FavorAnswer> favorAnswers = new ArrayList<>();
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 3L));
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 5L));
        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    }
}
