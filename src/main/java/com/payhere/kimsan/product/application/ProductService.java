package com.payhere.kimsan.product.application;

import static com.payhere.kimsan.common.exception.ErrorCode.INVALID_USER_INFO;

import com.payhere.kimsan.common.exception.CustomException;
import com.payhere.kimsan.product.application.dto.AddProductRequest;
import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.user.domain.User;
import com.payhere.kimsan.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.index.AliasAction.Add;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final UserRepository userRepository;

    @Transactional
    public void saveProduct(final AddProductRequest request, final String userId) {
        final Product product = request.toEntity();
        User user = userRepository.findByUserId(userId)
                                  .orElseThrow(() -> new CustomException(INVALID_USER_INFO));

        user.addProduct(product);
    }
}