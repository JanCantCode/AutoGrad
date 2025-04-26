package tk.jandev.fitting.optimizer;

import tk.jandev.function.VariableContext;
import tk.jandev.function.impl.Variable;

import java.util.Set;
import java.util.function.Function;

public interface OptimizationConfig {
    /**
     * returns randomly generated initial parameters
     */
    Function<Set<Variable>, VariableContext> RANDOM_SUPPLIER = variables -> {
        VariableContext context = new VariableContext();
        for (Variable var : variables) {
            context.introduce(var);
            context.set(var, Math.random());
        }

        return context;
    };
}
