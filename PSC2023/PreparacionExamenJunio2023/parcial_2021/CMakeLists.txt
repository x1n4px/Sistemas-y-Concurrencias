cmake_minimum_required(VERSION 3.25)
project(parcial_2021 C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(parcial_2021
        driver.c
        nfv.c
        nfv.h)
