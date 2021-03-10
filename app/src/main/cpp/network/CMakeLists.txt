project(oi_native)

cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

find_library(log-lib log)

# Specifies a path to native header files.
include_directories(.)
include_directories(http)
include_directories(jni)
include_directories(socket)
include_directories(utils)
include_directories(/usr/local/include)

file(GLOB SELF_TEMP_SRC_FILES RELATIVE ${PROJECT_SOURCE_DIR} *.cc)
list(APPEND SELF_SRC_FILES ${SELF_TEMP_SRC_FILES})

file(GLOB SELF_TEMP_SRC_FILES RELATIVE ${PROJECT_SOURCE_DIR} http/*.cc)
list(APPEND SELF_SRC_FILES ${SELF_TEMP_SRC_FILES})

file(GLOB SELF_TEMP_SRC_FILES RELATIVE ${PROJECT_SOURCE_DIR} jni/*.cc)
list(APPEND SELF_SRC_FILES ${SELF_TEMP_SRC_FILES})

file(GLOB SELF_TEMP_SRC_FILES RELATIVE ${PROJECT_SOURCE_DIR} socket/*.cc)
list(APPEND SELF_SRC_FILES ${SELF_TEMP_SRC_FILES})

file(GLOB SELF_TEMP_SRC_FILES RELATIVE ${PROJECT_SOURCE_DIR} utils/*.cc)
list(APPEND SELF_SRC_FILES ${SELF_TEMP_SRC_FILES})

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.
add_library(${PROJECT_NAME} SHARED ${SELF_SRC_FILES})
target_link_libraries(${PROJECT_NAME} ${log-lib})


#link_directories(/usr/local/lib)
