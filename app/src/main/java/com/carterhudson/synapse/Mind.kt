package com.carterhudson.synapse

import com.carterhudson.synapse.util.ActivationFunction
import com.carterhudson.synapse.util.Matrix
import com.carterhudson.synapse.util.WeightFactory


data class Mind constructor(val inputCount: Int,
                            val hiddenCount: Int,
                            val hiddenLayerCount: Int,
                            val outputCount: Int,
                            val learningRate: Float,
                            private val activationFunction: ActivationFunction) : NeuralNetwork {

    var weightsInputHidden: Matrix = Matrix(
            inputCount,
            hiddenCount,
            (0 until inputCount * hiddenCount).map { WeightFactory.getNormal() }.toFloatArray()
    )
    var weightsHiddenOutput: Matrix = Matrix(
            hiddenCount,
            outputCount,
            (0 until hiddenCount * outputCount).map { WeightFactory.getNormal() }.toFloatArray()
    )

    /**
     * Neural Network Training Function -
     * Iterates over a list of data and finds errors by comparing to the target values.
     * Adjusts layer weights accordingly.
     */
    override fun learn(inputs: List<Float>, targets: List<Float>): List<Float> {
        val hiddenOutputs = (weightsInputHidden * Matrix(
                inputCount,
                hiddenCount,
                inputs.toFloatArray()
        )).elements.map { activationFunction.accept(it) }

        val finalOutputs = hiddenOutputs.let {
            (weightsHiddenOutput * Matrix(
                    hiddenCount,
                    outputCount,
                    it.toFloatArray()
            ))
        }.elements.map { activationFunction.accept(it) }

        val lhs = Matrix(inputCount, hiddenCount, targets.toFloatArray())
        val rhs = Matrix(inputCount, hiddenCount, finalOutputs.toFloatArray())
        val outputErrors = lhs - rhs
        val hiddenErrors = weightsHiddenOutput.transpose() * outputErrors

        weightsHiddenOutput += getUpdatedWeights(
                hiddenCount,
                outputCount,
                finalOutputs,
                outputErrors,
                hiddenOutputs
        )

        weightsInputHidden += getUpdatedWeights(
                inputCount,
                hiddenCount,
                hiddenOutputs,
                hiddenErrors,
                inputs
        )

        return outputErrors.elements.toList()
    }

    /**
     * Function accepts matrix sizes, a list of values, errors
     */
    private fun getUpdatedWeights(rowCount: Int,
                                  colCount: Int,
                                  values: List<Float>,
                                  errors: Matrix,
                                  layer: List<Float>): Matrix {
        return Matrix(
                rowCount,
                colCount,
                (Matrix(
                        rowCount,
                        colCount,
                        (0 until values.size).map { (1 - values[it]) * values[it] * errors.elements[it] }.toFloatArray()
                ).transpose() * Matrix(
                        rowCount,
                        colCount,
                        layer.toFloatArray()
                )).elements.map { learningRate * it }.toFloatArray()
        )
    }

    override fun ponder(inputs: List<Float>): List<Float> =
            (weightsInputHidden * Matrix(
                    inputCount,
                    hiddenCount,
                    inputs.toFloatArray()
            )).elements.map { activationFunction.accept(it) }.let {
                (weightsHiddenOutput * Matrix(
                        hiddenCount,
                        outputCount,
                        it.toFloatArray()
                ))
            }.elements.map { activationFunction.accept(it) }
}