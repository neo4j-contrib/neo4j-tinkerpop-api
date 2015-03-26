Apache Licensed Neo4j API for Tinkerpop3
========================================

This API is meant to be consumed by neo4j-gremlin in Tinkerpop3, it wraps the Neo4j APIs
adapting them to a smaller, more convenient surface.

The classes provided:

* Neo4jFactory
** open ->
* Neo4jGraphAPI
** transaction (Neo4jTx)
** graph-properties
** create and find nodes

* Neo4jEntity
** id, delete, get-, has-, set-, remove-property
* Neo4jNode
** get and create relationships
** get, has, add labels
** degree
* Neo4jRelationship
** start,end,other node
** type
** Neo4jDirection

