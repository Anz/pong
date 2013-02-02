package ch.zhaw.nn;

import java.util.Arrays;

public class NeuralNetwork {
	private Neuron[] inputs; 
	private Neuron[] outputs;
	private Neuron[][] hiddens;
	
	public NeuralNetwork(int inputs, int outputs, int layers) {
		this.inputs = new Neuron[inputs+1];
		this.outputs = new Neuron[outputs];
		this.hiddens = new Neuron[layers][];
		
		for (int i = 0; i < this.inputs.length; i++) {
			Neuron n = new Neuron(null);
			this.inputs[i] = n;
		}
//		this.inputs[this.inputs.length-1].setOutput(1); // bias
		
		Neuron[] prev = this.inputs;
		for (int j = 0;  j < hiddens.length; j++) {			
			Neuron[] hiddens = new Neuron[(int)Math.ceil((prev.length+outputs+layers-j-1)/2)+1];
			for (int i = 0; i < hiddens.length; i++) {
				Neuron n = new Neuron(prev);
				hiddens[i] = n;
			}
//			hiddens[hiddens.length-1].setOutput(1); // bias
			prev = hiddens;
			this.hiddens[j] = hiddens;
		}
		
		for (int i = 0; i < outputs; i++) {
			Neuron n = new Neuron(hiddens[hiddens.length-1]);
			this.outputs[i] = n;
		}
	}
	
	public void applyChromoson(Chromoson chromoson) {
		int i = 0;
		
		for (Neuron[] layer : hiddens) {
			for (Neuron n : layer) {
				int length = n.getWeights().length;
				n.setWeights(Arrays.copyOfRange(chromoson.getString(), i, i + length));
				i += length;
			}
		}
		
		for (Neuron n : outputs) {
			int length = n.getWeights().length;
			n.setWeights(Arrays.copyOfRange(chromoson.getString(), i, i + length));
			i += length;
		}
	}
	
	public Chromoson getChromoson() {
		double[] string = new double[0];
		
		for (Neuron[] layer : hiddens) {
			for (Neuron n : layer) {
				string = concat(string, n.getWeights());
			}
		}
		
		for (Neuron n : outputs) {
			string = concat(string, n.getWeights());
		}
		
		return new Chromoson(string);
	}
	
	public void update() {
		for (Neuron[] layer : hiddens) {
			for (Neuron neuron : layer) {
				neuron.update();
			}
		}
		
		for (Neuron neuron : outputs) {
			neuron.update();
		}
	}

	
	public Neuron[] getInputs() {
		return inputs;
	}

	
	public Neuron[] getOutputs() {
		return outputs;
	}

	
	public Neuron[][] getHiddens() {
		return hiddens;
	}
	
	
	public static double[] concat(double[] first, double[] second) {
		double[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	
	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
