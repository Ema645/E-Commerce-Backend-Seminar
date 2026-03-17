package at.fhv.E_Commerce_App_EX1.application.mapper;

import at.fhv.E_Commerce_App_EX1.application.dto.CreateProductRequest;
import at.fhv.E_Commerce_App_EX1.application.dto.ProductResponse;
import at.fhv.E_Commerce_App_EX1.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    public Product toEntity(CreateProductRequest request) {
        return new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock()
        );
    }
}
