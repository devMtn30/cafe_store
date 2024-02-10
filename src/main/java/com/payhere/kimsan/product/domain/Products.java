package com.payhere.kimsan.product.domain;

import static com.payhere.kimsan.common.exception.ErrorCode.PRODUCT_NOT_NULL;
import static org.springframework.util.Assert.notNull;

import com.payhere.kimsan.common.exception.CustomException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ObjectUtils;


@Embeddable
public class Products {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        if(ObjectUtils.isEmpty(product)) {
            throw new CustomException(PRODUCT_NOT_NULL);
        }

        productList.add(product);
    }
}
