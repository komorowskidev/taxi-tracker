package pl.komorowskidev.taxitracker.testutils

private const val DOCKER_IMAGES_FILE = "/dockerimages/Dockerfile"

object DockerImageNameMap {
    private val images = mutableMapOf<String, String>()

    fun getFullImageName(name: String): String {
        if (images.isEmpty()) {
            readFileAsLinesUsingGetResourceAsStream().forEach { readLine(it) }
        }
        return images[name] ?: throw RuntimeException("Image not found: $name")
    }

    private fun readFileAsLinesUsingGetResourceAsStream() =
        this::class.java
            .getResourceAsStream(DOCKER_IMAGES_FILE)
            ?.bufferedReader()
            ?.readLines() ?: throw RuntimeException("File not found: $DOCKER_IMAGES_FILE")

    private fun readLine(line: String) {
        if (line.startsWith("FROM ")) {
            val imageName = line.trim().replace("FROM ", "")
            val key =
                imageName
                    .split(":")
                    .first()
                    .split("/")
                    .first()
            images[key] = imageName
        }
    }
}
