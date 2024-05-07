cmake_minimum_required(VERSION 3.25)
project(ExSept2014C C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(ExSept2014C
        principal.c
        Sistema.c
        Sistema.h)
