package org.factotum

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main

class Hello : CliktCommand() {
    override fun run() {
        echo("Hello World!")
    }
}

fun main(args: Array<String>) = Hello().main(args)