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
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.helpers.collection.IteratorWrapper;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.kernel.impl.core.GraphPropertiesImpl;
import org.neo4j.kernel.impl.core.NodeManager;
import org.neo4j.tooling.GlobalGraphOperations;

import java.util.*;

import static org.neo4j.tinkerpop.api.Util.wrapNodes;
import static org.neo4j.tinkerpop.api.Util.wrapRels;

/**
 * @author mh
 * @since 25.03.15
 */
public class Neo4jGraphAPI {
    private final GraphDatabaseService db;
    private final GraphPropertiesImpl graphProps;

    Neo4jGraphAPI(GraphDatabaseService db) {
        this.db = db;
        graphProps = ((GraphDatabaseAPI) this.db).getDependencyResolver().resolveDependency(NodeManager.class).newGraphProperties();
    }

    public Neo4jNode createNode(String...labels) {
        return Util.wrap((labels.length == 0) ? db.createNode() : db.createNode(Util.toLabels(labels)));
    }

    public Neo4jNode getNodeById(long id) {
        return Util.wrap(db.getNodeById(id));
    }
    public Neo4jRelationship getRelationshipById(long id) {
        return Util.wrap(db.getRelationshipById(id));
    }

    public void shutdown() {
        this.db.shutdown();
    }
    public Iterable<Neo4jNode> allNodes() {
        return wrapNodes(db.getAllNodes());
    }
    public Iterable<Neo4jRelationship> allRelationships() {
        return Util.wrapRels(GlobalGraphOperations.at(db).getAllRelationships());
    }

    public Iterable<Neo4jRelationship> findRelationships(String property,Object value) {
        return wrapRels(db.index().getRelationshipAutoIndexer().getAutoIndex().get(property, value));
    }
    public Iterable<Neo4jNode> findNodes(String property,Object value) {
        return wrapNodes((Iterable<Node>)db.index().getNodeAutoIndexer().getAutoIndex().get(property,value));
    }
    public Iterable<Neo4jNode> findNodes(String label) {
        return wrapNodes(db.findNodes(DynamicLabel.label(label)));
    }
    public Iterable<Neo4jNode> findNodes(String label, String property, Object value) {
        return wrapNodes(db.findNodes(DynamicLabel.label(label),property,value));
    }
    public Neo4jTx tx() {
        return new Neo4jTx(db.beginTx());
    }

    public Iterator<Map<String,Object>> execute(String query, Map<String,Object> params) {
        Map<String, Object> nullSafeParams = params == null ? Collections.<String, Object>emptyMap() : params;
        return new IteratorWrapper<Map<String,Object>,Map<String,Object>>(db.execute(query, nullSafeParams)) {
            @Override
            protected Map<String, Object> underlyingObjectToObject(Map<String, Object> row) {
                Map<String,Object> result=new LinkedHashMap<>(row.size());
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    result.put(entry.getKey(),Util.wrapObject(entry.getValue()));
                }
                return result;
            };
        };
    }

    public boolean hasSchemaIndex(String label, String property) {
        Iterable<IndexDefinition> indexes = db.schema().getIndexes(DynamicLabel.label(label));
        for (IndexDefinition index : indexes) {
            for (String prop : index.getPropertyKeys()) {
                if (prop.equals(property)) return true;
            }
        }
        return false;
    }
    public void autoIndexProperties(boolean node, String...properties) {
        if (node) {
            db.index().getNodeAutoIndexer().setEnabled(true);
            for (String property : properties) {
                db.index().getNodeAutoIndexer().startAutoIndexingProperty(property);
            }
        } else {
            db.index().getRelationshipAutoIndexer().setEnabled(true);
            for (String property : properties) {
                db.index().getRelationshipAutoIndexer().startAutoIndexingProperty(property);
            }
        }
    }
    public boolean hasAutoIndex(boolean node, String property) {
        if (node) return db.index().getNodeAutoIndexer().getAutoIndexedProperties().contains(property);
        return db.index().getRelationshipAutoIndexer().getAutoIndexedProperties().contains(property);
    }

    public Iterable<String> getKeys() { return graphProps.getPropertyKeys(); }
    public Object getProperty(String key) { return graphProps.getProperty(key); }
    public boolean hasProperty(String key) { return graphProps.hasProperty(key); }
    public Object removeProperty(String key) {
        return graphProps.removeProperty(key);
    }
    public void setProperty(String key, Object value) {
        graphProps.setProperty(key,value);
    }

}
