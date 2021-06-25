[![CI](https://github.com/jacq42/media-manager/actions/workflows/main.yml/badge.svg)](https://github.com/jacq42/media-manager/actions/workflows/main?query=branch%3Amaster++)
[![Nightly](https://github.com/jacq42/media-manager/actions/workflows/nightly.yml/badge.svg)](https://github.com/jacq42/media-manager/actions/workflows/nightly?query=branch%3Amaster++)

# Media Manager

* build: gradle clean asciidoctor
* run with debug: gradle bootRun --debug-jvm
* pitest: gradle pitest
* archUnit: gradle clean architectureTest

## Nützliche Links:

100daysOfCode: 
* https://www.100daysofcode.com/
* https://github.com/kallaway/100-days-of-code

AsciiDoc:
* https://asciidoctor.org/docs/asciidoctor-pdf/
* Gradle Plugin: https://asciidoctor.org/docs/asciidoctor-gradle-plugin/
* PDF: https://github.com/asciidoctor/asciidoctor-gradle-examples/tree/master/asciidoc-to-pdf-example

Kotlin:
* https://kotlinlang.org/docs/reference/
* https://try.kotlinlang.org/

Axon:
* https://docs.axoniq.io/reference-guide/

Hexagonal architecture:
* https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/

BDD Reports / Testing
* http://spockframework.org/
* https://github.com/renatoathaydes/spock-reports

Mutation Testing
* http://pitest.org/
* https://github.com/hcoles/pitest/

## Issues

Kotlin und Groovy vertragen sich nicht so wirklich in einem Eclipse Projekt
- die Sourcen von Kotlin landen nicht im bin Folder
- Groovy kann sie dann nicht finden
- mögliche Lösung: add kotlin nature erneut ausführen (?)
