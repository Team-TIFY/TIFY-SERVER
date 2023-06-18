package tify.server.domain.domains.tag.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.tag.domain.SmallCategory;
import tify.server.domain.domains.tag.exception.SmallCategoryNotFoundException;
import tify.server.domain.domains.tag.repository.SmallCategoryRepository;

@Adaptor
@RequiredArgsConstructor
public class SmallCategoryAdaptor {

    private final SmallCategoryRepository smallCategoryRepository;

    public SmallCategory query(Long smallCategoryId) {
        return smallCategoryRepository
                .findById(smallCategoryId)
                .orElseThrow(() -> SmallCategoryNotFoundException.EXCEPTION);
    }

    public SmallCategory save(SmallCategory smallCategory) {
        return smallCategoryRepository.save(smallCategory);
    }
}
