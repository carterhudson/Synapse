package com.carterhudson.synapse

import com.carterhudson.synapse.util.ActivationFunction
import com.carterhudson.synapse.util.Matrix
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test


class MindTests {

    lateinit var mind: Mind
    private val m3 = Matrix(
            rowCount = 3,
            colCount = 3,
            elements = floatArrayOf(
                    1f, 2f, 3f,
                    4f, 5f, 6f,
                    7f, 8f, 9f
            )
    )

    @Before
    fun setup() {
        mind = Mind(3, 3, 1, 3, .3f, ActivationFunction.SIGMOID)
    }

    @Test
    fun weightInputHiddenTest() {
        var exeCount = 0
        mind.weightsInputHidden.elements.let {
            assertThat(it.size == mind.inputCount * mind.hiddenCount, `is`(true))
            exeCount++
            it
        }.map {
            println(it)
            assertThat(-1 <= it && it <= 1, `is`(true))
            exeCount++
        }

        assertThat(exeCount, `is`(mind.weightsInputHidden.elements.size + 1))
    }

    @Test
    fun weightHiddenOutputTest() {
        var exeCount = 0
        mind.weightsHiddenOutput.elements.let {
            assertThat(it.size == mind.hiddenCount * mind.outputCount, `is`(true))
            exeCount++
            it
        }.map {
            println(it)
            assertThat(-1 <= it && it <= 1, `is`(true))
            exeCount++
        }

        assertThat(exeCount, `is`(mind.weightsHiddenOutput.elements.size + 1))
    }

    @Test
    fun ponderTest() {
        println(mind.ponder(m3.elements.toList()))
    }

    @Test
    fun learnTest() {
        println(mind.learn(m3.elements.toList(), m3.elements.toList()))
    }
}