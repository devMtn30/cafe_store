package com.payhere.kimsan.product.domain.search;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.*;

@Data
@Document(indexName = "product")
@AllArgsConstructor
public class SearchItem {
    @Id
    private Long id;

    @Field(type = FieldType.Long)
    private Long productId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String jaum;
}
