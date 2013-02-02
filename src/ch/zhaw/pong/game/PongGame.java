package ch.zhaw.pong.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.zhaw.pong.ValueUtils;


public class PongGame implements Runnable {
	
	public enum Speed {
		SLOW(20), MEDIUM(5), FAST(0);
		
		long delay;

		private Speed(long delay) {
			this.delay = delay;
		}

		public long getDelay() {
			return delay;
		}
		
		
	};
	
	public static final double RACKET_HEIGHT = 0.2;
	public static final double RACKET_WIDTH = 0.03;
	public static final double BALL_SIZE = 0.04;
	public static final double BALL_SPEED = 0.04;
	public static final long START_TIME = 10;
	public static final double PLAYER_SPEED = 0.04;
	
	private Player player1;
	private Player player2;
	private double player1Position = 0.5;
	private double player2Position = 0.5;
	private Ball ball = new Ball();
	private Players attacker = Players.PLAYER1;
	private Speed gameSpeed = Speed.MEDIUM;
	private long start;
	
	private List<GameListener> listeners = new ArrayList<GameListener>();
	

	public PongGame(Player player1, Player player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;
		
		reset();
	}


	@Override
	public void run() {
		reset();
		long timestamp;
//		long lastMovement = 0;
		
		while(true) {
			long now = System.currentTimeMillis();
			long playTime = now - start;
			timestamp = now;
			
			// ball position
			if (playTime > START_TIME * gameSpeed.getDelay()) {
				ball.step();
				
				if (ball.getX() > 1 || ball.getX() < 0) {
					if (ball.getX() < 0 && Math.abs(player1Position-ball.getY()) > (RACKET_HEIGHT+BALL_SIZE)/2+0.01) {
						if (gameSpeed != Speed.FAST) {
							for (GameListener l : listeners)
								l.end(Players.PLAYER2, player1Position, player2Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY(), playTime);
						}
						player1.end(false, player1Position, player2Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY(), playTime);
						player2.end(true, player2Position, player1Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY(), playTime);
						attacker = Players.PLAYER1;
						reset();
					} else if (ball.getX() > 1 && Math.abs(player2Position-ball.getY()) > (RACKET_HEIGHT+BALL_SIZE)/2+0.01) {
						if (gameSpeed != Speed.FAST) {
							for (GameListener l : listeners)
								l.end(Players.PLAYER1, player1Position, player2Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY(), playTime);
						}
						player1.end(true, player1Position, player2Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY(), playTime);
						player2.end(false, player2Position, player1Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY(), playTime);
						attacker = Players.PLAYER2;
						reset();
					} else {
						double ydistance;
						if (ball.getX() < 0) {
							ydistance = Math.PI/4*(ball.getY()-player1Position)/((RACKET_HEIGHT+BALL_SIZE)/2);
							player1.hitMe(player1Position, player2Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY());
							player2.hitEnemey(player2Position, player1Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY());
						} else {
							player2.hitMe(player2Position, player1Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY());
							player1.hitEnemey(player1Position, player2Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY());						
							ydistance = Math.PI/4*(ball.getY()-player2Position)/((RACKET_HEIGHT+BALL_SIZE)/2);
						}

						ball.setSpeedY(BALL_SPEED * Math.sin(ydistance));
						ball.setSpeedX(-ball.getSpeedX()/Math.abs(ball.getSpeedX()) * BALL_SPEED * Math.cos(ydistance));
						ball.setX(ValueUtils.between(ball.getX(), 0, 1));
					}
				}
			} else {
				if (attacker == Players.PLAYER1) {
					ball.setY(player1Position);
				} else {
					ball.setY(player2Position);
				}
			}
				
			// player destination
			double player1Destination = ValueUtils.between(player1.getDestination(), 0, 1);
			double player2Destination = ValueUtils.between(player2.getDestination(), 0, 1);
			
			double player1Speed = ValueUtils.between(player1Destination-player1Position, -PLAYER_SPEED, PLAYER_SPEED);
			double player2Speed = ValueUtils.between(player2Destination-player2Position, -PLAYER_SPEED, PLAYER_SPEED);
			
			player1Position = ValueUtils.between(player1Position+player1Speed, 0, 1);
			player2Position = ValueUtils.between(player2Position+player2Speed, 0, 1);
			
			// fire events
			if (gameSpeed != Speed.FAST) {
				for (GameListener l : listeners)
					l.update(player1Position, player2Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY());
			}
			player1.update(player1Position, player2Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY());
			player2.update(player1Position, player2Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY());
			
			// wait
			try {
				long wait;
				synchronized (gameSpeed) {
					wait = gameSpeed.getDelay() - (now - timestamp);
				}
				if (wait > 0) {
					Thread.sleep(wait);
				}
			} catch (InterruptedException e) {
			}
			
			
		}
		
	}
	
	public void reset() {
		if (attacker == Players.PLAYER1) {
			ball.setX(0.01);
			ball.setY(player1Position);
			ball.setSpeedX(BALL_SPEED);
			ball.setSpeedY(0);
		} else {
			ball.setX(0.99);
			ball.setY(player2Position);
			ball.setSpeedX(-BALL_SPEED);
			ball.setSpeedY(0);
		}
		start = System.currentTimeMillis();
		
		player1.start(player1Position, player2Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY());
		player2.start(player2Position, player1Position, ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY());
	}

	
	public void addGameListener(GameListener l) {
		listeners.add(l);
	}


	public void setGameSpeed(Speed gameSpeed) {
		synchronized (this.gameSpeed) {
			this.gameSpeed = gameSpeed;
		}
	}
	
	
}
