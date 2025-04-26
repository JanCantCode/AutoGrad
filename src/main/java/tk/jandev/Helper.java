package tk.jandev;

import tk.jandev.data.DataPoint;
import tk.jandev.data.DataSet;
import tk.jandev.function.Function;
import tk.jandev.function.VariableContext;
import tk.jandev.function.impl.Constant;
import tk.jandev.function.impl.Product;
import tk.jandev.function.impl.Variable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Helper {
    public static Product linear(double linearTerm, Variable x) {
        return new Product(new Constant(linearTerm), x);
    }

    public static Product linear(double linearTerm, String x) {
        return linear(linearTerm, new Variable(x));
    }

    public static Set<Variable> asVarSet(String... variableNames) {
        Set<Variable> variables = new HashSet<>();
        for (String variableName : variableNames) {
            variables.add(new Variable(variableName));
        }

        return variables;
    }

    public static Constant sumConstants(Constant[] constants) {
        double sum = 0;
        for (Constant constant : constants) {
            sum += constant.get();
        }

        return new Constant(sum);
    }

    public static DataSet createDataSet(Function originalFunction, double inputMin, double inputMax, int sampleCount, double randomNoise, Set<Variable> inputVars) {
        DataSet dataSet = new DataSet();

        for (int i = 0; i < sampleCount; i++) {
            VariableContext context = new VariableContext();

            for (Variable variable : inputVars) { // order of setting that values into the variables do not matter for noising
                context.introduce(variable);

                double xValue = Math.random() * (inputMax - inputMin) + inputMin;
                context.set(variable, xValue);
            }

            double output = originalFunction.apply(context) + Math.random() * randomNoise;
            DataPoint point = new DataPoint.SimpleDataPoint(context.array(), new double[]{output});

            dataSet.addData(point);
        }

        return dataSet;
    }
}
