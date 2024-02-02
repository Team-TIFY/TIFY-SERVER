package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FCTOPRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;
    private Map<String, Long> map = new ConcurrentHashMap<>();

    private static final String CATEGORY_NAME = "FCTOP";

    @Override
    public List<Product> recommendation(Long userId, String categoryName) { // 필요없는 파라미터들 날릴 예정

        List<FavorRecommendationDTO> dtos = getRecommendDTO(userId);

        // 1번 스텝
        List<Product> firstProducts = firstStep(categoryName, dtos.get(0).getAnswer());
        if (firstProducts.size() <= 12) {
            return firstProducts;
        }

        // 2번 스텝
        List<Product> secondProducts = secondStep(firstProducts, dtos.get(1).getAnswer());
        if (secondProducts.size() <= 12) {
            return secondProducts;
        }

        // 3번 스텝
        List<Product> thirdProducts = thirdStep(secondProducts, dtos.get(2).getAnswer());
        if (thirdProducts.size() <= 12) {
            return thirdProducts;
        }

        // 4번 스텝
        return fourthStep(thirdProducts, dtos.get(3).getAnswer());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.valueOf(CATEGORY_NAME);
    }

    private List<FavorRecommendationDTO> getRecommendDTO(Long userId) {
        map.put("티셔츠", 3L);
        map.put("맨투맨", 4L);
        map.put("니트", 5L);

        List<FavorAnswer> favorAnswers = new ArrayList<>();
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 1L));
        FavorAnswer initFavorAnswer =
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 2L);
        List<String> split = Arrays.stream(initFavorAnswer.getAnswerContent().split(", ")).toList();
        favorAnswers.add(initFavorAnswer);
        split.forEach(
                s -> {
                    if (s.equals("티셔츠") || s.equals("맨투맨") || s.equals("니트")) {
                        favorAnswers.add(
                                favorAnswerAdaptor.searchByCategoryNameAndNumber(
                                        userId, CATEGORY_NAME, map.get(s)));
                    }
                });
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 6L));

        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    }

    // 1번째 스텝
    private List<Product> firstStep(String categoryName, String answer) {
        List<Product> productList = new ArrayList<>();
        List<String> splitAnswer = Arrays.stream(answer.split(", ")).toList();
        if (splitAnswer.size() == 1) {
            productList.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            categoryName, splitAnswer.get(0)));
        } else {
            productList.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            categoryName, splitAnswer.get(0)));
            productList.addAll(
                    productAdaptor
                            .queryAllByCategoryNameAndCharacter(categoryName, splitAnswer.get(1))
                            .stream()
                            .filter(
                                    product ->
                                            !productList.contains(
                                                    product)) // 위에서 추가한 상품과 중복되는 상품 제거
                            .toList());
        }
        return productList;
    }

    // 2번째 스텝
    // Todo: 티셔츠 -> 티로 변경 필요할 듯?
    private List<Product> secondStep(List<Product> products, String answer) {
        List<String> splitAnswer = Arrays.stream(answer.split(", ")).toList();
        return products.stream()
                .filter(
                        product -> {
                            if (splitAnswer.size() == 1) {
                                return product.getCharacteristic().contains(splitAnswer.get(0));
                            } else {
                                return product.getCharacteristic().contains(splitAnswer.get(0))
                                        || product.getCharacteristic().contains(splitAnswer.get(1));
                            }
                        })
                .toList();
    }

    // 3번째 스텝
    private List<Product> thirdStep(List<Product> products, String answer) {
        List<String> splitAnswer = Arrays.stream(answer.split(", ")).toList();
        if (!splitAnswer.contains("크롭") && !splitAnswer.contains("상관없음")) {
            return products.stream()
                    .filter(product -> !product.getCharacteristic().contains("크롭"))
                    .toList();
        } else {
            return products;
        }
    }

    // 4번째 스텝. 2번째 스텝과 로직은 동일하나 단계를 구별하기 위해 작성
    private List<Product> fourthStep(List<Product> products, String answer) {
        List<String> splitAnswer = Arrays.stream(answer.split(", ")).toList();
        return products.stream()
                .filter(
                        product -> {
                            if (splitAnswer.size() == 1) {
                                return product.getCharacteristic().contains(splitAnswer.get(0));
                            } else {
                                return product.getCharacteristic().contains(splitAnswer.get(0))
                                        || product.getCharacteristic().contains(splitAnswer.get(1));
                            }
                        })
                .toList();
    }
}
