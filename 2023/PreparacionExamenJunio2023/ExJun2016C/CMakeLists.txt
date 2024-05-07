cmake_minimum_required(VERSION 3.25)
project(ExJun2016C C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(ExJun2016C
        ListaJugadores.c
        ListaJugadores.h
        Principal.c)
