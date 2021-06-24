package de.jkrech.mediamanager

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class MediamanagerApplicationSpec extends Specification {

	def "context loads"() {
        expect: "true"
        true
	}
}
