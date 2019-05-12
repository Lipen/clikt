package com.github.ajalt.clikt.parameters.types

import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.testing.TestCommand
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.tables.row
import org.junit.Test

class FloatTest {
    @Test
    fun `float option`() = forall(
            row("", null),
            row("--xx=4.0", 4f),
            row("-x5.5", 5.5f)) { argv, expected ->
        class C : TestCommand() {
            val x by option("-x", "--xx").float()
            override fun run_() {
                x shouldBe expected
            }
        }

        C().parse(argv)
    }

    @Test
    fun `float option error`() {
        class C : TestCommand(called = false) {
            val foo by option().float()
        }

        shouldThrow<BadParameterValue> { C().parse("--foo bar") }
                .message shouldBe "Invalid value for \"--foo\": bar is not a valid floating point value"
    }

    @Test
    fun `float option with default`() = forall(
            row("", -1f),
            row("--xx=4.0", 4f),
            row("-x5.5", 5.5f)) { argv, expected ->
        class C : TestCommand() {
            val x by option("-x", "--xx").float().default(-1f)
            override fun run_() {
                x shouldBe expected
            }
        }
        C().parse(argv)
    }

    @Test
    fun `float argument`() = forall(
            row("", null, emptyList()),
            row("1.1 2", 1.1f, listOf(2f)),
            row("1.1 2 3", 1.1f, listOf(2f, 3f))) { argv, ex, ey ->
        class C : TestCommand() {
            val x by argument().float().optional()
            val y by argument().float().multiple()
            override fun run_() {
                x shouldBe ex
                y shouldBe ey
            }
        }

        C().parse(argv)
    }
}
