package tk.jandev.fitting.fittable;

import tk.jandev.data.DataPoint;
import tk.jandev.data.DataSet;
import tk.jandev.function.Function;
import tk.jandev.function.impl.*;

import java.util.Arrays;
import java.util.Set;

/**
 * Represents a parameterized Function that maps an n-dimensional input to a 1-dimensional output
 * TODO find out why x is not getting substituted all the time
 */
public interface Fittable2D {
    /**
     *
     * @return all fittable paramters of this function
     */
    Set<Variable> getFittableParams();

    /**
     *
     * @return all input parameters of this function
     */
    Set<Variable> getInputParameters();

    /**
     *
     * @return the underlying Function of this parameterized function, with all parameters taken from `getFittableParams()`
     */
    Function getFunction();

    /** TODO replace all usages of variable sets with some ordered set for deterministic ordering!! important!!
     *
     * @param dataSet the data set to compute the error function in respect to
     * @return a Function that takes as input the entire dataset and as output returns the "error" of this function in respect to the entire dataset.
     */
    default Function getSquaredErrorFunction(DataSet dataSet) {
        Function predictor = getFunction();
        Function[] squaredErrorSums = new Function[dataSet.size()];

        for (int i = 0; i < squaredErrorSums.length; i++) {
            DataPoint point = dataSet.get(i);

            Constant expectedValue = new Constant(point.output()[0]);
            Function difference = new Difference(predictor, expectedValue);

            if (this.getInputParameters().size() != point.input().length) {
                throw new AssertionError("Function Input Parameter Count was not equal to DataPoint input parameter count: POINT:" + point.input().length + " FUNCTION: " + this.getInputParameters().size());
            }

            int varIndex = 0;
            for (Variable var : this.getInputParameters()) {
                difference  = difference.substituteVariableForConstant(var, new Constant(point.input()[varIndex])); // replace the input variable with the x-value of the datapoint. it's important to do this here and not when they have all been combined into a MultiSum
                varIndex++;
            }

            Exponent squaredDiff = new Exponent(difference, 2);

            squaredErrorSums[i] = squaredDiff;
        }

        return new MultiSum(squaredErrorSums);
    }
}
