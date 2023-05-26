cmake_minimum_required(VERSION 3.25)
project(ExSept2019C C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(ExSept2019C
        Componentes.c
        Componentes.h
        Driver.c)
