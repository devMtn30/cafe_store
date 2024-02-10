package com.payhere.kimsan.product.application;

import static com.payhere.kimsan.common.exception.ErrorCode.INVALID_USER_INFO;

import com.payhere.kimsan.common.exception.CustomException;
import com.payhere.kimsan.product.application.dto.AddProductRequest;
import com.payhere.kimsan.product.application.dto.ProductResponse;
import com.payhere.kimsan.product.application.dto.UpdateProductRequest;
import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.user.domain.User;
import com.payhere.kimsan.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final UserRepository userRepository;

    public void saveProduct(final AddProductRequest request, final String userId) {
        final Product product = request.toEntity();
        User user = getUser(userId);

        user.addProduct(product);
    }

    public void updateProduct(final UpdateProductRequest request, final String userId, final Long productId) {
        final Product product = request.toEntity(productId);
        User user = getUser(userId);

        user.updateProduct(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductResponse(final String userId) {
        User user = getUser(userId);

        return new ProductResponse(user.getLastAddProductId());
    }

    public void deleteProduct(final String userId, final Long productId) {
        User user = getUser(userId);
        Product target = Product.builder().id(productId).build();
        user.removeProduct(target);
    }
    private User getUser(String userId) {
        return userRepository.findByUserId(userId)
                             .orElseThrow(() -> new CustomException(INVALID_USER_INFO));
    }
}
