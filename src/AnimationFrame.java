import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class AnimationFrame extends JFrame {
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final public static int FRAMES_PER_SECOND = 60;
	final public static int SCREEN_HEIGHT = (int) screenSize.getHeight();
	final public static int SCREEN_WIDTH = (int) screenSize.getWidth();
	private JPanel panel = null;
	private JButton btnPauseRun;
	private JLabel lblCarsLabel;
	private JLabel lblCars;
	private double xCenter = 0;		
	private double yCenter = 0;
	private static Thread game;
	private static boolean stop = false;

	private final double scalingFactor = 2436.363636;
	
	private long current_time = 0;								//MILLISECONDS
	private long next_refresh_time = 0;							//MILLISECONDS
	private long last_refresh_time = 0;
	private long minimum_delta_time = 1000 / FRAMES_PER_SECOND;	//MILLISECONDS
	private long actual_delta_time = 0;							//MILLISECONDS
	private long elapsed_time = 0;
	private boolean isPaused = false;
	private double scale = 1;
	private KeyboardInput keyboard = new KeyboardInput();
	private Universe universe;
	ArrayList<ActiveSprite> activeSprites = new ArrayList<ActiveSprite>();
	ArrayList<StaticSprite> staticSprites = new ArrayList<StaticSprite>();
	Background background = new CityBackground();
	boolean centreOnPlayer = false;
	int level = 1;
	private boolean loaded = false;
	private int xpCenter = SCREEN_WIDTH / 2;
	private int ypCenter = SCREEN_HEIGHT / 2;
	private Image loadingImage;
	
	public AnimationFrame()
	{
		super("");
		
//		try {
//			loadingImage = ImageIO.read(new File("res/loadedImage.png"));
//		} catch (IOException e) {
//			loadingImage = null;
//		}

		this.setFocusable(true);
		this.setSize(SCREEN_WIDTH + 20, SCREEN_HEIGHT + 36);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				keyboard.keyPressed(arg0);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				keyboard.keyReleased(arg0);
			}
		});

		Container cp = getContentPane();
		cp.setBackground(Color.BLACK);
		cp.setLayout(null);

		panel = new DrawPanel();
		panel.setLayout(null);
		panel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		getContentPane().add(panel, BorderLayout.CENTER);

		btnPauseRun = new JButton("||");
		btnPauseRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnPauseRun_mouseClicked(arg0);
			}
		});

		btnPauseRun.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPauseRun.setBounds(20, 20, 48, 32);
		btnPauseRun.setFocusable(false);
		getContentPane().add(btnPauseRun);
		getContentPane().setComponentZOrder(btnPauseRun, 0);

		lblCarsLabel = new JLabel("Cars: ");
		lblCarsLabel.setForeground(Color.GREEN);
		lblCarsLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblCarsLabel.setBounds(80, 22, 96, 30);
		getContentPane().add(lblCarsLabel);
		getContentPane().setComponentZOrder(lblCarsLabel, 0);

		lblCars = new JLabel("20");
		lblCars.setForeground(Color.GREEN);
		lblCars.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblCars.setBounds(160, 22, 320, 30);
		getContentPane().add(lblCars);
		getContentPane().setComponentZOrder(lblCars, 0);
		
	}
	
	public static void main(String[] args)
	{
		AnimationFrame me = new AnimationFrame();
		me.setVisible(true);

		game = new Thread()
		{
			public void run()
			{
				me.animationLoop();
				System.out.println("run() complete");
			}
		};

		game.start();
		System.out.println("main() complete");

	}

	private void animationLoop() {

		universe = UniverseFactory.getNextUniverse();

		while (stop == false && universe != null) {

			activeSprites = universe.getActiveSprites();
			staticSprites = universe.getStaticSprites();
			universe.getPlayer1();
			background = universe.getBackground();
			centreOnPlayer = universe.centerOnPlayer();
			level = UniverseFactory.getLevel();
			this.scale = universe.getScale();
			this.xCenter = universe.getXCenter();
			this.yCenter = universe.getYCenter();
			// main game loop
			while (stop == false && universe.isComplete() == false) {

				//adapted from http://www.java-gaming.org/index.php?topic=24220.0
				last_refresh_time = System.currentTimeMillis();
				next_refresh_time = current_time + minimum_delta_time;

				//sleep until the next refresh time
				while (current_time < next_refresh_time)
				{
					//allow other threads (i.e. the Swing thread) to do its work
					Thread.yield();

					try {
						Thread.sleep(1);
					}
					catch(Exception e) {    					
					} 

					//track current time
					current_time = System.currentTimeMillis();
					
			
					
					if (elapsed_time > 5000) {
						loaded = true;
					}
				}

				//    			System.out.println(String.format("start update: %d", System.currentTimeMillis()));
				//read input
				keyboard.poll();
				handleKeyboardInput();

				//UPDATE STATE
				updateTime();
				universe.update(keyboard, actual_delta_time);
				updateControls();

				//REFRESH
				this.repaint();
				//    			System.out.println(String.format("end update: %d", System.currentTimeMillis()));
			}

			//    		System.out.println(String.format("level %d complete", UniverseFactory.getLevel()));

			universe = UniverseFactory.getNextUniverse();

		}

		System.out.println("animation complete");
		AudioPlayer.setStopAll(true);
		dispose();	

	}

	private void updateControls() {
		this.lblCars.setText(Long.toString(activeSprites.size()));
		if (activeSprites.size() < 34) {
			lblCarsLabel.setForeground(Color.GREEN);
			lblCars.setForeground(Color.GREEN);
		}else if (activeSprites.size() < 66) {
			lblCarsLabel.setForeground(Color.YELLOW);
			lblCars.setForeground(Color.YELLOW);
		}else {
			lblCarsLabel.setForeground(Color.RED);
			lblCars.setForeground(Color.RED);
		}
	}

	private void updateTime() {

		current_time = System.currentTimeMillis();
		actual_delta_time = (isPaused ? 0 : current_time - last_refresh_time);
		last_refresh_time = current_time;
		elapsed_time += actual_delta_time;

	}

	protected void btnPauseRun_mouseClicked(MouseEvent arg0) {
		if (isPaused) {
			isPaused = false;
			this.btnPauseRun.setText("||");
		}
		else {
			isPaused = true;
			this.btnPauseRun.setText(">");
		}
	}

	private void handleKeyboardInput() {
		//if the interface needs to respond to certain keyboard events
		if (keyboard.keyDown(80) && ! isPaused) {
			btnPauseRun_mouseClicked(null);	
		}
		if (keyboard.keyDown(79) && isPaused ) {
			btnPauseRun_mouseClicked(null);
		}
		if (keyboard.keyDown(112)) {
			if (scale < 1) {
				scale *= 1.01;
			}
//			scale = 1;
		}
		if (keyboard.keyDown(113)) {
			if (scale > SCREEN_WIDTH / scalingFactor) {
				scale /= 1.01;
			}
//			scale /= 1.01;
//			scale = 0.55;
		}
	}

	class DrawPanel extends JPanel {

		public void paintComponent(Graphics g)
		{	
			if (universe == null) {
				return;
			}
			
			paintBackground(g, background);
			
			for (StaticSprite staticSprite : staticSprites) {
				if (staticSprite.getShowImage()) {
					g.drawImage(staticSprite.getImage(), translateX(staticSprite.getMinX()), translateY(staticSprite.getMinY()), scaleX(staticSprite.getWidth()), scaleY(staticSprite.getHeight()), null);
				}
			}

			for (ActiveSprite activeSprite : activeSprites) {
				if (activeSprite.getImage() != null) {
					g.drawImage(activeSprite.getImage(), translateX(activeSprite.getMinX()), translateY(activeSprite.getMinY()), scaleX(activeSprite.getWidth()), scaleY(activeSprite.getHeight()), null);
				}
				else {
					g.setColor(Color.BLUE);
					g.fillRect(translateX(scale * (activeSprite.getMinX())), translateY(activeSprite.getMinY()), scaleX(activeSprite.getWidth()), scaleY(activeSprite.getHeight()));					
				}
				g.drawImage(activeSprite.getImage(), translateX(activeSprite.getMinX()), translateY(activeSprite.getMinY()), scaleX(activeSprite.getWidth()), scaleY(activeSprite.getHeight()), null);

			}
			if (loaded != true) {
				g.drawImage(loadingImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
	    	}

		}
		private int translateX(double x) {
			return xpCenter + scaleX(x - xCenter);
		}
		
		private int scaleX(double x) {
			return (int) Math.round(scale * x);
		}
		private int translateY(double y) {
			return ypCenter + scaleY(y - yCenter);
		}		
		private int scaleY(double y) {
			return (int) Math.round(scale * y);
		}
		private void paintBackground(Graphics g, Background background) {

			if ((g == null) || (background == null)) {
				return;
			}
			
			//what tile covers the top-left corner?
			double xTopLeft = ( xCenter - (xpCenter / scale));
			double yTopLeft =  (yCenter - (ypCenter / scale)) ;
			
			int row = background.getRow((int)yTopLeft);
			int col = background.getCol((int)xTopLeft);
			Tile tile = null;

			boolean rowDrawn = false;
			boolean screenDrawn = false;
			while (screenDrawn == false) {
				while (rowDrawn == false) {
					tile = background.getTile(col, row);
					if (tile.getWidth() <= 0 || tile.getHeight() <= 0) {
						//no increase in width; will cause an infinite loop, so consider this screen to be done
						g.setColor(Color.GRAY);
						g.fillRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);					
						rowDrawn = true;
						screenDrawn = true;						
					}
					else {
						Tile nextTile = background.getTile(col+1, row+1);
						int width = translateX(nextTile.getMinX()) - translateX(tile.getMinX());
						int height = translateY(nextTile.getMinY()) - translateY(tile.getMinY());
						g.drawImage(tile.getImage(), translateX(tile.getMinX()), translateY(tile.getMinY()), width, height, null);
					}					
					//does the RHE of this tile extend past the RHE of the visible area?
					if (translateX(tile.getMinX() + tile.getWidth()) > SCREEN_WIDTH || tile.isOutOfBounds()) {
						rowDrawn = true;
					}
					else {
						col++;
					}
				}
				//does the bottom edge of this tile extend past the bottom edge of the visible area?
				if (translateY(tile.getMinY() + tile.getHeight()) > SCREEN_HEIGHT || tile.isOutOfBounds()) {
					screenDrawn = true;
				}
				else {
					//TODO - should be passing in a double, as this represents a universe coordinate
					col = background.getCol((int)xTopLeft);
					row++;
					rowDrawn = false;
				}
			}
		}					
	}
	protected void this_windowClosing(WindowEvent e) {
		System.out.println("windowClosing()");
		stop = true;
		dispose();	
	}
}
