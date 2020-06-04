
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class CityBackground extends Background {

    private Image north;
    private Image south;
    private Image east;
    private Image west;
    private Image junction;
    private Image boundary;
    private Image disposal;
    private Image spawn;
    private static int backgroundWidth = 50;
	private static int backgroundHeight = 50;
    private static int maxCols = 61;
	private static int maxRows = 33;
    
    public final int START_X = 1200;
    public final int START_Y = 600;

	private int[][] map;
    
    public CityBackground() {
    	
    	try {
    		this.north = ImageIO.read(new File("res/city-tiles/north_tile.jpg"));
    		this.south = ImageIO.read(new File("res/city-tiles/south_tile.jpg"));
    		this.west = ImageIO.read(new File("res/city-tiles/west_tile.jpg"));
    		this.east = ImageIO.read(new File("res/city-tiles/east_tile.jpg"));    		
    		this.junction = ImageIO.read(new File("res/city-tiles/junction_tile.jpg"));  
    		this.boundary = ImageIO.read(new File("res/city-tiles/grass_tile.jpg"));  
    		this.disposal = ImageIO.read(new File("res/city-tiles/black_tile.jpg"));  
    		this.spawn = ImageIO.read(new File("res/city-tiles/black_tile.jpg"));    		
    	}
    	catch (IOException e) {
    		System.out.println(e.toString());
    	}
    }
	public void loadMap() {
		try {
			this.map = CSVReader.importFromCSV("res/map.csv");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public Tile getTile(int col, int row) {
		
		Image image = null;
		
		if (row < 0 || row > maxRows || col < 0 || col > maxCols) {
			image = null;
		}
		else if (map[row][col] == 0) {
			image = north;
		}
		else if (map[row][col] == 1) {
			image = south;
		}
		else if (map[row][col] == 2) {
			image = west;
		}
		else if (map[row][col] == 3) {
			image = east;
		}
		else if (map[row][col] == 4) {
			image = junction;
		}
		else if (map[row][col] == 5) {
			image = boundary;
		}
		else if (map[row][col] == 6) {
			image = disposal;
		}
		else if (map[row][col] == 7) {
			image = spawn;
		}
		else {
			image = null;
		}
			
		int x = (col * backgroundWidth);
		int y = (row * backgroundHeight);
		
		Tile newTile = new Tile(image, x, y, backgroundWidth, backgroundHeight, false);
		
		return newTile;
	}
	public static int getXSize() {
		
		return backgroundWidth*(maxRows / 2) + backgroundWidth * 15;
		
	}
	public static int getYSize() {
		
		return backgroundHeight*(maxCols / 2) - backgroundHeight * 13;
		
	}
	public int getHorizontal(int x) {
		//which tile is x sitting at?
		return 0;
	}

	public int getCol(int x) {
		//which col is x sitting at?
		int col = 0;
		if (backgroundWidth != 0) {
			col = (x / backgroundWidth);
			if (x < 0) {
				return col - 1;
			}
			else {
				return col;
			}
		}
		else {
			return 0;
		}
	}
	
	public int getRow(int y) {
		//which row is y sitting at?
		int row = 0;
		
		if (backgroundHeight != 0) {
			row = (y / backgroundHeight);
			if (y < 0) {
				return row - 1;
			}
			else {
				return row;
			}
		}
		else {
			return 0;
		}
	}
	
	public ArrayList<StaticSprite> getBarriers() {
		ArrayList<StaticSprite> barriers = new ArrayList<StaticSprite>();
		for (int row = 0; row < map[0].length; row++) {
			for (int col = 0; col < map.length; col++) {
				if (map[col][row] == 5) {
					barriers.add(new BarrierSprite(row * backgroundWidth, col * backgroundHeight, (row + 1) * backgroundWidth, (col + 1) * backgroundHeight, false));
				}
				
			}
		}
		return barriers;
	}
	public ArrayList<StaticSprite> getDirectionOfFlow(int num) {
		ArrayList<StaticSprite> flow = new ArrayList<StaticSprite>();
		for (int row = 0; row < this.map[0].length; row++) {
			for (int col = 0; col < this.map.length; col++) {
				if (this.map[col][row] == num) {
					flow.add(new BarrierSprite(row * backgroundWidth, col * backgroundHeight, (row + 1) * backgroundWidth, (col + 1) * backgroundHeight, false));
				}
				
			}
		}
		return flow;
	}
	public int getBackgroundWidth() {
		return backgroundWidth;
	}
	public int getBackgroundHeight() {
		return backgroundHeight;
	}
	
}
