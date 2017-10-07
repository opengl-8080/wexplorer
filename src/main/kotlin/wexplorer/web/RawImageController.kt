package wexplorer.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Files
import java.nio.file.Paths
import javax.servlet.http.HttpServletResponse

@RestController
class RawImageController {
    
    @GetMapping("/raw-image")
    fun get(@RequestParam("filePath") filePath: String, response: HttpServletResponse) {
        response.outputStream.use { os ->
            Files.copy(Paths.get(filePath), os)
        }
        response.flushBuffer()
    }
}