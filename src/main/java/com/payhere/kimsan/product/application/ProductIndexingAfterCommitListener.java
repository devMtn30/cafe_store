package com.payhere.kimsan.product.application;

import static com.payhere.kimsan.common.utils.HangulUtils.getAllJaum;

import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.search.SearchItemRepository;
import com.payhere.kimsan.product.domain.search.SearchItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronization;

@Slf4j
public class ProductIndexingAfterCommitListener implements TransactionSynchronization {

    private final Product product;
    private final SearchItemRepository searchItemRepository;

    public ProductIndexingAfterCommitListener(Product product, SearchItemRepository searchItemRepository) {
        this.product = product;
        this.searchItemRepository = searchItemRepository;
    }

    @Override
    public void afterCommit() {
        final String productName = product.getName();

        searchItemRepository.save(new SearchItem(product.getId(), product.getId(), productName, getAllJaum(productName)));
    }
}