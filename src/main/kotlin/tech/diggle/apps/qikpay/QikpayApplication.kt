package tech.diggle.apps.qikpay

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class QikpayApplication

fun main(args: Array<String>) {
    SpringApplication.run(QikpayApplication::class.java, *args)
}
