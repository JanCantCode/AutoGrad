package tk.jandev;

import tk.jandev.data.DataSet;
import tk.jandev.fitting.fittable.impl.InputScaler;
import tk.jandev.fitting.optimizer.OptimizationConfig;
import tk.jandev.fitting.optimizer.gradDescent.GradientDescentConfig;
import tk.jandev.fitting.optimizer.gradDescent.SimpleGradientDescent;
import tk.jandev.function.Function;
import tk.jandev.function.impl.Variable;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Base Function that we want to reproduce from its data
        Function myLinearFunction = Helper.linear(3, "x");
        final Variable xVar = new Variable("x");
        final Set<Variable> vars = Set.of(xVar);

        // dataset of points from this function
        DataSet fakeData = Helper.createDataSet(myLinearFunction, -5, 5, 2, 0, vars);
        System.out.println("Created Fake Dataset of Size: " + fakeData.size());

        // function that is meant to approximate our base function
        InputScaler approximator = new InputScaler();
        GradientDescentConfig config = new GradientDescentConfig(0.01, OptimizationConfig.RANDOM_SUPPLIER);
        SimpleGradientDescent optimizer = new SimpleGradientDescent(approximator, config, fakeData);

        for (int iteration = 0; iteration < 1000; iteration++) {
            optimizer.iteration();
            System.out.println("Epoch: " + iteration + "; Params: " + optimizer.getCurrentParameters());
        }

    }


}