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

