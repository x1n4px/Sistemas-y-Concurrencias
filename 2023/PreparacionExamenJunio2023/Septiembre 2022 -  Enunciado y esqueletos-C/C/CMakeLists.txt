cmake_minimum_required(VERSION 3.25)
project(C C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(C
        Principal.c
        Tren.c
        Tren.h)
