package org.metric.metricminer2;

public interface CodeMetric {

	String getName();
	boolean accepts(String fileName);
	
}
