package pt.isel.ls.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class IntsTests {
    @Test
    fun max_returns_greatest() {
        assertEquals(1, max(1, -2))
        assertEquals(1, max(-2, 1))
        assertEquals(-1, max(-1, -2))
        assertEquals(-1, max(-2, -1))
    }

    @Test
    fun indexOfBinary_returns_negative_if_not_found() {
        // Arrange
        val v = intArrayOf(1, 2, 3)

        // Act
        val ix: Int = indexOfBinary(v, 0, 3, 4)

        // Assert
        assertTrue(ix < 0)
    }

    @Test
    fun indexOfBinary_throws_IllegalArgumentException_if_indexes_are_not_valid() {
        assertFailsWith<IllegalArgumentException> {
            // Arrange
            val v = intArrayOf(1, 2, 3)

            // Act
            val ix: Int = indexOfBinary(v, 2, 1, 4)

            // Assert
            assertTrue(ix < 0)
        }
    }


    @Test
    fun indexOfBinary_right_bound_parameter_is_exclusive() {
        val v = intArrayOf(2, 2, 2)
        val ix: Int = indexOfBinary(v, 1, 1, 2)
        assertTrue(ix < 0)
    }
}