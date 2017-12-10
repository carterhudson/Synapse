package com.carterhudson.synapse.util

import java.util.*


class WeightFactory {
    companion object {
        private val random = Random()
        fun getNormal(scale: Float = 1f): Float = (random.nextFloat() * scale) - .2f
    }
}