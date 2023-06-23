cmake_minimum_required(VERSION 3.25)
project(Esqueletos C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(Esqueletos
        Imagenes.c
        Imagenes.h
        main.c)
