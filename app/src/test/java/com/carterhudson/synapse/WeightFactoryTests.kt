package com.carterhudson.synapse

import com.carterhudson.synapse.util.WeightFactory
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test


class WeightFactoryTests {
    @Before
    fun setup() {

    }

    @Test
    fun randomWeightTest() {
        (0 until 9).map {
            val rand = WeightFactory.getNormal(Math.pow(3.0, -.5).toFloat())
            System.out.println(rand)
            MatcherAssert.assertThat(rand >= -1f, `is`(true))
            MatcherAssert.assertThat(rand <= 1f, `is`(true))
        }
    }
}