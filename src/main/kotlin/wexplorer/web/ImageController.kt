package wexplorer.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import wexplorer.domain.Directory
import java.nio.file.Paths

@Controller
class ImageController {
    
    @GetMapping("/image")
    fun image(@RequestParam filePath: String, model: Model): String {
        val currentDirectory = Directory(Paths.get(filePath).parent)
        val nextPreviousImagePaths = currentDirectory.getNextPreviousImagePaths(filePath)
        
        model.addAttribute("filePath", filePath)
        model.addAttribute("currentDirectoryPath", currentDirectory.getPathString())
        model.addAttribute("nextPreviousImagePaths", nextPreviousImagePaths)
        
        return "image"
    }
}
