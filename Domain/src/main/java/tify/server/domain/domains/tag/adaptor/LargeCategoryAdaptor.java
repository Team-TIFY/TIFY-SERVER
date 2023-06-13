package tify.server.domain.domains.tag.adaptor;

import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.tag.domain.LargeCategory;
import tify.server.domain.domains.tag.exception.LargeCategoryNotFoundException;
import tify.server.domain.domains.tag.repository.LargeCategoryRepository;

@Adaptor
@RequiredArgsConstructor
public class LargeCategoryAdaptor {

  private final LargeCategoryRepository largeCategoryRepository;

  public LargeCategory query(Long largeCategoryId) {
    return largeCategoryRepository
            .findById(largeCategoryId)
            .orElseThrow(() -> LargeCategoryNotFoundException.EXCEPTION);
  }

  public LargeCategory save(LargeCategory largeCategory) {
    return largeCategory;
  }

}
