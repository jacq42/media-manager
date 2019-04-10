package de.jkrech.mediamanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MediamanagerApplication

fun main(args: Array<String>) {
	runApplication<MediamanagerApplication>(*args)
}
