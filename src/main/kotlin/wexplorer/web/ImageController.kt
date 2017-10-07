package wexplorer.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ImageController {
    
    @GetMapping("/image")
    fun image(@RequestParam filePath: String, model: Model): String {
        model.addAttribute("filePath", filePath)
        return "image"
    }
}
