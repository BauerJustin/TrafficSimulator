import java.awt.Image;

public abstract class StaticSprite {

	protected double minX = 0;
	protected double minY = 0;
	protected double maxX = 0;
	protected double maxY = 0;
	protected boolean showImage = true;
	public StaticSprite() {
	}
	public boolean getShowImage() {
		return showImage;
	}

	public void setShowImage(boolean showImage) {
		this.showImage = showImage;
	}
	public final void setMinX(double minX) {
		this.minX = minX;
	};
	public final void setMinY(double minY) {
		this.minY = minY;
	}
	
	public final double getMinX() {
		return minX;
	}

	public final double getMaxX() {
		return maxX;
	}

	public final double getMinY() {
		return minY;
	}

	public final double getMaxY() {
		return maxY;
	}

	public final double getHeight() {
		// TODO Auto-generated method stub
		return maxY - minY;
	}

	public final double getWidth() {
		// TODO Auto-generated method stub
		return maxX - minX;
	}

	public abstract Image getImage();
	
}
