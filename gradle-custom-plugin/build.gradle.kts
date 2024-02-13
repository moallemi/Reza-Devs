import me.moallemi.myplugin.MyPluginConfig

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false

  id("me.moallemi.my-plugin")
}

configure<MyPluginConfig>() {
  message = "Hi from main script"
}