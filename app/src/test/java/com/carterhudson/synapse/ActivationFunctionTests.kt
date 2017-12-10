package com.carterhudson.synapse

import com.carterhudson.synapse.util.ActivationFunction
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Test


class ActivationFunctionTests {
    @Test
    fun SigmoidTest() = MatcherAssert.assertThat(ActivationFunction.SIGMOID.accept(0f), `is`(.5f))
}