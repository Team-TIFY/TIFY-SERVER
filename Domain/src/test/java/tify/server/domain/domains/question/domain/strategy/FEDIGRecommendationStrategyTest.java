// package tify.server.domain.domains.question.domain.strategy;
//
// import java.util.ArrayList;
// import java.util.List;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Nested;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import tify.server.domain.domains.product.adaptor.ProductAdaptor;
// import tify.server.domain.domains.product.domain.Product;
//
// @DisplayName("상품서치")
// public class FEDIGRecommendationStrategyTest {
//
//    @MockBean
//    private ProductAdaptor productAdaptor;
//    private FEDIGRecommendationStrategy fedigRecommendationStrategy =
//        new FEDIGRecommendationStrategy(productAdaptor);
//
//    @Order(1)
//    @Nested
//    @DisplayName("단건 선택 테스트")
//    class selectOne {
//
//        @Test
//        public void 리스트가_단일이고_포함된_경우() throws Exception {
//            //given
//            String products = "갤럭시, 심플한, 아이폰, 워치스트랩";
//            String answer = "스트랩";
//            //when
//
//            //then
//            Assertions.assertTrue(fedigRecommendationStrategy.isContainsProduct(products,
// answer));
//        }
//
//        @Test
//        public void 리스트가_단일이고_포함된_경우2() throws Exception {
//            //given
//            String products = "갤럭시, 심플한, 아이폰, 스트랩";
//            String answer = "스트랩";
//            //when
//
//            //then
//            Assertions.assertTrue(fedigRecommendationStrategy.isContainsProduct(products,
// answer));
//        }
//
//        @Test
//        public void 리스트가_단일이고_포함되지_경우() throws Exception {
//            //given
//            String products = "갤럭시, 심플하지않은, 아이폰, 폰케이스";
//            String answer = "심플한";
//            //when
//
//            //then
//            Assertions.assertFalse(fedigRecommendationStrategy.isContainsProduct(products,
// answer));
//        }
//    }
//
//    @Order(2)
//    @Nested
//    @DisplayName("복수개를 선택한 경우")
//    class selectTwo {
//
//        @Test
//        public void 리스트에_전부_포함된_경우() throws Exception {
//            //given
//            String products = "갤럭시, 심플한, 아이폰, 폰케이스";
//            String answer = "심플한, 폰케이스";
//            //when
//
//            //then
//            Assertions.assertTrue(fedigRecommendationStrategy.isContainsProduct(products,
// answer));
//        }
//
//
//        @Test
//        public void 리스트에_일부만_포함된_경우() throws Exception {
//            //given
//            String products = "갤럭시, 심플한, 아이폰";
//            String answer = "심플한, 키치한";
//            //when
//
//            //then
//            Assertions.assertTrue(fedigRecommendationStrategy.isContainsProduct(products,
// answer));
//        }
//    }
//
// }
