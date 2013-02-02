package ch.zhaw.nn;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Chromoson implements Comparable<Chromoson> {
	private double[] string;
	private double fitness;
	
	
	public Chromoson(double[] string) {
		super();
		this.string = string;
	}





	public double[] getString() {
		return string;
	}





	public void setString(double[] string) {
		this.string = string;
	}


	public double getFitness() {
		return fitness;
	}





	public void setFitness(double fitness) {
		this.fitness = fitness;
	}


	@Override
	public int compareTo(Chromoson o) {
		double diff = o.getFitness() - fitness;
		
		if (diff > 0) {
			return 1;
		}
		if (diff < 0) {
			return -1;
		}
		return 0;
	}





	@Override
	public String toString() {
		return "Chromoson [string=" + Arrays.toString(string) + ", fitness="
				+ fitness + "]";
	}

	
	
	
}
