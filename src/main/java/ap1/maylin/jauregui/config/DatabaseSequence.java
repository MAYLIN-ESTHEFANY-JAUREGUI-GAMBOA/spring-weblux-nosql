package ap1.maylin.jauregui.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "database_sequences")
public class DatabaseSequence {
    
    @Id
    private String id;
    
    private Long seq;
}
