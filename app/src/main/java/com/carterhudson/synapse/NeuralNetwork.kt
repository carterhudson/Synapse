package com.carterhudson.synapse


interface NeuralNetwork {
    fun learn(inputs: List<Float>, targets: List<Float>): List<Float>
    fun ponder(inputs: List<Float>): List<Float>
}