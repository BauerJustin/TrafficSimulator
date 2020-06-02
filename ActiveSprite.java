import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class ActiveSprite {

	//TODO - can this be made private?
	protected double minX = 0;
	protected double minY = 0;
	protected int width = 50;
	protected int height = 50;
	private boolean dispose = false;

	public ActiveSprite() {
	}

	public final void setMinX(double currentX) {
		this.minX = currentX;
	};
	public final void setMinY(double currentY) {
		this.minY = currentY;
	}
	
	public final double getMinX() {
		return minX;
	}

	public final double getMaxX() {
		return minX + width;
	}

	public final double getMinY() {
		return minY;
	}

	public final double getMaxY() {
		return minY + height;
	}

	public final long getHeight() {
		// TODO Auto-generated method stub
		return height;
	}
	public final void setHeight(int h) {
		// TODO Auto-generated method stub
		height = h;
	}
	public final long getWidth() {
		// TODO Auto-generated method stub
		return width;
	}
	public final void setWidth(int w) {
		// TODO Auto-generated method stub
		width = w;
	}
	public double getCenterX() {
		return minX + (width / 2);
	};
	
	public double getCenterY() {
		return minY + (height / 2);
	};
				
	public boolean getDispose() {
		return dispose;
	}

	public void setDispose() {
		this.dispose = true;
	}

	public abstract void update(Universe level, KeyboardInput keyboard, long actual_delta_time);
	public abstract Image getImage();

	public void setSprites(ArrayList<ActiveSprite> activeSprites) {
		// TODO Auto-generated method stub
		
	}

	public void setCars(ArrayList<ActiveSprite> activeSprites) {
		// TODO Auto-generated method stub
		
	}
	
}
