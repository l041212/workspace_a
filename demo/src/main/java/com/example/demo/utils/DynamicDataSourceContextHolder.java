package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	private static List<String> labels = new ArrayList<String>();

	public static void setDataSource(String label) {
		contextHolder.set(label);
	}

	public static String getDataSource() {
		return contextHolder.get();
	}

	public static void removeDataSoure() {
		contextHolder.remove();
	}

	public static void setLabels(List<String> labels) {
		DynamicDataSourceContextHolder.labels = labels;
	}

	public static List<String> getLabels() {
		return DynamicDataSourceContextHolder.labels;
	}

	public static boolean isContainsDataSource(String label) {
		return labels.contains(label);
	}

}
