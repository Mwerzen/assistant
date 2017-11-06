package com.mikewerzen.automation.assistant.main

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = arrayOf("com.mikewerzen"))
class Application

fun main(args: Array<String>) {
	SpringApplication.run(Application::class.java, *args)
}
