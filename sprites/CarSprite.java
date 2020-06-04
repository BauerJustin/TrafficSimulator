import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

public class CarSprite extends ActiveSprite {
	
	private double currentAngle = 0;
	private double initialAngle;
	private Image[] rotatedImages;
	private double velocity;	
	private double setVelocity;
	private final int size = 4;
	private ArrayList<StaticSprite> barriers;
	private ArrayList<StaticSprite> north;
	private ArrayList<StaticSprite> south;
	private ArrayList<StaticSprite> west;
	private ArrayList<StaticSprite> east;
	private ArrayList<StaticSprite> junction;
	private ArrayList<StaticSprite> disposal;
	private ArrayList<StaticSprite> spawn;
	private Random rand = new Random();
	private final int MAXSPEED = 125;
	private final int MINSPEED = 100;
	private boolean notColliding = true;
	private double velocityX;
	private double velocityY;
	private boolean onSpawn = false;
	private boolean center = false;
	private boolean Entered = false;
	private boolean turningRight = false;
	private boolean turningLeft = false;
	private boolean firstTimeThrough;
	private boolean hasTurned = false;
	private boolean centerCar = false;
	private boolean centered = false;
	private int RATEOFTURNING = 5;
	private int turnTime;
	private boolean k = false;
	private boolean a = false;
	private boolean c = false;
	private boolean h = false;
	private boolean o = false;
	private boolean w = false;
	private boolean turningFreedom = false;
	private int waitTime = 0;
	private int stallTime = 0;
	private int spawnTime = 0;
	private int collisionTime = 0;
	
	public CarSprite(double currentX, double currentY) {
		
		super();

		this.minX = currentX;
		this.minY = currentY;
		setRotatedImages();
		width = CarImageGenerator.getSize().getWidth(null) / size;
		height = CarImageGenerator.getSize().getHeight(null) / size;
		setVelocity = getRandomVelocity();
		velocity = setVelocity;
		firstTimeThrough = true;
		centered = false;
		hasTurned = true;
	}

	public void setRotatedImages() {
		this.rotatedImages = CarImageGenerator.getRandomColour();
	}

	private int getRandomVelocity() {

		int speed = rand.nextInt(MAXSPEED);
		while (speed < MINSPEED) {
			speed++;
		}
		return speed;
	}

	public Image getImage() {
		if (currentAngle < 0) {
	    	currentAngle += 360;
	    }
	    if (currentAngle >= 360) {
	    	currentAngle -= 360;
	    }
	    
	    this.height = rotatedImages[(int)currentAngle].getHeight(null) / size;
	    this.width = rotatedImages[(int)currentAngle].getWidth(null) / size;
		return rotatedImages[(int)currentAngle];

	}
		
	@Override
	public void update(Universe level, KeyboardInput keyboard, long actual_delta_time) {

		if (turningFreedom || (keyboard.keyDown(17) && keyboard.keyDownOnce(84))) {
			turningFreedom = true;
		}else {
			turningRight = false;
			turningLeft = false;
		}
		double newX = this.minX;
		double newY = this.minY;
		velocityX = 0;
		velocityY = 0;
		if (currentAngle < 0) {
	    	currentAngle += 360;
	    }
		if (currentAngle < -360) {
			currentAngle += 360;
		}
	    if (currentAngle >= 360) {
	    	currentAngle -= 360;
	    }
	    this.height = rotatedImages[(int)currentAngle].getHeight(null) / size;
	    this.width = rotatedImages[(int)currentAngle].getWidth(null) / size;

	    //auto drive
	    if (currentAngle == 0 || currentAngle == 360) {
	    	velocityY = -velocity;
	    }
	    if (currentAngle == 90) {
	    	velocityX += velocity;	
	    }
	    if (currentAngle == 180) {
	    	velocityY += velocity;	
	    }
	    if (currentAngle == 270) {
	    	velocityX = -velocity;
	    }
//	    // LEFT
//	    if (keyboard.keyDown(37)) {
//	    	goLeft();
//		}
//		//UP
//		if (keyboard.keyDown(38)) {
//			goUp();	
//		}
//		// RIGHT
//		if (keyboard.keyDown(39)) {
//			goRight();
//		}
//		// DOWN
//		if (keyboard.keyDown(40)) {
//			goDown();
//		}
//		// LEFT + UP
//		if (keyboard.keyDown(38) && keyboard.keyDown(37)) {
//			if (currentAngle != 315) {
//				if (currentAngle > 315 || currentAngle <= 135) {
//		    		currentAngle--;
//		    	}
//		    	if (currentAngle < 315 && currentAngle > 135) {
//		    		currentAngle++;
//		    	}
//
//			}
//		}
//		// RIGHT + UP
//		if (keyboard.keyDown(38) && keyboard.keyDown(39)) {
//					
//			if (currentAngle != 45) {
//				if (currentAngle > 45 && currentAngle <= 225) {
//		    		currentAngle--;
//		    	}
//		    	if (currentAngle < 45 || currentAngle > 225) {
//		    		currentAngle++;
//		    	}
//
//			}
//		}
//		// LEFT + DOWN
//		if (keyboard.keyDown(40) && keyboard.keyDown(37)) {
//					
//			if (currentAngle != 225) {
//				if (currentAngle > 225 || currentAngle < 45) {
//		    		currentAngle--;
//		    	}
//		    	if (currentAngle < 225 && currentAngle >= 45) {
//		    		currentAngle++;
//		    	}
//
//			}
//		}
//		// RIGHT + DOWN
//		if (keyboard.keyDown(40) && keyboard.keyDown(39)) {
//							
//			if (currentAngle != 135) {
//				if (currentAngle > 135 && currentAngle <= 315) {
//		    		currentAngle--;
//		    	}
//		    	if (currentAngle < 135 || currentAngle > 315) {
//		    		currentAngle++;
//		    	}
//
//			}
//		}
//	    Lightning McQueen Easter Egg
	    if ((keyboard.keyDown(75) || k == true)) {
	    	k = true;
	    	if ((keyboard.keyDown(65) || a == true)) {
	    		a = true;
	    		if ((keyboard.keyDown(67) || c == true)) {
	    			c = true;
	    			if ((keyboard.keyDown(72) || h == true)) {
		    			h = true;
		    			if ((keyboard.keyDown(79) || o == true)) {
			    			o = true;
			    			if ((keyboard.keyDown(87) || w == true)) {
				    			w = true;
				    		}
			    		}
		    		}
	    		}
	    	}    	
		}
	    if (k == true && a == true && c == true && h == true && o == true && w == true) {
	    	this.rotatedImages = CarImageGenerator.getLightningImages();
	    	this.velocity = 500;
	    	currentAngle = initialAngle;
	    	hasTurned = true;
	    	k = false;
	    	a = false;
	    	c = false;
	    	h = false;
	    	o = false;
	    	w = false;
	    	System.out.println("KACHOW KACHOW KACHOW KACHOW KACHOW KACHOW KACHOW KACHOW");
	    	if (checkCollisionWithBarrier(level, newX, newY) == true) {
	    		this.setDispose();
	    	}
	    }
	    if (keyboard.keyDown(39)){
	    	if (turningRight != true && turningLeft != true) {
	    		velocity++;
		    	hasTurned = true;
	    	}
	    	
	    }
	    if (keyboard.keyDown(37)){
	    	if (velocity > 0 && (turningRight != true && turningLeft != true)) {
	    		velocity--;
		    	hasTurned = true;
	    	}
	    	
	    }	
		if (checkCollisionWithBarrier(level, newX, newY) == true) {
			notColliding = false;
		}
		
		if ((checkFlow(newX, newY, north) == true) && (checkFlow(newX, newY, south) == true)){
			if (currentAngle == 0) {
				velocityX += velocity;
			}if (currentAngle == 180) {
				velocityX = velocityX - velocity;	
			}
		}
		else if ((checkFlow(newX, newY, east) == true) && (checkFlow(newX, newY, west) == true)){
			if (currentAngle == 90) {
				velocityY += velocity;
			}if (currentAngle == 270) {
				velocityY = velocityY - velocity;	
			}
		}
		else if ((checkFlow(newX, newY, north) == true) && notColliding && (checkFlow(newX, newY, south) != true)){
			currentAngle = 0;
		}
		else if ((checkFlow(newX, newY, south) == true) && notColliding && (checkFlow(newX, newY, north) != true)){
			currentAngle = 180;
		}
		else if ((checkFlow(newX, newY, west) == true) && notColliding && (checkFlow(newX, newY, east) != true)){
			currentAngle = 270;
		}
		else if ((checkFlow(newX, newY, east) == true) && notColliding && (checkFlow(newX, newY, west) != true)){
			currentAngle = 90;
		}
		if (checkFlow(newX, newY, spawn) == true && checkFlow(newX, newY, disposal) != true){
			if (onSpawn == false) {
				currentAngle = currentAngle + 180;
				onSpawn = true;
			}
			if (currentAngle == 0 || currentAngle == 360) {
				goUp();
			}if (currentAngle == 90) {
				goRight();
			}if (currentAngle == 180) {
				goDown();
			}if (currentAngle == 270) {
				goLeft();
			}
			if (center == false) {
				setRotatedImages();
				setVelocity = getRandomVelocity();
				velocity = setVelocity;
				center();
				center = true;
				firstTimeThrough = false;
				hasTurned = false;
			}
		}
		else if (checkFlow(newX, newY, disposal) == true){
			center = false;
			if (checkFlow(newX, newY, disposal) == true && checkFlow(newX, newY, east) == true){
				goRight();
			}else if (checkFlow(newX, newY, disposal) == true && checkFlow(newX, newY, west) == true){
				goLeft();
			}else if (checkFlow(newX, newY, disposal) == true && checkFlow(newX, newY, north) == true){
				goUp();
			}else if (checkFlow(newX, newY, disposal) == true && checkFlow(newX, newY, south) == true){
				goDown();
			}else {
				velocityX = 0;
				velocityY = 0;
				if (currentAngle == 0 || currentAngle == 360) {
					goLeft();
				}if (currentAngle == 90) {
					goUp();
				}if (currentAngle == 180) {
					goRight();
				}if (currentAngle == 270) {
					goDown();
				}
			}
		}
		
		if (checkFlow(newX, newY, spawn) != true) {
			onSpawn = false;
		}
		if (checkFlow(newX, newY, junction) != true) {
			Entered = false;
			turningRight = false;
			turningLeft = false;
			if (hasTurned && centered != true) {
				turnTime = 0;
				centerCar = true;
				centered = true;
			}
		}
		if (checkFlow(newX, newY, junction) == true) {
			int choice = -1;
			if (Entered == false) {
				choice = rand.nextInt(3);
				initialAngle = currentAngle;
				if (choice == 1) {
					turningRight = true;
				}else if (choice == 2) {
					turningLeft = true;
				}
				Entered = true;
			}
		}
		if (turningLeft == true && firstTimeThrough != true && hasTurned != true) {
			if (initialAngle == 0) {
				turningLeft = false;
			}else {
				currentAngle = currentAngle - RATEOFTURNING / 2;
				velocityX = 0;
				velocityY = 0;
				if (currentAngle < initialAngle && currentAngle > initialAngle - 45) {
					if (initialAngle == 360) {
						velocityY = velocityY - MINSPEED * 1.4;
					}else if (initialAngle == 90) {
						velocityX += MINSPEED * 1.4;
					}else if (initialAngle == 270) {
						velocityX = velocityX - MINSPEED * 1.4;
					}else if (initialAngle == 180) {
						velocityY += MINSPEED * 1.4;
					}
				}else if (initialAngle - 45 > currentAngle && currentAngle > initialAngle - 90) {
					if (initialAngle == 360) {
						goLeft();
					}else if (initialAngle == 90) {
						goUp();
					}else if (initialAngle == 180) {
						goRight();
					}else if (initialAngle == 270) {
						goDown();
					}
				}else {
					currentAngle = initialAngle - 90;
					if (currentAngle == 0 || currentAngle == 360) {
				    	velocityY = -velocity;
				    	centered = false;
				    }
				    if (currentAngle == 90) {
				    	centered = false;
				    	velocityX += velocity;	
				    }
				    if (currentAngle == 180) {
				    	velocityY += velocity;	
				    }
				    if (currentAngle == 270) {
				    	centered = false;
				    	velocityX = -velocity;
				    }
				    
				   
					turningLeft = false;
					hasTurned = true;
				}
				
			}
			}
			
		if (turningRight == true && firstTimeThrough != true && hasTurned != true) {
			if (initialAngle == 270) {
				turningRight = false;
			}else {
				currentAngle = currentAngle + RATEOFTURNING;
				velocityX = 0;
				velocityY = 0;
				if (initialAngle + 45 > currentAngle) {
					if (initialAngle == 90) {
						velocityX += velocity / 0.7;
					}else if (initialAngle == 270) {
						velocityX = velocityX - velocity / 0.7;
					}else if (initialAngle == 0 || initialAngle == 360) {
						velocityY = velocityY - velocity / 0.7;
					}else if (initialAngle == 180) {
						velocityY += velocity / 0.7;
					}
				}else if (initialAngle + 45 < currentAngle && currentAngle < initialAngle + 90) {
					if (initialAngle == 90) {
						goDown();
					}else if (initialAngle == 270) {
						goUp();
					}else if (initialAngle == 0) {
						goRight();
					}else if (initialAngle == 180) {
						goLeft();
					}
				}else {
					currentAngle = initialAngle + 90;
					if (currentAngle == 0 || currentAngle == 360) {
				    	velocityY = -velocity;
				    }
				    if (currentAngle == 90) {
				    	velocityX += velocity;	
				    }
				    if (currentAngle == 180) {
				    	velocityY += velocity;	
				    }
				    if (currentAngle == 270) {
				    	velocityX = -velocity;
				    }
				    
				   
					turningRight = false;
					hasTurned = true;
					centered = false;
				}
			}
			
			
		}
		if (centerCar == true) {
			if (currentAngle == 0 || currentAngle == 360) {
				velocityX += MINSPEED * 0.25;
			}if (currentAngle == 180) {
				velocityX = velocityX - MINSPEED * 0.25;
			}if (currentAngle == 270) {
				velocityY = velocityY - MINSPEED * 0.25;	
			}
			turnTime++;
//			if (currentAngle == 0 || currentAngle == 360) {
//				velocityX = velocityX - MINSPEED * 10;
//			}if (currentAngle == 180) {
//				velocityX += MINSPEED * 10;
//			}if (currentAngle == 270) {
//				velocityY += MINSPEED * 10;
//			}
			if (turnTime == 20) {
				centerCar = false;
				centered = true;
			}
		}
		newX += actual_delta_time * 0.001 * velocityX;
		newY +=  actual_delta_time * 0.001 * velocityY;
		
		if (this.spacing(level, newX, newY, 5) && this.checkFlow(newX, newY, east) && this.checkFlow(newX, newY, west)) {
			this.setDispose();
		}
		if (this.spacing(level, newX, newY, 0)) {
			this.velocity = 0;
		}
		if (this.spacing(level, newX, newY, 5)) {
			this.velocity = 0;
		}
		else if (this.spacing(level, newX, newY, 35)) {
			this.velocity = this.velocity - 2;
		}else {
			if (this.setVelocity > this.velocity) {
				this.velocity = this.velocity + 2;
			}
		}
		
		if (this.checkFlow(newX, newY, spawn) && this.checkFlow(newX, newY, disposal)) {
			if (this.minX == newX && this.minY == newY) {
				this.setDispose();
			}
			
		}
		if (this.checkFlow(newX, newY, spawn) || this.checkFlow(newX, newY, disposal)) {
			this.spawnTime++;
			if (this.spawnTime > 500) {
				this.spawnTime = 0;
				this.setDispose();
			}
			
		}
		if (this.checkFlow(newX, newY, junction)) {
			if (this.waitTime < 100) {
				this.waitTime++;
				this.velocity = 0;
			}
		}else {
			this.waitTime = 0;
		}
		
		
		if (checkCollisionWithBarrier(level, newX, newY) == false) {
			this.minX = newX;
			this.minY = newY;
		}
		if (this.velocity < 0) {
			this.velocity = 0;
		}
		if (this.checkCollisionWithSprites(level, newX, newY)) {
			this.velocity = 0;
			this.turningLeft = false;
			this.turningRight = false;
			collisionTime++;
			if(collisionTime > 20 && firstTimeThrough) {
				this.setDispose();
			}
		}else {
			collisionTime = 0;
		}
		if (this.checkFlow(newX, newY, junction) && this.minX == newX && this.minY == newY) {
			this.stallTime++;
			if (this.stallTime > 100) {
				this.velocity--;
			}
		}else {
			this.stallTime = 0;
		}
		
		notColliding = true;
	}
	//end of update
	private void goDown() {
    	velocityY += velocity;
	}

	private void goRight() {
    	velocityX += velocity;
	}

	private void goUp() {
    	velocityY = velocityY - velocity;	
	}

	private void goLeft() {
    	velocityX = velocityX - velocity;
	}

	public boolean checkCollisionWithBarrier(Universe sprites, double x, double y) {

		boolean colliding = false;
		
		for (StaticSprite staticSprite : sprites.getStaticSprites()) {
			if (staticSprite instanceof BarrierSprite) {
				boolean toLeft = (x + this.width) < staticSprite.getMinX();
				boolean toRight = x > staticSprite.getMaxX();
				boolean collidingX = !( toLeft || toRight);
				boolean above = (y + this.height) < staticSprite.getMinY();
				boolean below = y > staticSprite.getMaxY();
				boolean collidingY = !( above || below);
				if (collidingX && collidingY) {
					colliding = true;
					break;
				}			
			}
		}		
		return colliding;		
	}

	public boolean checkCollisionWithSprites(Universe sprites, double x, double y) {

		for (ActiveSprite activeSprite : sprites.getActiveSprites()) {
			if (activeSprite instanceof CarSprite && activeSprite != this) {
				boolean toLeft = (x + this.width) < activeSprite.getMinX();
				boolean toRight = x > activeSprite.getMaxX();
				boolean collidingX = !( toLeft || toRight);
				boolean above = (y + this.height) < activeSprite.getMinY();
				boolean below = y > activeSprite.getMaxY();
				boolean collidingY = !( above || below);
				if (collidingX && collidingY) {
					return true;
				}	
			}	
		}
		return false;	
	}
	public boolean spacing(Universe sprites, double x, double y, double space) {
		
		if (this.checkFlow(x, y, south) || this.currentAngle == 180) {
			for (ActiveSprite activeSprite : sprites.getActiveSprites()) {
				if (activeSprite instanceof CarSprite && activeSprite != this) {
					boolean toLeft = (x + this.width) < activeSprite.getMinX();
					boolean toRight = x > activeSprite.getMaxX();
					boolean collidingX = !( toLeft || toRight);
					boolean above = (y + this.height + space) < activeSprite.getMinY() - space;
					boolean below = y > activeSprite.getMaxY();
					boolean collidingY = !( above || below);
					if (collidingX && collidingY) {
						return true;
					}	
				}	
			}
		}
		else if (this.checkFlow(x, y, east) || this.currentAngle == 90) {
			for (ActiveSprite activeSprite : sprites.getActiveSprites()) {
				if (activeSprite instanceof CarSprite && activeSprite != this) {
					boolean toLeft = (x + this.width + space) < activeSprite.getMinX() - space;
					boolean toRight = x > activeSprite.getMaxX();
					boolean collidingX = !( toLeft || toRight);
					boolean above = (y + this.height) < activeSprite.getMinY();
					boolean below = y > activeSprite.getMaxY();
					boolean collidingY = !( above || below);
					if (collidingX && collidingY) {
						return true;
					}	
				}	
			}
		}else if (this.checkFlow(x, y, west) || this.currentAngle == 270) {
			for (ActiveSprite activeSprite : sprites.getActiveSprites()) {
				if (activeSprite instanceof CarSprite && activeSprite != this) {
					boolean toLeft = (x + this.width) < activeSprite.getMinX();
					boolean toRight = x - space > activeSprite.getMaxX() + space;
					boolean collidingX = !( toLeft || toRight);
					boolean above = (y + this.height) < activeSprite.getMinY();
					boolean below = y > activeSprite.getMaxY();
					boolean collidingY = !( above || below);
					if (collidingX && collidingY) {
						return true;
					}	
				}	
			}
		}else {
			for (ActiveSprite activeSprite : sprites.getActiveSprites()) {
				if (activeSprite instanceof CarSprite && activeSprite != this) {
					boolean toLeft = (x + this.width) < activeSprite.getMinX();
					boolean toRight = x > activeSprite.getMaxX();
					boolean collidingX = !( toLeft || toRight);
					boolean above = (y + this.height) < activeSprite.getMinY();
					boolean below = y - space > activeSprite.getMaxY() + space;
					boolean collidingY = !( above || below);
					if (collidingX && collidingY) {
						return true;
					}	
				}	
			}
		}
		return false;	
	}
	public boolean spawnCheck(Universe sprites, double x, double y, double space) {

		for (ActiveSprite activeSprite : sprites.getActiveSprites()) {
			if (activeSprite instanceof CarSprite && activeSprite != this) {
				boolean toLeft = (x + this.width + space) < activeSprite.getMinX() - space;
				boolean toRight = x - space > activeSprite.getMaxX() + space;
				boolean collidingX = !( toLeft || toRight);
				boolean above = (y + this.height + space) < activeSprite.getMinY() - space;
				boolean below = y - space > activeSprite.getMaxY() + space;
				boolean collidingY = !( above || below);
				if (collidingX && collidingY) {
					return true;
				}	
			}
		}
		return false;	
	}
	public void setBarriers(ArrayList<StaticSprite> barriers) {
		this.barriers = barriers; 
		
	}
	public boolean checkCollisionWithBarrierSpawn(Universe sprites, double x, double y) {

		boolean colliding = false;

		for (StaticSprite staticSprite : barriers) {
			if (staticSprite instanceof BarrierSprite) {
				boolean toLeft = (x + this.width) < staticSprite.getMinX();
				boolean toRight = x > staticSprite.getMaxX();
				boolean collidingX = !( toLeft || toRight);
				boolean above = (y + this.height) < staticSprite.getMinY();
				boolean below = y > staticSprite.getMaxY();
				boolean collidingY = !( above || below);
				if (collidingX && collidingY) {
					colliding = true;
					break;
				}			
			}
		}		
		return colliding;		
	}
	public void setFlowNorth(ArrayList<StaticSprite> north) {
		this.north = north; 
		
	}
	public void setFlowSouth(ArrayList<StaticSprite> south) {
		this.south = south; 
		
	}
	public void setFlowWest(ArrayList<StaticSprite> west) {
		this.west = west; 
		
	}
	public void setFlowEast(ArrayList<StaticSprite> east) {
		this.east = east; 
		
	}
	public void setFlowJunction(ArrayList<StaticSprite> junction) {
		this.junction = junction; 
		
	}
	public void setFlowDisposal(ArrayList<StaticSprite> disposal) {
		this.disposal = disposal;
	}
	public void setFlowSpawn(ArrayList<StaticSprite> spawn) {
		this.spawn = spawn;
	}
	public boolean checkFlow(double x, double y, ArrayList<StaticSprite> flow) {

		boolean colliding = false;

		for (StaticSprite staticSprite : flow) {
			if (staticSprite instanceof BarrierSprite) {
				boolean toLeft = (x + this.width) < staticSprite.getMinX();
				boolean toRight = x > staticSprite.getMaxX();
				boolean collidingX = !( toLeft || toRight);
				boolean above = (y + this.height) < staticSprite.getMinY();
				boolean below = y > staticSprite.getMaxY();
				boolean collidingY = !( above || below);
				if (collidingX && collidingY) {
					colliding = true;
					break;
				}			
			}
		}		
		return colliding;		
	}

	public void setDirection() {
		if (checkFlow(this.minX, this.minY, north) == true){
			currentAngle = 0;
		}
		if (checkFlow(this.minX, this.minY, south) == true){
			currentAngle = 180;
		}
		if (checkFlow(this.minX, this.minY, west) == true){
			currentAngle = 270;
		}
		if (checkFlow(this.minX, this.minY, east) == true){
			currentAngle = 90;
		}
	}
	public void center() {
		velocityX = 0;
		velocityY = 0;
		if (currentAngle == 0 || currentAngle == 360) {
			velocityX += MINSPEED * 15;
		}if (currentAngle == 90) {
			velocityY += MINSPEED  * 15;
		}if (currentAngle == 180) {
			velocityX = velocityX - MINSPEED  * 15;
		}if (currentAngle == 270) {
			velocityY = velocityY - MINSPEED  * 15;	
		}
		if (currentAngle == 0 || currentAngle == 360) {
			velocityX = velocityX - MINSPEED * 10;
		}if (currentAngle == 90) {
			velocityY = velocityY - MINSPEED * 10;	
		}if (currentAngle == 180) {
			velocityX += MINSPEED * 10;
		}if (currentAngle == 270) {
			velocityY += MINSPEED * 10;
		}
	}
	public double getVelocity() {
		return velocity;
	}
}
