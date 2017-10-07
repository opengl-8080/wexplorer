package wexplorer.domain

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import java.nio.file.Path
import java.nio.file.Paths

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
class Explorer {
    private val logger = LoggerFactory.getLogger(Explorer::class.java)
    
    companion object {
        val IMAGES_DIRECTORY = System.getenv("WEXPLORER_IMAGES_DIR")!!
        val MOVIES_DIRECTORY = System.getenv("WEXPLORER_MOVIES_DIR")!!
    }
    
    fun changeDirectory(directory: String): Directory {
        val currentDirectory: Path = Paths.get(directory)
        logger.debug("change directory to '{}'.", currentDirectory)
        return Directory(currentDirectory)
    }
}