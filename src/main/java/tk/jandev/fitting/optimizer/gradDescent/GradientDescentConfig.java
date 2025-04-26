package tk.jandev.fitting.optimizer.gradDescent;

import tk.jandev.fitting.optimizer.OptimizationConfig;
import tk.jandev.function.VariableContext;
import tk.jandev.function.impl.Variable;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public record GradientDescentConfig(double learningRate, Function<Set<Variable>, VariableContext> initialParamSupplier) implements OptimizationConfig {
}
