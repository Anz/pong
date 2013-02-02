package ch.zhaw.pong;

import ch.zhaw.nn.NeuralNetwork;
import ch.zhaw.pong.game.AIPlayer;
import ch.zhaw.pong.game.PongGame;
import ch.zhaw.pong.gui.KeyboardPlayer;
import ch.zhaw.pong.gui.PongGUI;

public class Launcher {
	
	public static void main(String[] args) {
		
		NeuralNetwork network = new NeuralNetwork(6, 1, 1);
		
		// setup player
		KeyboardPlayer player1 = new KeyboardPlayer('w', 's');
		AIPlayer player2 = new AIPlayer(network);
		
		PongGame game = new PongGame(player1, player2);
		player1.setGame(game);
		PongGUI gui = new PongGUI(player2);
		gui.addKeyListener(player1);
		game.addGameListener(gui);
		game.run();
	}
}
