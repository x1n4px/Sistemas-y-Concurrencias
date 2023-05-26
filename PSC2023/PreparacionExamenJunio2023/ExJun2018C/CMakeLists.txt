cmake_minimum_required(VERSION 3.25)
project(ExJun2018C C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(ExJun2018C
        cifrado.c
        cifrado.h
        main.c)
