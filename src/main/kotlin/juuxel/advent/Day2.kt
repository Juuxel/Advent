package juuxel.advent

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("day2.txt")).joinToString(separator = "")
    val mem = input.split(',').map { it.toInt() }.toIntArray()

    part1(mem.copyOf())
    part2(mem) // Part 2 is mem-safe
}

private fun part1(mem: IntArray) {
    println(
        "Part 1: " + run(mem) {
            it[1] = 12
            it[2] = 2
        }
    )
}

private fun part2(mem: IntArray) {
    for (noun in 0 until mem.size) {
        for (verb in 0 until mem.size) {
            val result = run(mem.copyOf()) {
                it[1] = noun
                it[2] = verb
            }

            if (result == 19690720) {
                println("Part 2: ${100 * noun + verb}")
                break
            }
        }
    }
}

private inline fun run(mem: IntArray, setup: (mem: IntArray) -> Unit): Int {
    setup(mem)
    val reader = MemReader(mem)
    while (true) {
        reader.nextOpcode().run(mem) ?: return mem[0]
    }
}

private class MemReader(private val mem: IntArray) {
    private var pos: Int = 0

    fun nextOpcode(): Intcode {
        val result = when (val opcode = mem[pos]) {
            1 -> Intcode.Add(mem[pos + 1], mem[pos + 2], mem[pos + 3])
            2 -> Intcode.Multiply(mem[pos + 1], mem[pos + 2], mem[pos + 3])
            99 -> Intcode.Halt
            else -> Intcode.Unknown(opcode)
        }

        pos += 4
        return result
    }
}

private sealed class Intcode {
    data class Add(val a: Int, val b: Int, val out: Int) : Intcode()
    data class Multiply(val a: Int, val b: Int, val out: Int) : Intcode()
    object Halt : Intcode()
    data class Unknown(val opcode: Int) : Intcode()

    fun run(mem: IntArray): Unit? =
        when (this) {
            is Add -> mem[out] = mem[a] + mem[b]
            is Multiply -> mem[out] = mem[a] * mem[b]
            else -> null
        }
}
