# Demo Booking service

### Used Technologies
* KeyCloak - for authorization purposes. User should first authenticate in KeyCloak and obtain JWT token. But /api/v1/phone/** endpoints are not secured, as those are potentially provide public information. So JWT token needed only to book and return phones.
* PostgreSQL - stores data 
* RestAPI - provides interface to interact with service
* RestTemplate - to make rest requests to third party services, such as FonoAPI
* SpringBoot - main framework
* Liquibase - to manage database migrations
* JPARepository - to make database requests

### Entities
* Phone - represents phone information, also stores availability flag
* Booking - represents booking information, created when user books phone. After phone returning process it does not removed from database for history purposes.

P.S. List of predefined phones already inserted into database during first run.

P.S.S You may use  provided docker-compose in order to run KeyCloak and PostgreSQL.
