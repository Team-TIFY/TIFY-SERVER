package tify.server.domain.domains.question.strategy;

import static tify.server.domain.domains.user.domain.DetailCategory.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.domain.domains.user.domain.DetailCategory;

@Getter
@AllArgsConstructor
public enum StrategyName {
    BFMOI("BFMOI", MOISTURE),
    BFPLA("BFPLA", PLACE),
    BMEYE("BMEYE", EYE),
    BMLIP("BMLIP", LIP),
    BMPER("BMPER", PERFUME),
    FAACC("FAACC", ACCESSORY),
    FCTOP("FCTOP", TOP),
    FEBAG("FEBAG", BAG),
    FEDIG("FEDIG", DIG_PRODUCT),
    FEFAS("FEFAS", FAS_PRODUCT),
    HCCUP("HCCUP", CUP),
    HCDIS("HCDIS", DISH),
    HEEXE("HEEXE", EXERCISE),
    ;

    final String value;
    final DetailCategory detailCategory;
}
