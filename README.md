# Xenon Reborn

[![Build Status](https://travis-ci.org/fxrobin/XenonReborn.svg?branch=master)](https://travis-ci.org/fxrobin/XenonReborn)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/b8ebef973ed84f11966ddea4d17b5b98)](https://www.codacy.com/manual/fxrobin/XenonReborn/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=fxrobin/XenonReborn&amp;utm_campaign=Badge_Grade)
[![CodeFactor](https://www.codefactor.io/repository/github/fxrobin/xenonreborn/badge)](https://www.codefactor.io/repository/github/fxrobin/xenonreborn)

The main goal of this project is to offer a basic framework to implement an old school shoot-them-up game. 
Rookies can practice algorithm and basic Java programming to acquire development skills.

This framework is based on LibGDX and runs only on a desktop computer (Java SE 8) until now.

"XenonReborn" is a classic gradle generated by the LibGdx config tool.

Here is how the v0.1.8 looks like (Youtube) (just click on the image to launch the video)

[![Xenon_Reborn_Capture v0.1.8](http://img.youtube.com/vi/ki39sbk4VKc/0.jpg)](https://youtu.be/ki39sbk4VKc)

## How to build and run

Clone (or download) the project :

```bash
$ git clone https://github.com/fxrobin/xenon-reborn.git
```

Then enter the directory, then type :

- on linux `$ ./gradlew run`
- on windows `> gradlew run`

## How to generate de JAR distribution

- In a terminal : `$ ./gradlew dist`

Then open the browse the file system from the project root directory to "desktop/build/lib"

The generated jar name is "xenon-reborn-v.XX.XX.XX.jar". You can run it directly (after setting the executable bit on Linux with running `$ chmod +x xenon-reborn-v.XX.XX.XX.jar` in a terminal.

This JAR file includes the game and all its dependencies.

## IDE Integration

### Eclipse

- Import project as a gradle project. Let everything as default and click finish.
- Then open the "desktop" project in the project explorer.
- Open the "src" folder, then the "fr.fxjavadevblog.xr" package
- Right-click on the "Launcher.java" class, then "Run as Java Application".

### IntelliJ

- Import project as a gradle project. Let everything as default and click finish.
- Then open the "desktop" project in the project explorer.
- Open the "src" folder, then the "fr.fxjavadevblog.xr" package
- Right-click on the "Launcher.java" class, then "Run Launcher.main()".

## Technical information

- MVC paradigm
- Screen management and fade-in / fade-out transition (coded from scratch)
- Central assets management and loading, through enum
- Smooth random background scrolling on the menu screen
- Object states and polymorphism on every stage of the software design
- Sprite collision oded from scratch based on bounding circles
- Music playing an old format (Amiga and Atari-ST Mod format) which is very tiny compared to MP3
- Parallax scrolling on the game screen
- Java 8 lambdas and method references.
- etc.

## Code reviews

- 0% issues in Codacy.
- All files rated A in Codefactor.
- Average methods per class : 10
- Average LoC per class : 4
- Lines of code : 3813
- Nb of classes : 66 (only)
