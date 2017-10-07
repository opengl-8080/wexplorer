package wexplorer.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import wexplorer.domain.Explorer

@Controller
@RequestMapping("/explorer")
class ExplorerContoller {
    @Autowired
    lateinit var explorer: Explorer
    
    @GetMapping
    fun changeDirectory(@RequestParam("directory") directory: String, model: Model): String {
        val currentDirectory = this.explorer.changeDirectory(directory)
        model.addAttribute("currentDirectory", currentDirectory)
        
        return "explorer"
    }
}
