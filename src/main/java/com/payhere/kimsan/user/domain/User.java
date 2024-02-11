package com.payhere.kimsan.user.domain;

import static com.payhere.kimsan.common.exception.ErrorCode.PRODUCTS_NOT_EXIST;

import com.payhere.kimsan.common.exception.CustomException;
import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.Products;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String userId;
    private String password;
    private String name;
    private String regNo;

    @Embedded
    private final Products products = new Products();

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", regNo='" + regNo + '\'' +
            '}';
    }

    public void addProduct(Product product) {
        products.addProduct(product);
        product.setUser(this);

    }

    public Long getLastAddProductId() {
        Product product = products.getLastProduct();
        return product.getId();
    }

    public void updateProduct(Product product) {
        products.updateProduct(product);
    }

    public void removeProduct(Product product) {
        products.removeProduct(product);
        product.setUser(null);
    }

    public List<Product> getProducts(int page, int size) {
        int start = (page - 1) * size;
        int end = Math.min(start + size, products.getSize());
        if (start > end) {
            throw new CustomException(PRODUCTS_NOT_EXIST);
        }
        return products.getList(start, end);
    }

    public Product findProduct(Long productId) {
        return products.getMatchProduct(Product.builder().id(productId).build());
    }
}
