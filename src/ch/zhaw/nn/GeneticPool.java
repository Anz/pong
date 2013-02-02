package ch.zhaw.nn;

import java.util.Arrays;
import java.util.Random;

public class GeneticPool {
	private Chromoson[] chromosons;
	private NeuralNetwork network;
	private Random random;
	private double mutationRate = 0.3;
	
	public GeneticPool(NeuralNetwork network, int poolSize) {
		this.network = network;
		
		random = new Random(System.currentTimeMillis());
		Chromoson sample = network.getChromoson();
		
		chromosons = new Chromoson[poolSize];
		for (int i = 0; i < poolSize; i++) {
			double[] string = new double[sample.getString().length];
			for (int j = 0; j < string.length; j++) {
				string[j] = random.nextDouble();
			}

			chromosons[i] = new Chromoson(string);
		}
		
		network.applyChromoson(chromosons[0]);
	}
	
	
	public void train(boolean good, double[] inputs, double output) {
		// set inputs
		for (int i = 0; i < inputs.length; i++) {
			network.getInputs()[i].setOutput(inputs[i]);
		}
			
		// test each
		for (Chromoson chromoson : chromosons) {
			network.applyChromoson(chromoson);
			network.update();
			chromoson.setFitness(1 - Math.abs(network.getOutputs()[0].getOutput()-output));
		}
			
		// sort
		Arrays.sort(chromosons);
		
		// children
		Chromoson[] children = new Chromoson[chromosons.length];
		int size = (int)Math.round(Math.sqrt(children.length));
		int index = 0;
		
		for (int j = 0; j < size && index < children.length; j++) {
			for (int k = 0; k < size && index < children.length; k++) {
				Chromoson a = new Chromoson(null);
				Chromoson b = new Chromoson(null);
				cross(chromosons[k], chromosons[j], a, b);
				children[index++] = a;
				if (index < children.length) {
					children[index++] = b;
				}
			}
		}
		chromosons = children;
		
		// test each
		for (Chromoson chromoson : chromosons) {
			network.applyChromoson(chromoson);
			network.update();
			
			double fitness = Math.abs(network.getOutputs()[0].getOutput()- output);
			if (good) {
				fitness = 1 - fitness;
			}
			chromoson.setFitness(fitness);
		}
			
		// sort
		Arrays.sort(chromosons);
		
		
		network.applyChromoson(chromosons[0]);
	}
	
	
	public void cross(Chromoson parentA, Chromoson parentB, Chromoson childA, Chromoson childB) {			
		int length = parentA.getString().length;
		int swap = random.nextInt(length);
		double[] x = new double[length];
		double[] y = new double[length];
		
		for (int j = 0; j < length; j++) {
			if (j == swap) {
				double[] tmp = y;
				y = x;
				x = tmp;
			}
			
			x[j] = parentA.getString()[j];
			y[j] = parentB.getString()[j];
		}
		childA.setString(x);
		childB.setString(y);
		
		// mutation
		mutate(childA);
		mutate(childB);
	}
	
	private void mutate(Chromoson chromoson) {
		double[] string = chromoson.getString();
		for (int i = 0; i < string.length; i++) {
			if (random.nextDouble() < mutationRate) {
				string[i] += random.nextDouble() - 0.5;
			}
		}
		chromoson.setString(string);
	}

	
}
