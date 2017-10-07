package wexplorer.domain

import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

class Directory (
    private val path: Path
) {
    private val logger = LoggerFactory.getLogger(Directory::class.java)
    
    constructor(path: String): this(Paths.get(path))

    fun isEnd(): Boolean
            = Files.list(this.path)
            .allMatch { Files.isRegularFile(it) || Thumbnail.isThumbnailDirectory(it) }

    fun files(): List<FileItem> {
        return this.list { Files.isRegularFile(it) }
    }
    
    fun directories(): List<FileItem> {
        return this.list { Files.isDirectory(it) }
    }
    
    fun getPathString() = this.path.toString().replace("\\", "/")
    
    private fun list(filter: (Path) -> Boolean): List<FileItem> {
        return Files.list(this.path)
                .filter(filter)
                .map {
                    val name = it.fileName.toString()
                    val path = it.toString().replace("\\", "/")
                    FileItem(name, path)
                }
                .sorted()
                .peek { logger.debug("list() peek it={}", it) }
                .collect(Collectors.toList())
    }

    fun getNextPreviousImagePaths(filePath: String): NextPreviousImagePaths {
        val fileItems = this.files()
        val index = fileItems.indexOfFirst { it.path == filePath }
        
        val previousImagePath: String? = if (0 <= index - 1) {
            fileItems[index - 1].path
        } else {
            null
        }
        val nextImagePath: String? = if (index + 1 < fileItems.size) {
            fileItems[index + 1].path
        } else {
            null
        }
        
        return NextPreviousImagePaths(previousImagePath, nextImagePath)
    }
}