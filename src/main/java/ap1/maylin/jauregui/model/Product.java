package ap1.maylin.jauregui.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "product")
@Schema(description = "Product entity")
public class Product {
    
    @Id
    @Schema(description = "Unique identifier of the product", example = "1")
    private String id;
    
    @Field("name")
    @Schema(description = "Name of the product", example = "Laptop Dell XPS 15")
    private String name;
    
    @Field("description")
    @Schema(description = "Detailed description of the product", example = "High-performance laptop with 16GB RAM and 512GB SSD")
    private String description;
    
    @Field("price")
    @Schema(description = "Price of the product", example = "1299.99")
    private BigDecimal price;
    
    @Field("category")
    @Schema(description = "Category of the product", example = "Electronics")
    private String category;
    
    @Field("stock")
    @Schema(description = "Available stock quantity", example = "50")
    private Integer stock;
    
    @Field("created_at")
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
    
    @Field("updated_at")
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
    
    @Field("active")
    @Schema(description = "Whether the product is active", example = "true")
    private Boolean active;
}
