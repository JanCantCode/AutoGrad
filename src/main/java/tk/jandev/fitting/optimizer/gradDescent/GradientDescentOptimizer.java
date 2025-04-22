package tk.jandev.fitting.optimizer.gradDescent;

import tk.jandev.data.DataSet;
import tk.jandev.fitting.fittable.Fittable2D;
import tk.jandev.fitting.optimizer.IteratingOptimizer;
import tk.jandev.function.Function;
import tk.jandev.function.impl.Variable;

import java.util.HashMap;
import java.util.Map;

public class GradientDescentOptimizer extends IteratingOptimizer<GradientDescentConfig> {
    private final Map<Variable, Function> variableToDerivativeInRespectToFittableParam = new HashMap<>();
    private final Function errorFunctionInRespectToDataSet;

    public GradientDescentOptimizer(Fittable2D function, GradientDescentConfig configData, DataSet dataSet) {
        super(function, configData, dataSet);

        this.errorFunctionInRespectToDataSet = function.getSquaredErrorFunction(dataSet);

        for (Variable param : this.function.getFittableParams()) { // calculate the derivates of the error function in respect to every single tunable parameter.
            Function derivativeInRespectToFittableParam = this.errorFunctionInRespectToDataSet.deriveWith(param);

            this.variableToDerivativeInRespectToFittableParam.put(param, derivativeInRespectToFittableParam);
        }
    }

    @Override
    public void iteration() {
        for (Map.Entry<Variable, Function> entry : variableToDerivativeInRespectToFittableParam.entrySet()) {
            double slope = entry.getValue().apply(this.function.getFittableParams()); // find the current slope of the error function in respect to the current parameter
            double delta = slope * this.configData.learningRate();

            entry.getKey().subtract(delta); // move in the opposite direction of the (scaled) slope of the error function in order to move "downwards" on the error gradient.
        }
    }
}
