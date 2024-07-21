package studio.jasonsu.myfitness

import androidx.lifecycle.ViewModel
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Before
import org.junit.Test

class ArchitectureUnitTest {

    private lateinit var ui: Layer
    private lateinit var domain: Layer
    private lateinit var repository: Layer
    private lateinit var datasource: Layer
    private lateinit var database: Layer

    @Before
    fun before() {
        ui = Layer("UI", "studio.jasonsu.myfitness.ui..")
        domain = Layer("Domain", "studio.jasonsu.myfitness.domain..")
        repository = Layer("Repository", "studio.jasonsu.myfitness.repository..")
        datasource = Layer("Datasource", "studio.jasonsu.myfitness.datasource..")
        database = Layer("Database", "studio.jasonsu.myfitness.database..")
    }

    @Test
    fun `classes with 'UseCase' suffix should reside in 'usecase' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..usecase..") }
    }

    @Test
    fun `classes extending 'ViewModel' should have 'ViewModel' suffix`() {
        Konsist.scopeFromProject()
            .classes()
            .withAllParentsOf(ViewModel::class)
            .assertTrue { it.name.endsWith("ViewModel") }
    }


    @Test
    fun `UI layers should depend on Domain, Repository, and Datasource layers`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                ui.dependsOn(domain)
                ui.dependsOn(repository)
                ui.dependsOn(datasource)
            }
    }

    @Test
    fun `Domain layers should depend on Repository layers`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                domain.dependsOn(repository)
            }
    }

    @Test
    fun `Repository layers should depend on Datasource and Database layers`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                repository.dependsOn(datasource)
                repository.dependsOn(database)
            }
    }

    @Test
    fun `Datasource layers should depend on Database layers`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                datasource.dependsOn(database)
            }
    }

    @Test
    fun `Database layers should not depend on anything`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                database.dependsOnNothing()
            }
    }
}