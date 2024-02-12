package com.payhere.kimsan.product.domain.search;

import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchItemRepository extends ElasticsearchRepository<SearchItem, Long> {
    @Query("{\"query_string\":{\"query\":\"*?0*\",\"fields\":[\"name\",\"jaum\"]}}")
    List<SearchItem> findByCustomQuery(String queryString);

    void deleteByProductId(Long productId);
}
