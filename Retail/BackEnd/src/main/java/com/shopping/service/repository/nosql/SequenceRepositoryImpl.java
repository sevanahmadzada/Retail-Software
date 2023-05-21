package com.shopping.service.repository.nosql;

import com.shopping.service.document.Sequence;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SequenceRepositoryImpl implements SequenceRepository {

    private final MongoOperations mongoOperations;
    private final JdbcTemplate jdbcTemplate;
    private static Map<String, Long> lastIds=new HashMap<>();

    @Override
    public long getNextSequenceId(String key, String tableName) {
        if(!lastIds.containsKey(tableName)){
            Long lastId = jdbcTemplate.queryForObject("select max(id) from "+tableName, Long.class);
            lastIds.put(tableName, lastId);
        }

        Query query = new Query(Criteria.where("_id").is(key));
        Update update = new Update();
        update.inc("seq", 1);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        options.upsert(true);

        Sequence sequenceId = mongoOperations.findAndModify(query, update, options, Sequence.class);

        if (sequenceId == null) {
            throw new NullPointerException("Unable to get sequence id for key" + key);
        }

        return sequenceId.getSeq();
    }

}
