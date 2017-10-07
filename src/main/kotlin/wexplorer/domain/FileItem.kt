package wexplorer.domain

data class FileItem (
    val name: String,
    val path: String
): Comparable<FileItem> {
    
    override fun compareTo(other: FileItem): Int {
        return this.name.compareTo(other.name)
    }
}