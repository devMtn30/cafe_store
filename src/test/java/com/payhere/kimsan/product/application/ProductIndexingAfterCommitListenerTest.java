package com.payhere.kimsan.product.application;

import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.search.SearchItemRepository;
import com.payhere.kimsan.product.domain.search.SearchItem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.payhere.kimsan.common.HangulUtils.getAllJaum;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class ProductIndexingAfterCommitListenerTest {

    @Test
    public void afterCommitTest() {
        // given
        Product product = new Product();
        product.setName("testName");
        product.setId(1L);

        SearchItemRepository searchItemRepository = Mockito.mock(SearchItemRepository.class);
        ProductIndexingAfterCommitListener listener = new ProductIndexingAfterCommitListener(product, searchItemRepository);

        // when
        listener.afterCommit();

        // then
        verify(searchItemRepository).save(any(SearchItem.class));
    }
}
