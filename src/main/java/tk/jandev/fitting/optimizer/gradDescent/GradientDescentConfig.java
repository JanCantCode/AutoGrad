package tk.jandev.fitting.optimizer.gradDescent;

import tk.jandev.fitting.optimizer.OptimizationConfig;

public record GradientDescentConfig(double learningRate) implements OptimizationConfig {
}
