package ch.zhaw.nn;


public class Neuron {

	private Neuron[] inputs = null;
	private double[] weights = null;
	private double bias = -1.0;
	private double output = 0;
	

	public Neuron(Neuron[] inputs) {
		if (inputs == null) {
			return;
		}		
		
		this.inputs = inputs;
		weights = new double[inputs.length+1];
		
		for (int i = 0; i < inputs.length; i++) {
			weights[i] = 0.5;
		}
	}


	public Neuron[] getInputs() {
		return inputs;
	}
	
	
	public double[] getWeights() {
		return weights;
	}


	public void setWeights(double[] weights) {
		this.weights = weights;
	}


	public void setOutput(double output) {
		this.output = output;
	}
	
	
	public double getOutput() {
		return output;
	}
	
	
	public void update() {
		// do not change status if no inputs are avaiable
		if (inputs == null || inputs.length == 0) {
			return;
		}
		
		double activation = 0;
		
		for (int i = 0; i < inputs.length; i++) {
			activation += inputs[i].getOutput() * weights[i];
		}
		
		// sugnoid function
		output = 1 / (1 + Math.exp(-activation/1.0));
	}	


}
