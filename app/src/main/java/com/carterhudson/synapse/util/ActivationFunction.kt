package com.carterhudson.synapse.util


enum class ActivationFunction(private val function: (input: Float) -> Float) {
    SIGMOID({ 1 / (1 + Math.exp(-it.toDouble()).toFloat()) }),
    BINARY_STEP({ throw NotImplementedError() }),
    IDENTITY({ throw NotImplementedError() }),
    TAN_H({ throw NotImplementedError() }),
    ARC_TAN({ throw NotImplementedError() }),
    SOFTSIGN({ throw NotImplementedError() });

    fun accept(input: Float): Float = function(input)
}