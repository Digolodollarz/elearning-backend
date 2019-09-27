package tech.diggle.apps.elearning

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ELearningApplication

fun main(args: Array<String>) {
    SpringApplication.run(ELearningApplication::class.java, *args)
}
