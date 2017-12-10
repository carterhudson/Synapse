package com.carterhudson.synapse

import com.carterhudson.synapse.util.Matrix
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test


class MatrixTests {
    private val m1 = Matrix(
            rowCount = 2,
            colCount = 3,
            elements = floatArrayOf(
                    1f, 2f, 3f,
                    4f, 5f, 6f
            )
    )

    private val m1_t = Matrix(
            rowCount = 3,
            colCount = 2,
            elements = floatArrayOf(
                    1f, 4f,
                    2f, 5f,
                    3f, 6f
            )
    )

    private val m2 = Matrix(
            rowCount = 3,
            colCount = 2,
            elements = floatArrayOf(
                    1f, 2f,
                    3f, 4f,
                    5f, 6f
            )
    )

    private val m2_t = Matrix(
            rowCount = 2,
            colCount = 3,
            elements = floatArrayOf(
                    1f, 3f, 5f,
                    2f, 4f, 6f
            )
    )

    private val m3 = Matrix(
            rowCount = 3,
            colCount = 3,
            elements = floatArrayOf(
                    1f, 2f, 3f,
                    4f, 5f, 6f,
                    7f, 8f, 9f
            )
    )

    private val m3_t = Matrix(
            rowCount = 3,
            colCount = 3,
            elements = floatArrayOf(
                    1f, 4f, 7f,
                    2f, 5f, 8f,
                    3f, 6f, 9f
            )
    )

    @Before
    fun setup() {
    }

    @Test
    fun getRowTest() {
        assertThat(m1.getRow(0), `is`(listOf(1f, 2f, 3f)))
        assertThat(m1.getRow(1), `is`(listOf(4f, 5f, 6f)))

        var err: Exception? = null
        try {
            m1.getRow(4)
        } catch (e: IndexOutOfBoundsException) {
            err = e
        }

        assertThat(err!!, instanceOf(IndexOutOfBoundsException::class.java))
    }

    @Test
    fun getRowsTest() {
        val rows = m1.getRows()
        assertThat(rows.size, `is`(2))
        assertThat(rows[0][0], `is`(1f))
        assertThat(rows[0][1], `is`(2f))
        assertThat(rows[0][2], `is`(3f))
        assertThat(rows[1][0], `is`(4f))
        assertThat(rows[1][1], `is`(5f))
        assertThat(rows[1][2], `is`(6f))
    }

    @Test
    fun getColTest() {
        assertThat(m1.getCol(0), `is`(listOf(1f, 4f)))
        assertThat(m1.getCol(1), `is`(listOf(2f, 5f)))
        assertThat(m1.getCol(2), `is`(listOf(3f, 6f)))

        var err: Exception? = null
        try {
            m1.getCol(3)
        } catch (e: IndexOutOfBoundsException) {
            err = e
        }

        assertThat(err!!, instanceOf(IndexOutOfBoundsException::class.java))
    }

    @Test
    fun getColsTest() {
        val cols = m1.getCols()
        assertThat(cols.size, `is`(3))
        assertThat(cols[0][0], `is`(1f))
        assertThat(cols[0][1], `is`(4f))
        assertThat(cols[1][0], `is`(2f))
        assertThat(cols[1][1], `is`(5f))
        assertThat(cols[2][0], `is`(3f))
        assertThat(cols[2][1], `is`(6f))
    }

    @Test
    fun dotProductTest() {
        val product = (m1 * m2)
        assertThat(product.getRow(0), `is`(listOf(22f, 28f)))
        assertThat(product.getRow(1), `is`(listOf(49f, 64f)))
        assertThat(product.getCol(0), `is`(listOf(22f, 49f)))
        assertThat(product.getCol(1), `is`(listOf(28f, 64f)))

        var err: Exception? = null
        try {
            m1 * m3
        } catch (e: IllegalStateException) {
            err = e
        }

        assertThat(err!!, instanceOf(IllegalStateException::class.java))
    }

    @Test
    fun minusTest() {
        var exe = 0
        val difference = m1 - m1
        difference.elements.map {
            assertThat(it, `is`(0f))
            exe++
        }

        assertThat(exe, `is`(difference.elements.size))
    }

    @Test
    fun transposeTest() {
        assertThat(m1.transpose(), `is`(m1_t))
        assertThat(m2.transpose(), `is`(m2_t))
        assertThat(m3.transpose(), `is`(m3_t))
    }
}