package tify.server.domain.domains.question.strategy;


import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FCTOPRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    @Override
    public List<Product> recommendation(
            Long userId, String categoryName, List<FavorRecommendationDTO> dtos) {

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

    // Todo: 세환이한테 물어보기 => 핏의 경우 무엇을 선택하더라도 (티셔츠, 맨투맨, 니트 모두)가 되어야함
    //    private List<FavorRecommendationDTO> getRecommendationDTO() {
    //        List<FavorAnswer> favorAnswers = new ArrayList<>();
    //        favorAnswers.add(favorAnswerAdaptor.searchByCategoryNameAndNumber(CATEGORY_NAME, 2L));
    //        favorAnswers.add(favorAnswerAdaptor.searchByCategoryNameAndNumber(CATEGORY_NAME, 3L));
    //        favorAnswers.add(favorAnswerAdaptor.searchByCategoryNameAndNumber(CATEGORY_NAME, 4L));
    //        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    //    }

    // 1번째 스텝
    private List<Product> firstStep(String categoryName, String answer) {
        return productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, answer);
    }

    // 2번째 스텝
    private List<Product> secondStep(List<Product> products, String answer) {
        List<String> splitAnswer = Arrays.stream(answer.split(", ")).toList();
        return products.stream()
                .filter(
                        product -> {
                            if (splitAnswer.size() == 1) {
                                return product.getCharacteristic().contains(splitAnswer.get(0));
                            } else {
                                return product.getCharacteristic().contains(splitAnswer.get(0))
                                        && product.getCharacteristic().contains(splitAnswer.get(1));
                            }
                        })
                .toList();
    }

    // 3번째 스텝
    private List<Product> thirdStep(List<Product> products, String answer) {
        List<String> splitAnswer = Arrays.stream(answer.split(", ")).toList();
        if (!splitAnswer.contains("크롭")) {
            return products.stream()
                    .filter(
                            product -> {
                                return !product.getCharacteristic().contains("크롭");
                            })
                    .toList();
        } else {
            return products;
        }
    }

    // 4번째 스텝
    private List<Product> fourthStep(List<Product> products, String answer) {
        List<String> splitAnswer = Arrays.stream(answer.split(", ")).toList();
        return products.stream()
                .filter(product -> splitAnswer.contains(product.getCharacteristic()))
                .toList();
    }
}
