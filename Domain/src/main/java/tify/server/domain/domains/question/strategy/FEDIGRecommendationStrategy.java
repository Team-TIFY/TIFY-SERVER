package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

@Component
@RequiredArgsConstructor
public class FEDIGRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    private static final String CATEGORY_NAME = "FEDIG";

    // TODO : 만약 취향 답변 수정 기능이 나오면 static List는 불가능할듯..
    private static List<Product> fedigProducts = new ArrayList<>();

    @Override
    public List<Product> recommendation(Long userId, String categoryName) {

        List<FavorRecommendationDTO> initRecommendationDTO = getInitRecommendationDTO(userId);
        List<String> productAnswer =
                Arrays.stream(initRecommendationDTO.get(0).getAnswer().split(", ")).toList();

        for (String string : productAnswer) {
            System.out.println("answer = " + string);
        }

        if (productAnswer.size() == 1) {
            List<Product> products =
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            categoryName, productAnswer.get(0));
            productFilter(products, productAnswer.get(0), userId);
        } else {
            List<Product> findProducts = productAdaptor.queryAllByCategoryName(categoryName);
            productFilter(findProducts, productAnswer.get(0), userId);
            productFilter(findProducts, productAnswer.get(1), userId);
        }

        return fedigProducts;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.valueOf(CATEGORY_NAME);
    }

    private List<FavorRecommendationDTO> getInitRecommendationDTO(Long userId) {
        List<FavorAnswer> favorAnswers = new ArrayList<>();
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 6L));
        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    }

    private List<Product> singleAnswerStep(List<Product> products, String answer) {
        return products.stream()
                .filter(product -> product.getCharacteristic().contains(answer))
                .toList();
    }

    private List<Product> multiAnswerStep(List<Product> products, String answer) {
        List<String> splitAnswer = Arrays.stream(answer.split(", ")).toList();
        if (splitAnswer.size() == 1) {
            return singleAnswerStep(products, splitAnswer.get(0));
        } else {
            return splitAnswer.stream()
                .map(str -> singleAnswerStep(products, str))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        }
    }

    private void productFilter(List<Product> products, String answer, Long userId) {
        if (answer.equals("폰스트랩") || answer.equals("키링")) {
            fedigProducts.addAll(products);
        } else if (answer.equals("그립톡") || answer.equals("파우치")) {
            fedigProducts.addAll(
                    multiAnswerStep(
                            products,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 7L)
                                    .getAnswerContent()));
        } else if (answer.equals("워치 스트랩")) {
            fedigProducts.addAll(
                    singleAnswerStep(
                            products,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 5L)
                                    .getAnswerContent().contains("애플워치") ? "애플워치" : "갤럭시워치"));
        } else if (answer.equals("폰케이스")) {
            List<Product> products1 =
                    singleAnswerStep(
                            products,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 1L)
                                    .getAnswerContent().contains("iPhone") ? "아이폰" : "갤럭시");
            fedigProducts.addAll(
                    multiAnswerStep(
                            products1,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 7L)
                                    .getAnswerContent()));
        } else if (answer.equals("이어폰 케이스")) {
            List<Product> products1 =
                    singleAnswerStep(
                            products,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 2L)
                                    .getAnswerContent().contains("에어팟") ? "에어팟" : "버즈");
            fedigProducts.addAll(
                    multiAnswerStep(
                            products1,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 7L)
                                    .getAnswerContent()));
        }
    }
}
