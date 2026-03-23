package ap1.maylin.jauregui.repository;

import ap1.maylin.jauregui.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    
    Flux<Product> findByNameContainingIgnoreCase(String name);
    
    Flux<Product> findByCategory(String category);
    
    Flux<Product> findByActive(Boolean active);
    
    Flux<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    
    Flux<Product> findByStockGreaterThan(Integer stock);
}
