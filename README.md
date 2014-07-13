Cxf_Jpa_Jackson_Example
=======================

Example of java web app with CXF ReST service and JPA (Hibernate/MySQL) using Spring

NOTE: 
 - initial_db.sql script must run manually to create the schema and the tables
 - 4 tables are created but only 2 of them are in use (place and addresses), just not finished

Small java web application that demonstrates some technologies usage:

0. Spring
  - Basic Spring annotations
  - Transactional
  - Unit tests with SpringJUnit4ClassRunner and DB context
  - DB setup (app-context)
  - 

1. Rest service
  - Apache CXF
  - All operations (Get, Post, Put, Delete)
  - PathParam
  - QueryParam
  - Return Response or Exception
  - RestBeans.xml config file for ReST service bean configuration and XML/JSON providers definition
  
2. JPA
  - Hibernate
  - MySQL
  - PersistenceContext annotation
  - Generated ID
  - Mapping (one-to-many, many-to-one)
  - Named query
  - Constraints
  
3. Jackson
  - Default usage via JAXB annotations (AddressDTO, PlaceDTO)
  - ObjectMapper for UnboundXXX classes - no real need, just to illustrate ObjectMapper usage
  - Fields rename via ObjectMapper
  - Eliminate null values field via ObjectMapper
  - Change default visibility level for ObjectMapper to fields only

4. General
  - log4j configuration
  - slf-log4j usage
