package com.jb.factorial

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FactorialApplication

fun main(args: Array<String>) {
	runApplication<FactorialApplication>(*args)
}
