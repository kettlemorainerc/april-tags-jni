## Clone

To clone the repository you'll also need to initialize the sub-modules.
Given you haven't already cloned you can do this in one line by using
```shell
git clone --recurse-submodules https://github.com/kettlemorainerc/april-tags-jni.git
```

If you already have it pulled locally, or you want to update the sub-modules, you may need to run 
```shell
git submodule update --init --recursive
```

## Requirements

In order to build this library you will need to make sure the following items are available locally

- Some C++ 20 SDK
- CMake
- JDK version (at least) 11