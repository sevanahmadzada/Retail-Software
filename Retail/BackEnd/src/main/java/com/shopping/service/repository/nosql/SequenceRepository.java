package com.shopping.service.repository.nosql;

public interface SequenceRepository {
    long getNextSequenceId(String key,String tableName);
}
