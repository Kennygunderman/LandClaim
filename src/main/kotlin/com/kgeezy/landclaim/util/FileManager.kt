package com.kgeezy.landclaim.util

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

private const val YML_EXT = ".yml"
private const val CLAIMS_YML = "claims$YML_EXT"

class FileManager private constructor(private val pluginFolder: File) {
    companion object {
        private var instance: FileManager? = null

        fun initialize(pluginFolder: File) {
            instance =
                FileManager(pluginFolder)
        }

        fun getInstance(): FileManager {
            return if (instance == null) {
                throw Exception("File Manager must be initialized with the Plugin Folder!")
            } else {
                instance!!
            }
        }
    }

    fun yml(): File = File("$pluginFolder/$CLAIMS_YML")
    fun configFromYml(ymlFile: File): YamlConfiguration = YamlConfiguration.loadConfiguration(ymlFile)
}