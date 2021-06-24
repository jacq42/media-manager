package de.jkrech.mediamanager.quality

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import com.tngtech.archunit.library.Architectures
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RestController

@AnalyzeClasses(packages = ArchitectureSpec.IMPORT_PACKAGE, importOptions = [ ExcludeTestClasses.class ])
class ArchitectureSpec {

  static final String IMPORT_PACKAGE = "de.jkrech.mediamanager"

  // -- structure

  @ArchTest
  def "controllers are ports"(JavaClasses importedClasses) {
    when:
    def rule = ArchRuleDefinition.classes().that().areAnnotatedWith(RestController)
        .should().resideInAPackage("..ports.http..")
        .as("rest controller are adapters")

    then:
    rule.check(importedClasses)
  }

  @ArchTest
  def "services in application"(JavaClasses importedClasses) {
    when:
    def rule = ArchRuleDefinition.classes().that().areAnnotatedWith(Service)
        .should().resideInAPackage("..application..")
        .as("rest controller are adapters")

    then:
    rule.check(importedClasses)
  }

  @ArchTest
  def "layer check"(JavaClasses importedClasses) {
    when:
    def rule = Architectures.layeredArchitecture()
        .layer("domain").definedBy("..domain..")
        .layer("application").definedBy("..application..")
        .layer("ports").definedBy("..ports..")
        .layer("config").definedBy("..config..")

        .whereLayer("ports").mayNotBeAccessedByAnyLayer()
        .whereLayer("application").mayOnlyBeAccessedByLayers("ports")
        .whereLayer("domain").mayOnlyBeAccessedByLayers("application", "ports", "config")

        .as("Hexagonale Architektur sicherstellen");

    then:
    rule.check(importedClasses)
  }
}
