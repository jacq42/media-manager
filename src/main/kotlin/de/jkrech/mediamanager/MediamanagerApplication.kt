package de.jkrech.mediamanager

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
//@EnableCaching
class MediamanagerApplication

fun main(args: Array<String>) {
	SpringApplication.run(MediamanagerApplication::class.java, *args)
}
