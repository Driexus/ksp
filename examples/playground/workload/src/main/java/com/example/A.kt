package com.example

import GreaterThan
import HELLO

fun main() {
    val hello = HELLO()
    println(hello.foo())

    val builder = AClassBuilder()
    builder
        .withA(1)
        .withB("foo")
        .withC(2.3)
        .withD(hello)
    val aClass : AClass = builder.build()
    println(aClass.foo())

    val test = GymDataQueryable(
        lat = GreaterThan(5.0)
    )
    //test.lat = GreaterThan(5.0)
}


