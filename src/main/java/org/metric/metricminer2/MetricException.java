package org.metric.metricminer2;

public class MetricException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MetricException(ClassLevelMetric metric,
			String sourceCode, Throwable t) {
		super("Metric: " + metric.getClass().getName() + " in source code " + sourceCode, t);
	}
	
	public MetricException(MethodLevelMetric metric,
			String sourceCode, Throwable t) {
		super("Metric: " + metric.getClass().getName() + " in source code " + sourceCode, t);
	}

}
