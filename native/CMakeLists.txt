cmake_minimum_required(VERSION 3.23.3)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED True)

project(april_tags_2077 VERSION 1.0)

include("cmake/sources.txt")
add_library(${PROJECT_NAME} MODULE
    ${JNI_CPP_SOURCES}
)


add_subdirectory(native-deps/april-tags)
include_directories(native-deps native-deps/april-tags)

target_link_libraries(${PROJECT_NAME} PUBLIC
    apriltag
)

target_include_directories(${PROJECT_NAME} PUBLIC
    "native-deps"
    "jni"
    "build/jnigen-jni/jni-headers"
    "build/jnigen-jni/jni-headers/win32"
)
