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
    private static List<FavorRecommendationDTO> favorRecommendationDTOS = new ArrayList<>();

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
            productFilter(products, productAnswer.get(0));
        } else {
            List<Product> firstProducts =
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            categoryName, productAnswer.get(0));
            List<Product> secondProducts =
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            categoryName, productAnswer.get(1));
            productFilter(firstProducts, productAnswer.get(0));
            productFilter(secondProducts, productAnswer.get(1));
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

    // Todo: 그립톡, 파우치는 7번 질문에 대한 답변을 dto로 / 워치 스트랩은 5번 질문에 대한 답변을 dto
    // Todo: 폰케이스, 이어폰케이스 질문 분리
    private void productFilter(List<Product> products, String answer) {
        if (answer.equals("폰스트랩") || answer.equals("키링")) {
            fedigProducts.addAll(products);
        } else if (answer.equals("그립톡") || answer.equals("파우치") || answer.equals("워치스트랩")) {
            fedigProducts.addAll(singleFilterStep(products, answer));
        } else {
            fedigProducts.addAll(multiFilterStep(products, answer));
        }
    }
}
