package de.jkrech.mediamanager

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
//@EnableJpaRepositories
//@EnableCaching
open class MediamanagerApplication

fun main(args: Array<String>) {
	SpringApplication.run(MediamanagerApplication::class.java, *args)
}
