package ch.zhaw.pong.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ch.zhaw.pong.game.GameListener;
import ch.zhaw.pong.game.Players;

public class TimeBoard extends JLabel implements GameListener {
	private static final long serialVersionUID = 1L;
	private static final String FORMAT = "Age: %d h %02d m %02d s %03d mili";
	private long timestamp;
	
	
	public TimeBoard(int width, int height) {
		super(String.format(FORMAT, 0, 0, 0, 0), SwingConstants.CENTER);
		
		timestamp = System.currentTimeMillis();
		
		Dimension dimension = new Dimension(width, height);

		setPreferredSize(dimension);
		
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		setOpaque(true);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
	}


	@Override
	public void update(double player1, double player2, double ballX, double ballY, double ballSpeedX, double ballSpeedY) {
		SwingUtilities.invokeLater(new Runnable() {				
			@Override
			public void run() {
				long time = System.currentTimeMillis() - timestamp;
				long hours = time / 3600000;
				long minutes = (time / 60000) % 60;
				long seconds = (time / 1000) % 60;
				long milisecs = time % 1000;
				
				setText(String.format(FORMAT, hours, minutes, seconds, milisecs));
				revalidate();
				repaint();
			}
		});
	}

	@Override
	public void end(Players won, double player1, double player2, double ballX, double ballY, double ballSpeedX, double ballSpeedY, long time) {}
}
