package tk.jandev;

import tk.jandev.data.DataSet;
import tk.jandev.fitting.fittable.impl.LinearFunction;
import tk.jandev.fitting.optimizer.IteratingOptimizer;
import tk.jandev.fitting.optimizer.gradDescent.GradientDescentConfig;
import tk.jandev.fitting.optimizer.gradDescent.GradientDescentOptimizer;
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
        DataSet fakeData = Helper.createDataSet(myLinearFunction, -5, 5, 20, 0, vars);
        System.out.println("Created Fake Dataset of Size: " + fakeData.size() + ": " + fakeData);

        // function that is meant to approximate our base function
        LinearFunction approximator = new LinearFunction();
        GradientDescentConfig config = new GradientDescentConfig(0.01);
        GradientDescentOptimizer optimizer = new GradientDescentOptimizer(approximator, config, fakeData);

        for (int iteration = 0; iteration < 100; iteration++) {
            optimizer.iteration();
            System.out.println("Epoch: " + iteration + " Params: " + approximator.getFittableParams());
        }

    }


}