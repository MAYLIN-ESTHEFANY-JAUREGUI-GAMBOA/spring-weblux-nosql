package ap1.maylin.jauregui.impl;

import ap1.maylin.jauregui.config.SequenceGeneratorService;
import ap1.maylin.jauregui.model.Product;
import ap1.maylin.jauregui.repository.ProductRepository;
import ap1.maylin.jauregui.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public Mono<Product> createProduct(Product product) {
        return sequenceGenerator.generateSequence("product_sequence")
                .map(seq -> {
                    product.setId(String.valueOf(seq));
                    product.setCreatedAt(LocalDateTime.now());
                    product.setUpdatedAt(LocalDateTime.now());
                    product.setActive(true);
                    return product;
                })
                .flatMap(productRepository::save);
    }

    @Override
    public Mono<Product> getProductById(String id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found with id: " + id)));
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> updateProduct(String id, Product product) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setStock(product.getStock());
                    existingProduct.setUpdatedAt(LocalDateTime.now());
                    existingProduct.setActive(product.getActive());
                    return productRepository.save(existingProduct);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found with id: " + id)));
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }

    @Override
    public Flux<Product> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Flux<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Flux<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public Flux<Product> getActiveProducts() {
        return productRepository.findByActive(true);
    }

    @Override
    public Flux<Product> getProductsInStock() {
        return productRepository.findByStockGreaterThan(0);
    }

    @Override
    public Mono<Product> activateProduct(String id) {
        return getProductById(id)
                .flatMap(product -> {
                    product.setActive(true);
                    product.setUpdatedAt(LocalDateTime.now());
                    return updateProduct(id, product);
                });
    }

    @Override
    public Mono<Product> deactivateProduct(String id) {
        return getProductById(id)
                .flatMap(product -> {
                    product.setActive(false);
                    product.setUpdatedAt(LocalDateTime.now());
                    return updateProduct(id, product);
                });
    }
}
