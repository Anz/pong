package ch.zhaw.pong.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import ch.zhaw.pong.ValueUtils;
import ch.zhaw.pong.game.Player;
import ch.zhaw.pong.game.PongGame;
import ch.zhaw.pong.game.PongGame.Speed;

public class KeyboardPlayer implements Player, KeyListener {
	private PongGame game;
	private Speed speed = Speed.MEDIUM;
	private char up;
	private char down;
	private char key = 0;
	private double destination = 0.5;

	
	public KeyboardPlayer(char up, char down) {
		super();
		this.up = up;
		this.down = down;
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		if (up == e.getKeyChar()) {
			synchronized (this) {
				key = 0;
			}			
		} else if(down == e.getKeyChar()) {
			synchronized (this) {
				key = 0;
			}
		} else if ('r' == e.getKeyChar()) {
			speed = Speed.SLOW;
			game.setGameSpeed(speed);
		}  else if ('t' == e.getKeyChar()) {
			speed = Speed.MEDIUM;
			game.setGameSpeed(speed);
		} else if ('z' == e.getKeyChar()) {
			speed = Speed.FAST;
			game.setGameSpeed(speed);
		}	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (up == e.getKeyChar()) {
			synchronized (this) {
				key = up;
			}			
		} else if(down == e.getKeyChar()) {
			synchronized (this) {
				key = down;
			}
		}
	}

	@Override
	public double getDestination() {
		synchronized (this) {
			if (speed == Speed.SLOW) {
				if (key == up) {
					destination += PongGame.PLAYER_SPEED;
					destination = ValueUtils.between(destination, PongGame.RACKET_HEIGHT/2, 1-PongGame.RACKET_HEIGHT/2);
				} else if (key == down) {
					destination -= PongGame.PLAYER_SPEED;
					destination = ValueUtils.between(destination, PongGame.RACKET_HEIGHT/2, 1-PongGame.RACKET_HEIGHT/2);
				}
			}
			return destination;
		}
	}

	@Override
	public void update(double player1, double player2, double ballX,
			double ballY, double ballSpeedX, double ballSpeedY) {
		synchronized (this) {
			if (speed != Speed.SLOW) {
				destination += ValueUtils.between(ballY - destination + PongGame.RACKET_HEIGHT/2 * (new Random().nextDouble() - 0.5), -PongGame.PLAYER_SPEED/2, PongGame.PLAYER_SPEED/2);
//				destination = ballY ;//+ PongGame.RACKET_HEIGHT/2 * (new Random().nextDouble() - 0.5);
			}
		}
		
	}

	@Override
	public void end(boolean won, double me, double enemy, double ballX, double ballY, double ballSpeedX, double ballSpeedY, long time) {
		// TODO Auto-generated method stub
		
	}


	public PongGame getGame() {
		return game;
	}

	public void setGame(PongGame game) {
		this.game = game;
	}

	@Override
	public void hitMe(double me, double enemy, double ballX, double ballY,
			double ballSpeedX, double ballSpeedY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hitEnemey(double me, double enemy, double ballX, double ballY,
			double ballSpeedX, double ballSpeedY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(double me, double enemy, double ballX, double ballY,
			double ballSpeedX, double ballSpeedY) {
		// TODO Auto-generated method stub
		
	}
	
	
}
