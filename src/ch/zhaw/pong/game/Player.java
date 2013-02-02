package ch.zhaw.pong.game;

public interface Player {
	public double getDestination();
	public void update(double me, double enemy, double ballX, double ballY, double ballSpeedX, double ballSpeedY);
	public void start(double me, double enemy, double ballX, double ballY, double ballSpeedX, double ballSpeedY);
	public void end(boolean won, double me, double enemy, double ballX, double ballY, double ballSpeedX, double ballSpeedY, long time);
	public void hitMe(double me, double enemy, double ballX, double ballY, double ballSpeedX, double ballSpeedY);
	public void hitEnemey(double me, double enemy, double ballX, double ballY, double ballSpeedX, double ballSpeedY);
}
