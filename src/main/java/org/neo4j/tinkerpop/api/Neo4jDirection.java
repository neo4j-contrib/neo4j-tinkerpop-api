package org.neo4j.tinkerpop.api;

/**
* @author mh
* @since 25.03.15
*/
public enum Neo4jDirection {
    INCOMING(org.neo4j.graphdb.Direction.INCOMING),OUTGOING(org.neo4j.graphdb.Direction.OUTGOING),BOTH(org.neo4j.graphdb.Direction.BOTH);
    public final org.neo4j.graphdb.Direction direction;

    Neo4jDirection(org.neo4j.graphdb.Direction direction) {
        this.direction = direction;
    }
    public String toString() {return direction.name();}
}
