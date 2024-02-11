package com.payhere.kimsan.product.domain;

import static com.payhere.kimsan.common.exception.ErrorCode.PRODUCTS_NOT_EXIST;
import static com.payhere.kimsan.common.exception.ErrorCode.PRODUCT_NOT_FOUND;
import static com.payhere.kimsan.common.exception.ErrorCode.PRODUCT_NOT_NULL;
import static org.springframework.util.Assert.notNull;

import com.payhere.kimsan.common.exception.CustomException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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

    public void updateProduct(Product product) {
        Product matchProduct = getMatchProduct(product);
        matchProduct.update(product);
    }

    private Product getMatchProduct(Product product) {
        return productList.stream()
                          .filter(isMatchProduct(product))
                          .findFirst()
                          .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
    }

    private static Predicate<Product> isMatchProduct(Product product) {
        return item -> item.getId().equals(product.getId());
    }

    public Product getLastProduct() {
        if(productList.isEmpty()) {
            throw new CustomException(PRODUCTS_NOT_EXIST);
        }
        return productList.get(productList.size() - 1);
    }

    public void removeProduct(Product product) {
        Product matchProduct = getMatchProduct(product);
        productList.remove(matchProduct);
    }

    public int getSize() {
        return productList.size();
    }

    public List<Product> getList(int start, int end) {
        return productList.subList(start, end);
    }
}
