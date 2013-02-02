package ch.zhaw.pong.game;

public interface GameListener {
	public void end(Players won, double player1, double player2, double ballX, double ballY, double ballSpeedX, double ballSpeedY, long time);
	public void update(double player1, double palyer2, double ballX, double ballY, double ballSpeedX, double ballSpeedY);
}
