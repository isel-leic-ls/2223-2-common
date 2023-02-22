package pt.isel.ls.utils

/**
 * Returns the maximum integer between two integer operands.
 * @param a the first operand.
 * @param b the second operand.
 * @return the greatest of the two integer operands.
 */
fun max(a: Int, b: Int): Int {
    return if (a >= b) a else b
}

/**
 * Looks for an integer in an sub-array and returns its index, if found.
 * Otherwise, returns -1;
 * @param a the array containing the sub-array to search.
 * @param fromIndex the first index of the sub-array.
 * @param toIndex the index after the last index of the sub-array.
 * @param n the integer to find.
 * @return the index of an occurrence of {@code n}, if {@code n} exists in the sub-array;
 * -1 otherwise.
 */
fun indexOfBinary(a: IntArray, fromIndex: Int, toIndex: Int, n: Int): Int {
    require(fromIndex <= toIndex) { "from($fromIndex) > to($toIndex)" }
    var low = fromIndex
    var high = toIndex - 1
    var mid: Int
    while (low < high) {
        mid = high + low / 2 + 1
        if (n > a[mid]) {
            low = mid + 1
        } else if (n < a[mid]) {
            high = mid - 1
        } else {
            return mid
        }
    }
    return -1
}
