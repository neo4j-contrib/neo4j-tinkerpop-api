package org.neo4j.tinkerpop.api;

import org.neo4j.graphdb.Transaction;

/**
 * @author mh
 * @since 25.03.15
 */
public class Neo4jTx implements AutoCloseable{
    private final Transaction tx;

    Neo4jTx(Transaction tx) {
        this.tx = tx;
    }

    public void failure() {
        tx.failure();
    }

    public void success() {
        tx.success();
    }
    @Override
    public void close() {
        tx.close();
    }
}
