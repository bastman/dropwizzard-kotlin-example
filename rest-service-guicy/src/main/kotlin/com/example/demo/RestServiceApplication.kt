package com.example.demo

//import com.hubspot.dropwizard.guice.GuiceBundle


import com.example.demo.jdbi.mapper.InstantAsSqlTimestampArgument
import com.example.demo.logging.AppLogger
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import io.federecio.dropwizard.swagger.SwaggerBundle
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration
import org.skife.jdbi.v2.DBI
import ru.vyarus.dropwizard.guice.GuiceBundle
import ru.vyarus.dropwizard.guice.injector.lookup.InjectorLookup
import ru.vyarus.guicey.jdbi.JdbiBundle
import ru.vyarus.guicey.jdbi.dbi.ConfigAwareProvider


class RestServiceApplication : Application<RestServiceConfiguration>() {
    private val LOGGER = AppLogger.get(this::class.java)
    override fun initialize(bootstrap: Bootstrap<RestServiceConfiguration>) {
        super.initialize(bootstrap)

        // must be first, to be able to parse json/yml config files !!!!
        bootstrap.objectMapper
                .registerModules(
                        KotlinModule(),
                        JavaTimeModule(),
                        Jdk8Module(),
                        ParameterNamesModule()
                )
                .disable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS
                )



        bootstrap.addBundle(object : SwaggerBundle<RestServiceConfiguration>() {
            override fun getSwaggerBundleConfiguration(configuration: RestServiceConfiguration): SwaggerBundleConfiguration {
                return configuration.swaggerBundleConfiguration
            }
        })

        val dbiProvider: ConfigAwareProvider<DBI, RestServiceConfiguration> = object : ConfigAwareProvider<DBI, RestServiceConfiguration> {
            override fun get(configuration: RestServiceConfiguration, environment: Environment): DBI {
                val jdbi = DBIFactory().build(environment, configuration.database, "database")



                //jdbi.installPlugin(KotlinPlugin())
                //jdbi.installPlugin(KotlinSqlObjectPlugin());
                jdbi.registerArgumentFactory(InstantAsSqlTimestampArgument())
                // jdbi.onDemand()
                return jdbi
            }
        }
        val jdbiBundle = forDbi<RestServiceConfiguration>(dbiProvider)


        val guiceBundle = GuiceBundle.builder<RestServiceConfiguration>()
                .bundles(jdbiBundle)

                // enable classpath scanning
                .enableAutoConfig(javaClass.`package`.name)

                .modules(RestServiceModule())

                // enable @WebServlet, @WebFilter, @WebListener
                .useWebInstallers()

                // enable DiagnosticBundle.class
                // limit output to only show installers
                //.printAvailableInstallers()

                // optional: enable automatic registration of dropwizard bundles
                // guicey checks registered dropwizard bundles if they implement interface GuiceyBundle and register them as guicey bundles
                // - e.g.: diagnostics bundle
                .configureFromDropwizardBundles()
                .printDiagnosticInfo()

                // optional: auto install dropwizard commands
                .searchCommands()

                // enable HkDebugBundle: strictScopeControl
                // throw ex if sth wrong with hk vs guice
                .strictScopeControl()

                .build()

        bootstrap.addBundle(guiceBundle)



        /*
        bootstrap.addBundle(object : SwaggerBundle<RestServiceConfiguration>() {
            override fun getSwaggerBundleConfiguration(sampleConfiguration: RestServiceConfiguration): SwaggerBundleConfiguration {
                // this would be the preferred way to set up swagger, you can also construct the object here programtically if you want
                return sampleConfiguration.swaggerBundleConfiguration
            }
        })
        */
    }

    fun <C : Configuration> forDbi(dbi: ConfigAwareProvider<DBI, C>): JdbiBundle {
        return JdbiBundle.forDbi(dbi)
    }


    override fun run(configuration: RestServiceConfiguration, environment: Environment) {
        val injector = InjectorLookup.getInjector(this).get()
        LOGGER.info("+++++++++++++++++++++++++")
        LOGGER.info("run() injector=$injector")
        LOGGER.info("+++++++++++++++++++++++++")

        //environment.jersey().register(FooResource::class.java)
        // environment.lifecycle().manage(guiceBundle.injector.getInstance(TemplateHealthCheck::class.java))
    }

}