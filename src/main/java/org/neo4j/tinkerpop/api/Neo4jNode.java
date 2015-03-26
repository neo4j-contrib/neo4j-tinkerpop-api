/**
 * Licensed to Neo Technology under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Neo Technology licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.neo4j.tinkerpop.api;

import org.neo4j.graphdb.*;
import org.neo4j.helpers.collection.IterableWrapper;
import java.util.List;

import static org.neo4j.graphdb.DynamicRelationshipType.withName;
import static org.neo4j.tinkerpop.api.Util.wrap;

/**
 * @author mh
 * @since 25.03.15
 */
public class Neo4jNode extends Neo4jEntity<Node> {
    public Neo4jNode(Node node) {
        super(node);
    }
    public List<String> labels() { return Util.toLabels(entity.getLabels());}
    public boolean hasLabel(String label) { return entity.hasLabel(DynamicLabel.label(label));}
    public void addLabel(String label) { entity.addLabel(DynamicLabel.label(label));}
    public void removeLabel(String label) { entity.removeLabel(DynamicLabel.label(label));}

    public int degree(Neo4jDirection direction, String type) {
        return type == null ?
                (direction != null ?
                        entity.getDegree(direction.direction) :
                        entity.getDegree()) :
                (direction != null ?
                        entity.getDegree(withName(type), direction.direction) :
                        entity.getDegree(withName(type)));
    }
    public Iterable<Neo4jRelationship> relationships(Neo4jDirection direction, String...types) {
        Iterable<Relationship> relationships = types.length == 0 ?
                (direction != null ?
                        entity.getRelationships(direction.direction) :
                        entity.getRelationships()) :
                (direction != null ?
                        entity.getRelationships(direction.direction, Util.types(types)) :
                        entity.getRelationships(Util.types(types)));

        return new IterableWrapper<Neo4jRelationship, Relationship>(relationships) {
            @Override
            protected Neo4jRelationship underlyingObjectToObject(Relationship relationship) {
                return new Neo4jRelationship(relationship);
            }
        };
    }
    public Neo4jRelationship connectTo(Neo4jNode node, String type) {
        return wrap(entity.createRelationshipTo(node.entity,DynamicRelationshipType.withName(type)));
    }
}
