package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
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
public class FEFASRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    private static final String CATEGORY_NAME = "FEFAS";

    @Override
    public List<Product> recommendation(Long userId, String categoryName) {
        List<Product> fefasProducts = new ArrayList<>();
        List<FavorRecommendationDTO> initRecommendationDTO = getInitRecommendationDTO(userId);
        if (!initRecommendationDTO.get(0).getAnswer().contains("거의 안씀")
                && !initRecommendationDTO.get(0).getAnswer().contains("특별한 경우만")) {
            fefasProducts.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, "모자"));
        }
        if (!initRecommendationDTO.get(1).getAnswer().contains("거의 안함")
                && !initRecommendationDTO.get(1).getAnswer().contains("특별한 경우만")) {
            fefasProducts.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, "목도리"));
        }
        if (!initRecommendationDTO.get(2).getAnswer().contains("거의 안낌")
                && !initRecommendationDTO.get(2).getAnswer().contains("특별한 경우만")) {
            fefasProducts.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, "장갑"));
        }

        List<Product> products = new ArrayList<>();
        products.addAll(hatStep(userId, fefasProducts));
        products.addAll(walletStep(userId, fefasProducts));
        products.addAll(mufflerStep(userId, fefasProducts));
        products.addAll(gloveStep(userId, fefasProducts));

        return products;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.valueOf(CATEGORY_NAME);
    }

    // 모자, 목도리, 장갑 을 특별한 경우, 거의 안씀을 골랐는지를 판별하는 답변들
    private List<FavorRecommendationDTO> getInitRecommendationDTO(Long userId) {
        List<FavorAnswer> initFavorAnswers = new ArrayList<>();
        // 모자
        initFavorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 1L));

        // 목도리
        initFavorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 5L));

        // 장갑
        initFavorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 7L));
        return initFavorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    }

    private List<Product> hatStep(Long userId, List<Product> products) {
        StringBuilder stringBuilder = new StringBuilder();

        // 모자 관련 답변 조회
        List<FavorRecommendationDTO> dto =
                Stream.of(
                                favorAnswerAdaptor.searchByCategoryNameAndNumber(
                                        userId, CATEGORY_NAME, 2L),
                                favorAnswerAdaptor.searchByCategoryNameAndNumber(
                                        userId, CATEGORY_NAME, 3L))
                        .map(FavorRecommendationDTO::from)
                        .toList();

        List<Product> hatList =
                products.stream()
                        .filter(product -> product.getCharacteristic().contains("모자"))
                        .toList();
        String hatAnswer =
                stringBuilder
                        .append(dto.get(0).getAnswer())
                        .append(", ")
                        .append(dto.get(1).getAnswer())
                        .toString();
        return hatList.stream().filter(hat -> hat.getCharacteristic().contains(hatAnswer)).toList();
    }

    private List<Product> walletStep(Long userId, List<Product> products) {
        String answerContent =
                favorAnswerAdaptor
                        .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 4L)
                        .getAnswerContent();

        List<Product> walletList =
                products.stream()
                        .filter(product -> product.getCharacteristic().contains("지갑"))
                        .toList();
        return walletList.stream()
                .filter(wallet -> wallet.getCharacteristic().contains(answerContent))
                .toList();
    }

    private List<Product> mufflerStep(Long userId, List<Product> products) {

        String answerContent =
                favorAnswerAdaptor
                        .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 6L)
                        .getAnswerContent();
        List<Product> mufflerList =
                products.stream()
                        .filter(product -> product.getCharacteristic().contains("목도리"))
                        .toList();
        return mufflerList.stream()
                .filter(muffler -> muffler.getCharacteristic().contains(answerContent))
                .toList();
    }

    private List<Product> gloveStep(Long userId, List<Product> products) {

        String answerContent =
                favorAnswerAdaptor
                        .searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 8L)
                        .getAnswerContent();
        List<Product> gloveList =
                products.stream()
                        .filter(product -> product.getCharacteristic().contains("장갑"))
                        .toList();
        return gloveList.stream()
                .filter(glove -> glove.getCharacteristic().contains(answerContent))
                .toList();
    }
}
