package ch.zhaw.pong.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ch.zhaw.pong.game.AIPlayer;
import ch.zhaw.pong.game.GameListener;
import ch.zhaw.pong.game.Players;
import ch.zhaw.pong.game.PongGame;

public class PongGUI extends JPanel implements GameListener {
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 700;
	
	private JFrame frame = new JFrame();
	private ScoreBoard player1Score = new ScoreBoard(Players.PLAYER1, FRAME_WIDTH/2-8, FRAME_HEIGHT/4);
	private ScoreBoard player2Score = new ScoreBoard(Players.PLAYER2, FRAME_WIDTH/2-8, FRAME_HEIGHT/4);
	private GenerationBoard generationBoard;
	private TimeBoard timeBoard = new TimeBoard(FRAME_WIDTH/2-8, 50);

	private double player1 = 0.5;
	private double player2 = 0.5;
	private double ballX = 0.5;
	private double ballY = 0.5;
	private boolean end = false;
	
	public PongGUI(AIPlayer player) {
		JPanel info = new JPanel(new BorderLayout());
		info.add(player1Score, BorderLayout.WEST);
		info.add(player2Score, BorderLayout.EAST);
		
		generationBoard = new GenerationBoard(player, FRAME_WIDTH/2-8, 50);
		JPanel scores = new JPanel(new BorderLayout());
		scores.add(generationBoard, BorderLayout.WEST);
		scores.add(timeBoard, BorderLayout.EAST);
		scores.add(info, BorderLayout.PAGE_START);
		
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this, BorderLayout.CENTER);
		frame.add(scores, BorderLayout.NORTH);
		frame.setVisible(true);
	}
	
	@Override
	public void addKeyListener(KeyListener l) {
		frame.addKeyListener(l);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		
		Color background = Color.BLACK;
		Color color = Color.WHITE;
		
		int ballSize = (int)(height*PongGame.BALL_SIZE);
		int racketHeight = (int)(height*PongGame.RACKET_HEIGHT);
		int rackteWidth = (int)(width*PongGame.RACKET_WIDTH);
		int player1Position = (int)((height)*(1-player1));
		int player2Position = (int)((height)*(1-player2));
		int ballXPosition = (int)((width)*ballX)+rackteWidth;
		int ballYPosition = (int)((height-ballSize/2)*(1-ballY));
		
		Graphics2D graphics = (Graphics2D)g;
		
		// background color
		graphics.setColor(background);
		
		// draw field
		graphics.fill(graphics.getClipBounds());
		
		// foreground color
		graphics.setColor(color);
		
		// draw player1
		graphics.fill(new Rectangle(0, player1Position-racketHeight/2, rackteWidth, racketHeight));
		
		// draw player2	
		graphics.fill(new Rectangle(getWidth()-rackteWidth, player2Position-racketHeight/2, rackteWidth, racketHeight));
		
		// draw ball
		graphics.fill(new Ellipse2D.Double(ballXPosition-ballSize/2, ballYPosition, ballSize, ballSize));
	}
	

	@Override
	public void update(final double p1, final double p2, final double bx, final double by, final double bsx, final double bsy) {
		player1Score.update(p1, p2, bx, by, bsx, bsy);
		player2Score.update(p1, p2, bx, by, bsx, bsy);
		generationBoard.update(p1, p2, bx, by, bsx, bsy);
		timeBoard.update(p1, p2, bx, by, bsx, bsy);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				player1 = p1;
				player2 = p2;
				ballX = bx;
				ballY = by;
				
				revalidate();
				repaint();
			}
		});	
	}

	@Override
	public void end(Players player, double player1, double player2, double ballX, double ballY, double ballSpeedX, double ballSpeedY, long time) {
		player1Score.end(player, player1, player2, ballX, ballY, ballSpeedX, ballSpeedY, time);
		player2Score.end(player, player1, player2, ballX, ballY, ballSpeedX, ballSpeedY, time);
		generationBoard.end(player, player1, player2, ballX, ballY, ballSpeedX, ballSpeedY, time);
		timeBoard.end(player, player1, player2, ballX, ballY, ballSpeedX, ballSpeedY, time);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				end = true;
			}
		});	
		
	}
}
