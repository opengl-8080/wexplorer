package wexplorer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class WexplorerMain

fun main(args: Array<String>) {
    SpringApplication.run(WexplorerMain::class.java, *args)
}
