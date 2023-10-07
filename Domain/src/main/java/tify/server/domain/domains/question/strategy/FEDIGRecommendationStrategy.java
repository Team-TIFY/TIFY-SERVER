package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

@RequiredArgsConstructor
public class FEDIGRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    private static final String CATEGORY_NAME = "FEDIG";

    private static List<Product> fedigProducts = new ArrayList<>();

    @Override
    public List<Product> recommendation(
            Long userId, String categoryName, List<FavorRecommendationDTO> dto) {

        List<FavorRecommendationDTO> initRecommendationDTO = getInitRecommendationDTO(userId);
        List<String> productAnswer =
                Arrays.stream(initRecommendationDTO.get(0).getAnswer().split(", ")).toList();

        if (productAnswer.size() == 1) {
            List<Product> products =
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            categoryName, productAnswer.get(0));
            productFilter(products, productAnswer.get(0), userId);
        } else {
            List<Product> firstProducts =
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            categoryName, productAnswer.get(0));
            List<Product> secondProducts =
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            categoryName, productAnswer.get(1));
            productFilter(firstProducts, productAnswer.get(0), userId);
            productFilter(secondProducts, productAnswer.get(1), userId);
        }

        return fedigProducts;
    }

    private List<FavorRecommendationDTO> getInitRecommendationDTO(Long userId) {
        List<FavorAnswer> favorAnswers = new ArrayList<>();
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 6L));
        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    }

    private List<Product> singleFilterStep(List<Product> products, String answer) {
        return products.stream()
                .filter(product -> product.getCharacteristic().contains(answer))
                .toList();
    }

    private List<Product> multiFilterStep(List<Product> products, String answer) {
        return products.stream()
                .filter(
                        product -> {
                            return isContainsProduct(product.getCharacteristic(), answer);
                        })
                .toList();
    }

    private boolean isContainsProduct(String productCharacter, String answer) {
        List<String> selectWord = Arrays.stream(answer.split(",")).map(String::trim).toList();
        for (String word : selectWord) {
            if (productCharacter.contains(word)) {
                return true;
            }
        }
        return false;
    }

    private void productFilter(List<Product> products, String answer, Long userId) {
        if (answer.equals("폰스트랩") || answer.equals("키링")) {
            fedigProducts.addAll(products);
        } else if (answer.equals("그립톡") || answer.equals("파우치")) {
            fedigProducts.addAll(
                    singleFilterStep(
                            products,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 7L)
                                    .getAnswerContent()));
        } else if (answer.equals("워치스트랩")) {
            fedigProducts.addAll(
                    singleFilterStep(
                            products,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 5L)
                                    .getAnswerContent()));
        } else if (answer.equals("폰케이스")) {
            List<Product> products1 =
                    singleFilterStep(
                            products,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 1L)
                                    .getAnswerContent());
            fedigProducts.addAll(
                    singleFilterStep(
                            products1,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 7L)
                                    .getAnswerContent()));
        } else if (answer.equals("이어폰 케이스")) {
            List<Product> products1 =
                    singleFilterStep(
                            products,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 2L)
                                    .getAnswerContent());
            fedigProducts.addAll(
                    singleFilterStep(
                            products1,
                            favorAnswerAdaptor
                                    .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 7L)
                                    .getAnswerContent()));
        }
    }
}
