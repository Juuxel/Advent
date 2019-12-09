package juuxel.advent.util

/**
 * A dynamically sized int array.
 *
 * Used in challenges:
 * - Day 2
 */
class DynIntArray(private var array: IntArray = IntArray(1)) {
    constructor(size: Int) : this(IntArray(size))

    val size: Int get() = array.size

    fun resize(size: Int) {
        if (array.size <= size) {
            val newArray = IntArray(size)
            array.copyInto(newArray)
            array = newArray
        }
    }

    operator fun get(i: Int) = array[i]

    operator fun set(i: Int, value: Int) {
        if (array.lastIndex < i) {
            resize(i + 1)
        }

        array[i] = value
    }

    fun copy(): DynIntArray =
        DynIntArray(array.copyOf())
}

fun List<Int>.toDynIntArray(): DynIntArray {
    val result = DynIntArray(size)
    for ((i, value) in this.withIndex()) {
        result[i] = value
    }
    return result
}
