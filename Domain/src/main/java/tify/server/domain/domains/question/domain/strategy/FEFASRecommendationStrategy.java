package tify.server.domain.domains.question.domain.strategy;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

@RequiredArgsConstructor
public class FEFASRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;

    @Override
    public List<Product> recommendation(String categoryName, List<FavorRecommendationDTO> dto) {
        List<Product> fefasProducts = new ArrayList<>();
        if (!dto.get(0).getAnswer().contains("거의 안씀")
                && !dto.get(0).getAnswer().contains("특별한 경우만")) {
            fefasProducts.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, "모자"));
        }
        if (!dto.get(4).getAnswer().contains("거의 안함")
                && !dto.get(4).getAnswer().contains("특별한 경우만")) {
            fefasProducts.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, "목도리"));
        }
        if (!dto.get(6).getAnswer().contains("거의 안낌")
                && !dto.get(6).getAnswer().contains("특별한 경우만")) {
            fefasProducts.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, "장갑"));
        }

        List<Product> products = new ArrayList<>();
        products.addAll(hatStep(fefasProducts, dto));
        products.addAll(walletStep(fefasProducts, dto));
        products.addAll(mufflerStep(fefasProducts, dto));
        products.addAll(gloveStep(fefasProducts, dto));

        return products;
    }

    private List<Product> hatStep(List<Product> products, List<FavorRecommendationDTO> dto) {
        StringBuilder stringBuilder = new StringBuilder();

        List<Product> hatList =
                products.stream()
                        .filter(product -> product.getCharacteristic().contains("모자"))
                        .toList();
        String hatAnswer =
                stringBuilder
                        .append(dto.get(1).getAnswer())
                        .append(", ")
                        .append(dto.get(2).getAnswer())
                        .toString();
        return hatList.stream().filter(hat -> hatAnswer.contains(hat.getCharacteristic())).toList();
    }

    private List<Product> walletStep(List<Product> products, List<FavorRecommendationDTO> dto) {
        StringBuilder stringBuilder = new StringBuilder();

        List<Product> walletList =
                products.stream()
                        .filter(product -> product.getCharacteristic().contains("지갑"))
                        .toList();
        String walletAnswer = stringBuilder.append(dto.get(3).getAnswer()).toString();
        return walletList.stream()
                .filter(wallet -> walletAnswer.contains(wallet.getCharacteristic()))
                .toList();
    }

    private List<Product> mufflerStep(List<Product> products, List<FavorRecommendationDTO> dto) {
        StringBuilder stringBuilder = new StringBuilder();

        List<Product> mufflerList =
                products.stream()
                        .filter(product -> product.getCharacteristic().contains("목도리"))
                        .toList();
        String mufflerAnswer = stringBuilder.append(dto.get(5).getAnswer()).toString();
        return mufflerList.stream()
                .filter(muffler -> mufflerAnswer.contains(muffler.getCharacteristic()))
                .toList();
    }

    private List<Product> gloveStep(List<Product> products, List<FavorRecommendationDTO> dto) {
        StringBuilder stringBuilder = new StringBuilder();

        List<Product> gloveList =
                products.stream()
                        .filter(product -> product.getCharacteristic().contains("장갑"))
                        .toList();
        String gloveAnswer = stringBuilder.append(dto.get(7).getAnswer()).toString();
        return gloveList.stream()
                .filter(glove -> gloveAnswer.contains(glove.getCharacteristic()))
                .toList();
    }
}
