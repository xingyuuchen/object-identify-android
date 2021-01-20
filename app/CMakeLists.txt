project(oi_native)

# custom your boost location here
set(boost_location /Users/cxy/Downloads/boost_1_66_0)
#set(boost_location /usr/local/include)

cmake_minimum_required(VERSION 3.4.1)


#aux_source_directory(src/main/cpp/network src_files)
file(GLOB_RECURSE src_files "*.cc")

find_library(log-lib log)

message(${src_files})


# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.
add_library(${PROJECT_NAME} SHARED ${src_files})
target_link_libraries(${PROJECT_NAME} ${log-lib})

# Specifies a path to native header files.
include_directories(src/main/cpp/include)
include_directories(${boost_location})

#link_directories(/usr/local/lib)
