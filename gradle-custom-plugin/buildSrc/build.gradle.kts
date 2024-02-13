plugins {
  `kotlin-dsl`
}

repositories {
  mavenCentral()
}

gradlePlugin {
  plugins {
    create("my-plugin") {
      id = "me.moallemi.my-plugin"
      implementationClass = "me.moallemi.myplugin.MyCustomPlugin"
    }
  }
}