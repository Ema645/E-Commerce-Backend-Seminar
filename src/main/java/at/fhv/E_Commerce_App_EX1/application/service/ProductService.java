package at.fhv.E_Commerce_App_EX1.application.service;

import at.fhv.E_Commerce_App_EX1.application.dto.CreateProductRequest;
import at.fhv.E_Commerce_App_EX1.application.dto.ProductResponse;
import at.fhv.E_Commerce_App_EX1.application.dto.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse updateProduct(Long id, UpdateProductRequest request);
    void deleteProduct(Long id);
}
