# MachOParser

MachOParser is a Kotlin library to parse Mach-O files

[Mach-O](https://en.wikipedia.org/wiki/Mach-O) is a file format for executables, shared libraries and related binaries.
This library reads the Mach-O file and provides access to its metadata. The CPU Type metadata is supported.

## Installation

Gradle:
``` gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.mobile:machoparser")
}
```

## Usage

``` kotlin
let file = File(...)
if (MachOParser.parseCPUTypes(file.inputStream()).contains(X86_64)) {
    // handle X86_64
}
```
