package wexplorer.domain

import org.slf4j.LoggerFactory
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.plugins.jpeg.JPEGImageWriteParam

object Thumbnail {
    private val logger = LoggerFactory.getLogger(Thumbnail::class.java)
    
    private val DIRECTORY_NAME = "thumbnail"

    fun isThumbnailDirectory(path: Path): Boolean
            = path.fileName.toString() == DIRECTORY_NAME

    fun path(originalImagePathString: String): Path {
        val originalImagePath = Paths.get(originalImagePathString)
        val thumbnailPath = this.toThumbnailPath(originalImagePath)
        
        if (Files.notExists(thumbnailPath)) {
            this.createThumbnail(originalImagePath, thumbnailPath)
        }
        
        return thumbnailPath
    }
    
    private fun toThumbnailPath(originalImagePath: Path): Path {
        val thumbnailDirectory = originalImagePath.parent.resolve(DIRECTORY_NAME)
        return thumbnailDirectory.resolve(originalImagePath.fileName)
    }

    private fun createThumbnail(originalImagePath: Path, thumbnailPath: Path) {
        synchronized(Thumbnail::class) {
            if (Files.notExists(thumbnailPath.parent)) {
                Files.createDirectories(thumbnailPath.parent)
            }
        }

        Files.newInputStream(originalImagePath).use { input ->
            logger.debug("crating thumbnail file... (originalImagePath={})", originalImagePath)
            // compress
            val originalImage = ImageIO.read(input)

            val width = 300
            val rate: Double = width.toDouble() / originalImage.width
            val height: Int = (originalImage.height * rate).toInt()
            val thumbnailImage = BufferedImage(width, height, originalImage.type)

            val graphics = thumbnailImage.createGraphics()
            graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
            graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
            graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
            graphics.drawImage(originalImage, 0, 0, width, height, null)

            // write thumbnail
            val param = JPEGImageWriteParam(Locale.getDefault())
            param.compressionMode = ImageWriteParam.MODE_EXPLICIT
            param.compressionQuality = 0.8f

            val writer = ImageIO.getImageWritersByFormatName("jpg").next()
            writer.output = ImageIO.createImageOutputStream(thumbnailPath.toFile())
            writer.write(null, IIOImage(thumbnailImage, null, null), param)
            writer.dispose()
        }
    }
}
