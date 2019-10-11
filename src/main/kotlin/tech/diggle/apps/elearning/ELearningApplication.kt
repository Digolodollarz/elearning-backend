package tech.diggle.apps.elearning

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import tech.diggle.apps.elearning.uploads.StorageService
import javax.annotation.Resource

@SpringBootApplication
class ELearningApplication(@Resource val storageService: StorageService) : CommandLineRunner {
    override fun run(vararg p0: String?) {
        storageService.init()
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ELearningApplication::class.java, *args)
}
