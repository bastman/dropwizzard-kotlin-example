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

