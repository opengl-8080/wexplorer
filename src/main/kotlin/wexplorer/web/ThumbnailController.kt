package wexplorer.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wexplorer.domain.Thumbnail
import java.nio.file.Files
import javax.servlet.http.HttpServletResponse

@RestController
class ThumbnailController {
    
    @GetMapping("/thumbnail")
    fun get(@RequestParam("filePath") filePath: String, response: HttpServletResponse) {
        val thumbnail = Thumbnail.path(filePath)
        
        response.outputStream.use { os ->
            Files.copy(thumbnail, os)
        }

        response.flushBuffer()
    }
}