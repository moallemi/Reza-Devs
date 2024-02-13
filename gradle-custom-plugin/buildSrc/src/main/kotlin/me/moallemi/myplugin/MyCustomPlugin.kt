package me.moallemi.myplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create

interface MyPluginConfig {
  val message: Property<String>
}

class MyCustomPlugin : Plugin<Project> {

  override fun apply(project: Project) {

    val extension = project.extensions.create<MyPluginConfig>("myConfig")
    extension.message.convention("This is my default message")

    project.task("myTask") {

      println("This is body")

      doLast {
        println(extension.message.get())
      }
    }
  }
}