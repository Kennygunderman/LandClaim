package com.kgeezy.landclaim.manager

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

private const val YML_EXT = ".yml"
private const val CLAIMS_YML = "claims$YML_EXT"
private const val CONFIG_YML = "config$YML_EXT"

interface ConfigFile {
    fun configYml(): File
}

class FileManager private constructor(private val pluginFolder: File): ConfigFile {
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


    override fun configYml() = File("$pluginFolder/$CONFIG_YML")

    fun yml(): File = File("$pluginFolder/$CLAIMS_YML")
    fun configFromYml(ymlFile: File): YamlConfiguration = YamlConfiguration.loadConfiguration(ymlFile)
}