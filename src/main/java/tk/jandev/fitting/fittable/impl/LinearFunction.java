package tk.jandev.fitting.fittable.impl;

import tk.jandev.fitting.fittable.Fittable2D;
import tk.jandev.function.Function;
import tk.jandev.function.impl.Product;
import tk.jandev.function.impl.Sum;
import tk.jandev.function.impl.Variable;

import java.util.Set;

/**
 * Represents a linear Function in the form of f(x) = m * x + n
 */
public class LinearFunction implements Fittable2D {
    private final Function function;

    private final Variable m;
    private final Variable n;

    private final Variable x;

    private final Set<Variable> fittableParams;
    private final Set<Variable> inputParams;

    public LinearFunction() {
        this.m = new Variable("m");
        this.n = new Variable("n");

        this.x = new Variable("x");

        this.fittableParams = Set.of(m, n);
        this.inputParams = Set.of(x);

        this.function = new Sum(
                new Product(
                        m,
                        x
                ),
                n
        );
    }
    @Override
    public Set<Variable> getFittableParams() {
        return this.fittableParams;
    }

    @Override
    public Set<Variable> getInputParameters() {
        return this.inputParams;
    }

    @Override
    public Function getFunction() {
        return this.function;
    }
}
