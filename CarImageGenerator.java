import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class CarImageGenerator {
	
	private static Random rand = new Random();
	private static Image red = null;
	private static Image blue = null;
	private static Image yellow = null;
	private static Image black = null;
	private static Image white = null;
	private static Image lightning = null;
	private static Image[] redImages;
	private static Image[] blueImages;
	private static Image[] yellowImages;
	private static Image[] blackImages;
	private static Image[] whiteImages;
	private static Image[] lightningImages;
	private static int amountOfCars = 6;
	
	public static void createRotatedImages()  {
		for (int i = 0; i < amountOfCars; i++) {
			Image image = getCarImage(i);
			Image[] current = new Image[360];
			for (int j = 0; j < 360; j++) {
				current[j] = ImageRotator.rotate(image, j);
			}
			if (i == 0) {
				setRedImages(current);
			}else if (i == 1) {
				setBlueImages(current);
			}else if (i == 2) {
				setYellowImages(current);
			}else if (i == 3) {
				setBlackImages(current);
			}else if (i == 4) {
				setWhiteImages(current);
			}else if (i == 5) {
				setLightningImages(current);
			}else {
				System.out.println("Error");
			}
		}
	}
	public static Image getCarImage(int car) {
	
		try {
			red = ImageIO.read(new File("res/cars/red_car.png"));
			blue = ImageIO.read(new File("res/cars/blue_car.png"));
			yellow = ImageIO.read(new File("res/cars/yellow_car.png"));
			black = ImageIO.read(new File("res/cars/black_car.png"));
			white = ImageIO.read(new File("res/cars/white_car.png"));
			lightning = ImageIO.read(new File("res/cars/lightningmcqueen_car.png"));
		}
		catch (IOException e) {	
		}
		
		if (car == 0) {
			return red;
		}else if (car == 1) {
			return blue;
		}else if (car == 2) {
			return yellow;
		}else if (car == 3) {
			return black;
		}else if (car == 4) {
			return white;
		}else if (car == 5) {
			return lightning;
		}else {
			return null;
		}
		
		
	}
	public static Image[] getRandomColour() {
		int car = rand.nextInt(amountOfCars - 1);
		if (car == 0) {
			return getRedImages();
		}else if (car == 1) {
			return getBlueImages();
		}else if (car == 2) {
			return getYellowImages();
		}else if (car == 3) {
			return getBlackImages();
		}else if (car == 4) {
			return getWhiteImages();
		}else {
			return null;
		}
	}
	public static Image getSize() {
		
		return red;
		
	}
	public static Image[] getRedImages() {
		return redImages;
	}
	public static Image[] getBlueImages() {
		return blueImages;
	}
	public static Image[] getYellowImages() {
		return yellowImages;
	}
	public static Image[] getBlackImages() {
		return blackImages;
	}
	public static Image[] getWhiteImages() {
		return whiteImages;
	}
	public static Image[] getLightningImages() {
		return lightningImages;
	}
	public static void setRedImages(Image[] redImages) {
		CarImageGenerator.redImages = redImages;
	}
	public static void setBlueImages(Image[] blueImages) {
		CarImageGenerator.blueImages = blueImages;
	}
	public static void setYellowImages(Image[] yellowImages) {
		CarImageGenerator.yellowImages = yellowImages;
	}
	public static void setBlackImages(Image[] blackImages) {
		CarImageGenerator.blackImages = blackImages;
	}
	public static void setWhiteImages(Image[] whiteImages) {
		CarImageGenerator.whiteImages = whiteImages;
	}
	public static void setLightningImages(Image[] lightningImages) {
		CarImageGenerator.lightningImages = lightningImages;
	}
}