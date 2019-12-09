package juuxel.advent

import java.nio.file.Files
import java.nio.file.Paths

object Day1 {
    fun fuel(mass: Int) = mass / 3 - 2

    tailrec fun recursiveFuel(mass: Int, counter: Int = 0): Int {
        val fuel = fuel(mass)
        val fuelOfFuel =  fuel(fuel)
        return if (fuelOfFuel <= 0) counter + fuel
            else recursiveFuel(fuel, counter = counter + fuel)
    }
}

fun day1Part1(input: List<String>) =
    input.map { Day1.fuel(it.toInt()) }.sum()

fun day1Part2(input: List<String>) =
    input.map { Day1.recursiveFuel(it.toInt()) }.sum()

fun main() {
    println(day1Part1(Files.readAllLines(Paths.get("day1.txt"))))
    println(day1Part2(Files.readAllLines(Paths.get("day1.txt"))))
}
