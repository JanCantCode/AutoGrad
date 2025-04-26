package tk.jandev.function.impl;

import tk.jandev.function.Function;
import tk.jandev.Helper;

import java.util.Arrays;
import java.util.Set;

public class MultiSum implements Function {
    private final Function[] summands;

    public MultiSum(Function... summands) {
        this.summands = summands;
    }

    @Override
    public double apply(Set<Variable> variables) {
        return Arrays.stream(summands).mapToDouble(function -> function.apply(variables)).sum();
    }

    @Override
    public Function deriveWith(Variable variable) {
        return new MultiSum(Arrays.stream(summands).map(function -> function.deriveWith(variable)).toArray(Function[]::new));
    }

    @Override
    public Function findOptimizedNode() {
        Constant[] constantNodes = new Constant[summands.length];
        Function[] nonConstantNodes = new Function[summands.length];
        int constantCounter = 0;
        int nonConstantCounter = 0;


        for (int i = 0; i < summands.length; i++) {
            Function current = summands[i];

            summands[i] = current.findOptimizedNode();

            if (summands[i] instanceof Constant constant) {
                constantNodes[constantCounter] = constant;
                constantCounter++;
            } else {
                nonConstantNodes[nonConstantCounter] = summands[i];
                nonConstantCounter++;
            }
        }

        if (constantCounter <= 1) return this; // if we only found a singular, or no constant nodes at all, the multisum cannot easily be optimized.

        Function combinedConstantFunction = Helper.sumConstants(constantNodes);
        Function[] result = new Function[nonConstantCounter + 1];

        System.arraycopy(nonConstantNodes, 0, result, 0, nonConstantCounter);
        result[nonConstantCounter] = combinedConstantFunction;

        return new MultiSum(result);
    }

    @Override
    public Function substituteVariableForConstant(Variable variable, Constant constant) {
        Function[] newFunctions = new Function[this.summands.length];

        for (int i = 0; i < this.summands.length; i++) {
            Function summand = this.summands[i];
            if (summand instanceof Variable var) {
                if (var.equals(variable)) newFunctions[i] = constant;
            } else {
                newFunctions[i] = summand.substituteVariableForConstant(variable, constant);
            }
        }

        return new MultiSum(newFunctions);
    }
}
