package com.github.ajalt.clikt.parameters.types

import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.parameters.arguments.RawArgument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.RawOption
import com.github.ajalt.clikt.parameters.options.convert
import kotlin.jvm.JvmOverloads

internal fun valueToInt(it: String): Int {
    return it.toIntOrNull() ?: throw BadParameterValue("$it is not a valid integer")
}

/** Convert the argument values to an `Int` */
fun RawArgument.int() = convert { valueToInt(it) }

/** Convert the option values to an `Int` */
@JvmOverloads
fun RawOption.int(metavar: String = "INT") = convert(metavar) { valueToInt(it) }
