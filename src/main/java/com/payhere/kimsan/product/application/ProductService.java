package com.payhere.kimsan.product.application;

import static com.payhere.kimsan.common.exception.ErrorCode.INVALID_USER_INFO;

import com.payhere.kimsan.common.exception.CustomException;
import com.payhere.kimsan.product.application.dto.AddProductRequest;
import com.payhere.kimsan.product.application.dto.GetProductListResponse;
import com.payhere.kimsan.product.application.dto.GetProductResponse;
import com.payhere.kimsan.product.application.dto.ProductResponse;
import com.payhere.kimsan.product.application.dto.UpdateProductRequest;
import com.payhere.kimsan.product.domain.Product;
import com.payhere.kimsan.product.domain.ProductRepository;
import com.payhere.kimsan.user.domain.User;
import com.payhere.kimsan.user.domain.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

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
    public List<Product> getUserProducts(String userId, int page) {
        User user = getUser(userId);
        return user.getProducts(page, 10);
    }


    @Transactional(readOnly = true)
    public GetProductListResponse findProductsByPage(final String userId, final Pageable pageable, final Long cursor) {
        User user = getUser(userId);
        Slice<Product> productSlice = productRepository.findByUserAndIdLessThan(user, cursor, pageable);

        List<GetProductResponse> products = getGetProductResponses(productSlice);

        return new GetProductListResponse(products, productSlice.hasNext());
    }

    @Transactional(readOnly = true)
    public GetProductResponse findProduct(final String userId, Long productId) {
        User user = getUser(userId);
        Product product = user.findProduct(productId);

        return GetProductResponse.from(product);
    }

    private static List<GetProductResponse> getGetProductResponses(Slice<Product> productSlice) {
        return productSlice.getContent().stream()
                           .map(GetProductResponse::from)
                           .toList();
    }

    private User getUser(String userId) {
        return userRepository.findByUserId(userId)
                             .orElseThrow(() -> new CustomException(INVALID_USER_INFO));
    }
}
