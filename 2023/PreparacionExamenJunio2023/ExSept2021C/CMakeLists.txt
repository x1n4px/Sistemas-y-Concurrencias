cmake_minimum_required(VERSION 3.25)
project(ExSept2021C C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(ExSept2021C
        Driver.c
        GestionTren.c
        GestionTren.h)
