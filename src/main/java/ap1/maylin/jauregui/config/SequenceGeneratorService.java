package ap1.maylin.jauregui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    @Autowired
    private ReactiveMongoOperations mongoOperations;

    public Mono<Long> generateSequence(String seqName) {
        return mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq", 1),
                DatabaseSequence.class)
                .map(DatabaseSequence::getSeq)
                .switchIfEmpty(Mono.defer(() -> {
                    DatabaseSequence sequence = new DatabaseSequence();
                    sequence.setId(seqName);
                    sequence.setSeq(1L);
                    return mongoOperations.save(sequence)
                            .map(DatabaseSequence::getSeq);
                }));
    }
}
