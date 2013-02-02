package ch.zhaw.pong;

public class ValueUtils {
	private ValueUtils() {}
	
	public static double between(double value, double min, double max) {
		return Math.max(min, Math.min(max, value));
	}
}
