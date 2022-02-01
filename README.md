# The Jungle Chronicles

The Jungle Chronicles is a side-scroller platformer game, where the player aims to collect as many 
coins as possible and avoid all traps waiting for him along the way. The game contains three levels
with progressing difficulty, built using the Tiled map editor. If desired, more levels can be built,
using the Tiled map editor and some simple layer naming and custom object property rules.
The game is made using the LibGDX framework / library.

## Requirements

This project uses Java 11, so make sure you have that installed, and available on the system $PATH.
If on Arch Linux, simply install the Java JDK using Pacman, and set Java 11 as the default version 
of Java using the following commands. This should also install any other dependencies for Java, such
as the runtime environment.
```bash
$ sudo pacman -S jdk11-openjdk
$ sudo archlinux-java set jdk11-openjdk
```

You can check the currently used version by running
```bash
$ archlinux-java status
```

If using any other distro, use the appropriate equivalent commands. As LibGDX supports many other
platforms, this should also work on Windows with the appropriate requirements, but is yet untested.
Just make sure that your graphics driver is up-to-date and supports OpenGL.

## Installation/Usage

Clone the repo.
```bash
$ git clone https://github.com/vilfa/jungle-chronicles
$ cd jungle-chronicles
```

You should then be able to simply run the project using the Gradle wrapper.
```bash
$ ./gradlew run
```

## License

No license.
