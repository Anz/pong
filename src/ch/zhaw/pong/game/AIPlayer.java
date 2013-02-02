package ch.zhaw.pong.game;

import ch.zhaw.nn.GeneticPool;
import ch.zhaw.nn.NeuralNetwork;
import ch.zhaw.nn.Neuron;

public class AIPlayer implements Player {
	
	private static final int MAX_CHROMOSONS = 200;
	
	private NeuralNetwork network;
	private GeneticPool geneticPool;
	private int generation = 1;
	private double[] enemyInputs;
	private double enemyOutput;
	private double[] meInputs;
	private double meOutput;
	private double[] enemyInputsMirror;
	private double enemyOutputMirror;
	private double[] meInputsMirror;
	private double meOutputMirror;

	
	public AIPlayer(NeuralNetwork network) {
		super();
		this.network = network;
		this.geneticPool = new GeneticPool(network, MAX_CHROMOSONS);
	}



	@Override
	public double getDestination() {
		synchronized (network) {
			return network.getOutputs()[0].getOutput();
		}
	}



	@Override
	public void update(double me, double enemy, double ballX, double ballY, double ballSpeedX, double ballSpeedY) {
		synchronized (network) {
			Neuron[] inputs = network.getInputs();
			
			inputs[0].setOutput(me);
			inputs[1].setOutput(enemy);
			inputs[2].setOutput(ballX);
			inputs[3].setOutput(ballY);
			inputs[4].setOutput(ballSpeedX);
			inputs[5].setOutput(ballSpeedY);

			network.update();
		}
		
	}



	@Override
	public void end(boolean won, double me, double enemy, double ballX, double ballY, double ballSpeedX, double ballSpeedY, long time) {
		synchronized (network) {
			if (won) {
//				geneticPool.train(true, enemyInputs, ballY);
//				geneticPool.train(true, meInputs, meOutput);
			} else {
//				geneticPool.train(true, meInputs, ballY);
				geneticPool.train(true, enemyInputs, enemyOutput);
			}
			generation++;
		}
	}



	public int getGeneration() {
		return generation;
	}

	public int getGenePoolSize() {
		return -1;
	}



	@Override
	public void hitMe(double me, double enemy, double ballX, double ballY, double ballSpeedX, double ballSpeedY) {
		meOutput = me;
		enemyInputs = new double[] {enemy, me, 1-ballX, ballY, 1-ballSpeedX, ballSpeedY };
//		geneticPool.train(false, enemyInputs, enemyOutput);
//		geneticPool.train(true, meInputs, meOutput);
//		geneticPool.train(true, meInputs, meOutput);
	}


	@Override
	public void hitEnemey(double me, double enemy, double ballX, double ballY,
			double ballSpeedX, double ballSpeedY) {
		meInputs = new double[] {me, enemy, ballX, ballY, ballSpeedX, ballSpeedY };
		enemyOutput = enemy;
//		geneticPool.train(false, meInputs, meOutput);
//		geneticPool.train(true, enemyInputs, enemyOutput);
		geneticPool.train(true, enemyInputs, enemyOutput);
	}



	@Override
	public void start(double me, double enemy, double ballX, double ballY,
			double ballSpeedX, double ballSpeedY) {
		meInputs = new double[] {me, enemy, ballX, ballY, ballSpeedX, ballSpeedY };
		enemyInputs = new double[] {enemy, me, 1-ballX, ballY, 1-ballSpeedX, ballSpeedY };
		meOutput = me;
		enemyOutput = enemy;
	}
}
