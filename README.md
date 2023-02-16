# Microservice components and wiring

![microservices wiring](https://user-images.githubusercontent.com/13161714/219713403-b84a91ed-3dd6-4f36-952f-309b941dda7a.png)

This repository contains a set of projects demonstrating each aspect of microservices.
The project is based on a nice tutorial from youtube (with my own additions and tweaks): https://www.youtube.com/@ProgrammingTechie

Each git-commit talks about an aspect of microservices.

contains aspects such as,

- multiple microservices
- service discovery using eureka
- API gateway
- services intercommunication
- circuit break pattern using resilience4j
- timeout and retry operations
- authentication using keycloak
- observing
  - micrometer
  - Zipkin
  - Prometheus
  - Grafana
- use Kafka for event management
- dockerize services
  - use docker-compose
  - use maven plugin: jib for docker image building and distributing
- distributed configuration - use a git repository to keep application configurations (todo)
- protect secrets using Vault - by Hashi corp (todo)
- misc
  - use flyway for future database migration (todo)
  - use swagger for exposing endpoint details to outside (todo)
- add sample unit tests (todo)
- add sample integration tests (todo)
- configure ci/cd pipeline (todo)
