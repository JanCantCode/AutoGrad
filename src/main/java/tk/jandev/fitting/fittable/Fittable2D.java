package tk.jandev.fitting.fittable;

import tk.jandev.data.DataPoint;
import tk.jandev.data.DataSet;
import tk.jandev.function.Function;
import tk.jandev.function.impl.*;

import java.util.Set;

/**
 * Represents a parameterized Function that maps an n-dimensional input to a 1-dimensional output
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

    /**
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
            Difference difference = new Difference(predictor, expectedValue);
            Exponent squaredDiff = new Exponent(difference, 2);

            squaredErrorSums[i] = squaredDiff;
        }

        return new MultiSum(squaredErrorSums);
    }
}
