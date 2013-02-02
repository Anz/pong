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

public class ScoreBoard extends JLabel implements GameListener {
	private static final long serialVersionUID = 1L;
	private static final String FORMAT = "%05d";
	private int score = 0;
	private Players player;
	
	
	public ScoreBoard(Players player, int width, int height) {
		super(String.format(FORMAT, 0), SwingConstants.CENTER);
		
		this.player = player;
		
		Dimension dimension = new Dimension(width, height);

		setPreferredSize(dimension);
		
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 100));
		setOpaque(true);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
	}


	@Override
	public void update(double player1, double player2, double ballX, double ballY, double ballSpeedX, double ballSpeedY) {}

	@Override
	public void end(Players won, double player1, double player2, double ballX, double ballY, double ballSpeedX, double ballSpeedY, long time) {
		if (this.player == won) {
			SwingUtilities.invokeLater(new Runnable() {				
				@Override
				public void run() {
					score++;
					setText(String.format(FORMAT, score));
					revalidate();
					repaint();
				}
			});
		}
		
	}
}
