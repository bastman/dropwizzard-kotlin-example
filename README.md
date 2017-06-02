# dropwizzard-kotlin-example
playing around with dropwizzard

## status
- experimental
- in progress ...

# findings

- use dropwizard-guicey! (is inspired by hubspot dropwizard-guice) seems to be to best integratio with guice

## Dropwizard Resource (api endpoints)

- do not use @singleton (in spring-boot we use singleton), since you may want to inject HttpRequest
- 

## HK & Guice
- To access HK bindings we need HK2 ServiceLocator: it's instance is registered by GuiceFeature (in time of HK context startup).
- http://xvik.github.io/dropwizard-guicey/4.1.0/guide/lifecycle/