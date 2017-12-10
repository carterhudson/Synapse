package com.carterhudson.synapse.util

import java.util.*

class Matrix constructor(val rowCount: Int,
                         val colCount: Int,
                         val elements: FloatArray = FloatArray(rowCount * colCount)) {

    fun getRow(rowNum: Int): List<Float> = (rowNum * colCount).let {
        elements.copyOfRange(
                it,
                it + colCount
        ).toList()
    }

    fun getRows(): List<List<Float>> = (0 until rowCount).map { getRow(it) }

    fun getCol(colNum: Int): List<Float> = getRows().map { it[colNum] }

    fun getCols(): List<List<Float>> = (0 until colCount).map { getCol(it) }

    fun transpose(): Matrix = Matrix(
            colCount,
            rowCount,
            this.getCols().asSequence().flatten().toList().toFloatArray()
    )

    operator fun times(rhs: Matrix): Matrix {
        val lhs = this
        if (lhs.rowCount != rhs.colCount) {
            throw IllegalStateException("LHS row count != RHS col count")
        }

        val product = Matrix(lhs.rowCount, rhs.colCount)
        var productIdx = 0

        (0 until lhs.rowCount).map { rIdx ->
            (0 until rhs.colCount).map { cIdx ->

                var sum = 0f
                val row = lhs.getRow(rIdx)
                val col = rhs.getCol(cIdx)

                (0 until row.size).map { eleIdx ->
                    sum += row[eleIdx] * col[eleIdx]
                    product.elements[productIdx] = sum
                }

                productIdx++
            }
        }

        return product
    }

    operator fun minus(rhs: Matrix): Matrix {
        if (this.rowCount != rhs.rowCount || this.colCount != rhs.colCount) {
            throw InputMismatchException("lhs and rhs must have same dimensions")
        }

        return (0 until this.elements.size)
                .map { elements[it] - rhs.elements[it] }
                .let {
                    Matrix(
                            rowCount,
                            colCount,
                            it.toFloatArray()
                    )
                }
    }

    operator fun plus(rhs: Matrix): Matrix {
        if (this.rowCount != rhs.rowCount || this.colCount != rhs.colCount) {
            throw InputMismatchException("lhs and rhs must have same dimensions")
        }

        return (0 until this.elements.size)
                .map { elements[it] + rhs.elements[it] }
                .let {
                    Matrix(
                            rowCount,
                            colCount,
                            it.toFloatArray()
                    )
                }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is Matrix) {
            return false
        }

        if (other === this) {
            return true
        }

        return Arrays.equals(this.elements, other.elements)
    }

    override fun hashCode(): Int = Arrays.hashCode(elements)
}