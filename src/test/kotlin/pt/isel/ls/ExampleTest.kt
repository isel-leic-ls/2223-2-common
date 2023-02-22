package pt.isel.ls

import java.io.FileInputStream
import java.io.FileNotFoundException
import kotlin.test.Test
import kotlin.test.assertEquals

class ExampleTest {
    @Test
    fun example() {
        // arrange | given
        val a = 1
        val b = 2

        // act | when
        val result = a + b

        // assert | then
        assertEquals(3, result)
    }

    @Test
    @Throws(FileNotFoundException::class)
    fun do_not_ignore_unexpected_exceptions_on_tests() {
        // test methods can have a non-empty `throws` exception list.
        FileInputStream("does-not-exist")
    }
}