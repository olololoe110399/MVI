package com.sun.demo.util

fun main() {
    methodA(1)

    methodB(methodA(2))

    methodC(methodB(methodA(3)))

    methodA(methodC(methodB(methodA(4))))

    methodB(methodA(methodC(methodB(methodA(5)))))

    methodC(methodB(methodA(methodC(methodB(methodA(6))))))

    methodA(7)
            .let(::methodB)
            .let(::methodC)
            .let(::methodA)
            .let(::methodB)
            .let(::methodC)
}

fun methodA(int: Int): String = int.toString()

fun methodB(str: String): Float = str.toFloat()

fun methodC(float: Float): Int = float.toInt()
