package com.payhere.kimsan.user.domain;

import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.Products;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    }
}
