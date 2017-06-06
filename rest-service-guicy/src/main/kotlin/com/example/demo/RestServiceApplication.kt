package com.example.demo

//import com.hubspot.dropwizard.guice.GuiceBundle


import com.example.demo.jdbi.mapper.InstantAsSqlTimestampArgument
import com.example.demo.logging.AppLogger
import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI
import ru.vyarus.dropwizard.guice.GuiceBundle
import ru.vyarus.dropwizard.guice.injector.lookup.InjectorLookup
import ru.vyarus.guicey.jdbi.JdbiBundle
import ru.vyarus.guicey.jdbi.dbi.ConfigAwareProvider

class RestServiceApplication : Application<RestServiceConfiguration>() {
    private val LOGGER = AppLogger.get(this::class.java)

    override fun initialize(bootstrap: Bootstrap<RestServiceConfiguration>) {
        super.initialize(bootstrap)

        val dbiProvider: ConfigAwareProvider<DBI, RestServiceConfiguration> = object : ConfigAwareProvider<DBI, RestServiceConfiguration> {
            override fun get(configuration: RestServiceConfiguration, environment: Environment): DBI {
                val jdbi = DBIFactory().build(environment, configuration.database, "database")
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