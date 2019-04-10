package de.jkrech.mediamanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.SpringApplication

@SpringBootApplication
//@EnableJpaRepositories
//@EnableCaching
open class MediamanagerApplication

fun main(args: Array<String>) {
	SpringApplication.run(MediamanagerApplication::class.java, *args)
}
