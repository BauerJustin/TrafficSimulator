import java.util.ArrayList;
import java.util.Random;

public class MappedUniverse extends Universe {

	private Random rand = new Random();
	private final int carSpawnLimit = 20;
	private ArrayList<StaticSprite> north;
	private ArrayList<StaticSprite> south;
	private ArrayList<StaticSprite> west;
	private ArrayList<StaticSprite> east;
	private ArrayList<StaticSprite> junction;
	private ArrayList<StaticSprite> barriers;
	private ArrayList<StaticSprite> disposal;
	private ArrayList<StaticSprite> spawn;
	private boolean firstTime = true;
	private int maxCars = 100;
	
	public MappedUniverse() {
	
		super();
		
		background = new CityBackground();
		((CityBackground) background).loadMap();
		north = ((CityBackground) background).getDirectionOfFlow(0);
		south = ((CityBackground) background).getDirectionOfFlow(1);
		west = ((CityBackground) background).getDirectionOfFlow(2);
		east = ((CityBackground) background).getDirectionOfFlow(3);
		junction = ((CityBackground) background).getDirectionOfFlow(4);
		barriers = ((CityBackground) background).getDirectionOfFlow(5);
		disposal = ((CityBackground) background).getDirectionOfFlow(6);
		spawn = ((CityBackground) background).getDirectionOfFlow(7);
		
		CarImageGenerator.createRotatedImages();
		
		for (int i = 0; i < carSpawnLimit; i++){
			createCar();
    	}
		
		staticSprites.addAll(barriers);
//		for (ActiveSprite sprite : sprites) {
//			activeSprites.add(sprite);
//    	}
		
	}

	public void createCar() {
//		System.out.println(activeSprites.size());
		if (maxCars > activeSprites.size()) {
			int x = rand.nextInt((int)(CityBackground.getXSize()*2));
	   		int y = rand.nextInt((int)(CityBackground.getYSize()*2));
			CarSprite car = new CarSprite(x ,y);
	    	car.setBarriers(barriers);
			if(car.checkCollisionWithBarrierSpawn(getUniverse(), x, y) == true){
				car.setDispose();
				createCar();
			}else {
				if (firstTime == true) {
					firstTime = false;
					activeSprites.add(car);
					car.setFlowNorth(north);
		        	car.setFlowSouth(south);
		        	car.setFlowWest(west);
		        	car.setFlowEast(east);
		        	car.setFlowJunction(junction);
		        	car.setFlowDisposal(disposal);
		        	car.setFlowSpawn(spawn);
		        	car.setDirection();
				}else {
					if (car.checkCollisionWithSprites(getUniverse(), x, y) == false) {
						activeSprites.add(car);
						car.setFlowNorth(north);
			        	car.setFlowSouth(south);
			        	car.setFlowWest(west);
			        	car.setFlowEast(east);
			        	car.setFlowJunction(junction);
			        	car.setFlowDisposal(disposal);
			        	car.setFlowSpawn(spawn);
			        	car.setDirection();
			        	if (car.checkFlow(x, y, north) && car.checkFlow(x, y, south) && car.spawnCheck(getUniverse(), x, y, 25)&& car.spawnCheck(getUniverse(), x, y, -25)) {
			        		car.setDispose();
							createCar();
			        	}
			        	else if (car.checkFlow(x, y, west) && car.checkFlow(x, y, east) && car.spawnCheck(getUniverse(), x, y, 25) && car.spawnCheck(getUniverse(), x, y, -25)) {
			        		car.setDispose();
							createCar();
			        	}
			        	else if (car.checkFlow(x, y, junction) || car.checkFlow(x, y, spawn)|| car.checkFlow(x, y, disposal)) {
			        		car.setDispose();
							createCar();
			        	}
					}else {
						car.setDispose();
						createCar();
					}
				}
			}
		}
	}
	
	public boolean centerOnPlayer() {
		return false;
	}		

	public void update(KeyboardInput keyboard, long actual_delta_time) {

		if (keyboard.keyDownOnce(27)) {
			complete = true;
		}
		if (keyboard.keyDown(38)) {
	    	createCar();
	    	
	    }
		if (keyboard.keyDown(40)) {
			if (activeSprites.size() > 1) {
				activeSprites.get(activeSprites.size()-1).setDispose();
			}
	    }
		if (keyboard.keyDown(17) && keyboard.keyDown(83)) {
			maxCars = 1000;
		}
		updateSprites(keyboard, actual_delta_time);
		disposeSprites();
	}
	
}
