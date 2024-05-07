cmake_minimum_required(VERSION 3.25)
project(ExJun2017C C)

set(CMAKE_C_STANDARD 11)

include_directories(.)
include_directories(Junio2017EsqC)

add_executable(ExJun2017C
        Junio2017EsqC/Lista.c
        Junio2017EsqC/Lista.h
        Junio2017EsqC/Principal.c)
