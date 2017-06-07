# dropwizard-kotlin-example
playing around with dropwizard

## status
- experimental
- in progress ...

# findings

- use dropwizard-guicey! (is inspired by hubspot dropwizard-guice) seems to be to best integratio with guice

## Dropwizard Resource (api endpoints)

- if you need access to HttpRequest, do not use @singleton in resource class
  (in spring-boot we preferable use singleton, HttpRequest is injected into method signature) 

## HK & Guice
- To access HK bindings we need HK2 ServiceLocator: it's instance is registered by GuiceFeature (in time of HK context startup).
- http://xvik.github.io/dropwizard-guicey/4.1.0/guide/lifecycle/

## exclude from classpath scan: @InvisibleForScanner
- http://xvik.github.io/dropwizard-guicey/4.1.0/guide/scan/

## testing:
- http://xvik.github.io/dropwizard-guicey/4.1.0/guide/test/
- http://xvik.github.io/dropwizard-guicey/4.1.0/installers/resource/

- GuiceyAppRule vs DropwizardAppRule
- GuiceyAppRule: doesn't start jetty (and so jersey and guice web modules will not be initialized)

For web component tests (servlets, filters, resources) use DropwizardAppRule
InjectorLookup.getInjector(RULE.getApplication()).getBean(MyService.class);

# jdbi 
- http://xvik.github.io/dropwizard-guicey/4.1.0/examples/jdbi/
- http://xvik.github.io/dropwizard-guicey/4.1.0/extras/jdbi/

    DAO / Repository

    @JdbiRepository
    @InTransaction
    interface TweetRepository {
        @GetGeneratedKeys
        @SqlUpdate("INSERT INTO tweets (tweet_id, message, modifiedAt) VALUES (:tweetId, :message" +
                ", UTC_TIMESTAMP(3))")
        fun add(@BindBean tweet: Tweet): Int
    }

    from the docs: Note the use of @InTransaction: it was used to be able to call repository methods without extra annotations (the lowest transaction scope it's repository itself). It will make beans "feel the same" as usual DBI on demand sql object proxies.

- https://github.com/bfkelsey/dropwizard-kotlin-api-example/blob/master/src/main/kotlin/com/bfkelsey/api/jdbi/ProviderDAO.kt

## jdbi kotlin plugins (requires jdbi3)
- https://jdbi.github.io/#_kotlin
- dropwizard jdbi: https://github.com/arteam/dropwizard-jdbi3


## swagger:

- https://github.com/smoketurner/dropwizard-swagger
Open a browser and hit http://localhost:8080/swagger

## mysql (docker)

- run: $ docker-compose -f docker/db/docker-compose.yml up --build

