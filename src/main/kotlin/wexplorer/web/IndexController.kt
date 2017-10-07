package wexplorer.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import wexplorer.domain.Explorer

@Controller
class IndexController {
    
    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("imagesDirectoryPath", Explorer.IMAGES_DIRECTORY)
        model.addAttribute("moviesDirectoryPath", Explorer.MOVIES_DIRECTORY)
        
        return "index"
    }
    
}
