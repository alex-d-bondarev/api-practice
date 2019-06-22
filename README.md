Practice API testing
====================

Run generic testNG tests
------------------------

`mvn clean test`

Run generic tests with mocks
----------------------------

1. `docker-compose up -d`
1. Open [GenericMockServerExample](src/test/java/GenericMockServerExample.java)
1. Trigger tests from IDE
1. `docker-compose down -v`