package com.payhere.kimsan.product.domain;

import com.payhere.kimsan.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Slice<Product> findByUserAndIdLessThan(User user, Long id, Pageable pageable);
}

