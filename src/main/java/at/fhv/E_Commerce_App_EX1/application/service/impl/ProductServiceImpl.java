package at.fhv.E_Commerce_App_EX1.application.service.impl;

import at.fhv.E_Commerce_App_EX1.application.dto.CreateProductRequest;
import at.fhv.E_Commerce_App_EX1.application.dto.ProductResponse;
import at.fhv.E_Commerce_App_EX1.application.dto.UpdateProductRequest;
import at.fhv.E_Commerce_App_EX1.application.mapper.ProductMapper;
import at.fhv.E_Commerce_App_EX1.application.service.ProductService;
import at.fhv.E_Commerce_App_EX1.domain.model.Product;
import at.fhv.E_Commerce_App_EX1.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        if (request.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }
        if (request.getPrice() == null || request.getPrice().signum() < 0) {
            throw new IllegalArgumentException("Price must be a non-negative value.");
        }
        Product product = productMapper.toEntity(request);
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStock(request.getStock());
        Product saved = productRepository.save(existing);
        return productMapper.toResponse(saved);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
