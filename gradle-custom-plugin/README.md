# Custom Gradle Plugin

This project contains a custom Gradle plugin that provides a configurable task.

## YouTube Video
[![Custom Gradle Plugin on Reza Devs](https://img.youtube.com/vi/RmKeWAoYGJU/0.jpg)](https://youtu.be/RmKeWAoYGJU)

## Plugin Configuration

The plugin can be configured using the `myConfig` extension. The configuration includes a `message` property that can be set in the build script.

### Default Configuration

By default, the `message` property is set to:

```
This is my default message
```

### Custom Configuration

You can override the default configuration in your `build.gradle.kts` file:

```kotlin
import me.moallemi.myplugin.MyPluginConfig

plugins {
  id("me.moallemi.my-plugin")
}

configure<MyPluginConfig> {
  message.set("Hi from main script")
}
```

## Tasks

The plugin adds a custom task named `myTask` to your project. This task prints a message to the console.

### Running the Task

To run the task, use the following command:

```sh
./gradlew myTask
```

### Task Output

The task will print the configured message to the console. For example, with the default configuration, the output will be:

```
This is my default message
```

With the custom configuration, the output will be:

```
Hi from main script
```