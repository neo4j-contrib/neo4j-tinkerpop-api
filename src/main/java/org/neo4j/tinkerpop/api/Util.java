package org.neo4j.tinkerpop.api;

import org.neo4j.graphdb.*;
import org.neo4j.helpers.collection.IterableWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.neo4j.graphdb.DynamicRelationshipType.withName;

/**
 * @author mh
 * @since 25.03.15
 */
class Util {
    static Label[] toLabels(String... labels) {
        Label[] result = new Label[labels.length];
        for (int i = 0; i < labels.length; i++) {
            result[i]= DynamicLabel.label(labels[i]);
        }
        return result;
    }

    static List<String> toLabels(Iterable<Label> labels) {
        List<String> result = new ArrayList<>();
        for (Label label : labels) {
            result.add(label.name());
        }
        return result;
    }

    static RelationshipType[] types(String...types) {
        RelationshipType[] result = new RelationshipType[types.length];
        for (int i = 0; i < types.length; i++) {
            result[i] = withName(types[i]);
        }
        return result;
    }

    static Neo4jNode wrap(Node node) {
        return new Neo4jNode(node);
    }

    static Neo4jRelationship wrap(Relationship rel) {
        return new Neo4jRelationship(rel);
    }

    static Iterable<Neo4jNode> wrapNodes(final Iterable<Node> nodes) {
        return new IterableWrapper<Neo4jNode, Node>(nodes) {
            @Override
            protected Neo4jNode underlyingObjectToObject(Node node) {
                return wrap(node);
            }
        };
    }
    static Iterable<Neo4jNode> wrapNodes(final ResourceIterator<Node> nodes) {
        return new IterableWrapper<Neo4jNode, Node>(new SingleIteratorWrapper(nodes)) {
            @Override
            protected Neo4jNode underlyingObjectToObject(Node node) {
                return wrap(node);
            }
        };
    }
    static Iterable<Neo4jRelationship> wrapRels(final Iterable<Relationship> rels) {
        return new IterableWrapper<Neo4jRelationship, Relationship>(rels) {
            @Override
            protected Neo4jRelationship underlyingObjectToObject(Relationship rel) {
                return wrap(rel);
            }
        };
    }

    static Object wrapObject(Object value) {
        if (value == null) return null;
        if (value instanceof Node) return wrap((Node) value);
        if (value instanceof Relationship) return wrap((Relationship) value);
        if (value instanceof Iterable) {
            List<Object> result = new ArrayList<>();
            for (Object o : (Iterable)value) {
                result.add(wrapObject(o));
            }
        }
        return value;
    }

    private static class SingleIteratorWrapper implements Iterable<Node> {
        private final ResourceIterator<Node> nodes;

        public SingleIteratorWrapper(ResourceIterator<Node> nodes) {
            this.nodes = nodes;
        }

        @Override
        public Iterator<Node> iterator() {
            return nodes;
        }
    }
}
