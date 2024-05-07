cmake_minimum_required(VERSION 3.25)
project(ExJun2013C C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(ExJun2013C
        main.c
        userList.c
        userList.h)
