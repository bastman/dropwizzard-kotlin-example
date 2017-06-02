package com.example.demo

import com.example.demo.api.resources.FooResource
import com.example.demo.logging.AppLogger
//import com.hubspot.dropwizard.guice.GuiceBundle
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


        val mainBundle=GuiceBundle.builder<RestServiceConfiguration>()
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

        bootstrap.addBundle(mainBundle)
        //JdbiBundle.forDbi { configuration, environment ->  }

       // JdbiBundle.forDbi<RestServiceConfiguration>((conf, env) -> locateDbi())
/*
        val jdbiBundle=GuiceBundle.builder<RestServiceConfiguration>()
        GuiceBundle.builder()
                .bundles(JdbiBundle.<RestServiceConfiguration>forDatabase((conf, env) -> conf.getDatabase()))

        forDbi<RestServiceConfiguration>((conf, env) -> locateDbi())
*/

        /*
        val dbiProvider:ConfigAwareProvider<DBI, RestServiceConfiguration> = object : ConfigAwareProvider<DBI, RestServiceConfiguration>{
            override fun get(configuration: RestServiceConfiguration, environment: Environment): DBI {
                return DBIFactory().build(environment, configuration.database, "database")
            }
        }
        forDbi<RestServiceConfiguration>(dbiProvider)
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