package ch.zhaw.pong.game;

public class Ball {
	private double x = 0.5;
	private double y = 0.5;
	private double speedX = 0;
	private double speedY = 0;
	
	public void step() {
		x += speedX;
		y += speedY;
		
		// check y boundaries
		if (y < 0) {
			y = 0;
			speedY = -speedY;
		} else if (y > 1) {
			y = 1;
			speedY = -speedY;
		}
	}
	
	public double getX() {
		return x;
	}
	
	
	public void setX(double x) {
		this.x = x;
	}
	
	
	public double getY() {
		return y;
	}
	
	
	public void setY(double y) {
		this.y = y;
	}
	
	
	public double getSpeedX() {
		return speedX;
	}
	
	
	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	
	
	public double getSpeedY() {
		return speedY;
	}
	
	
	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}
	
	
}
