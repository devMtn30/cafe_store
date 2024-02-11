package com.payhere.kimsan.product.application.dto;

import java.util.List;

public record GetProductListResponse(
    List<GetProductResponse> products,
    boolean hasNext
) {

}
