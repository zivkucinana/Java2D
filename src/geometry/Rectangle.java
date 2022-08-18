package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SurfaceShape {

	private Point upperLeftPoint;
	private int width;
	private int height;

	public Rectangle() {

	}

	public Rectangle(Point upperLeftPoint, int width, int height) {
		this.upperLeftPoint = upperLeftPoint;
		this.width = width;
		this.height = height;
	}

	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected) {
		this(upperLeftPoint, width, height);
		this.setSelected(selected);
	}

	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color color) {
		this(upperLeftPoint, width, height, selected);
		this.setColor(color);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, Color color, Color innerColor) {
		this(upperLeftPoint, width, height);
		this.setColor(color);
		this.setInnerColor(innerColor);
	}

	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color color, Color innerColor) {
		this(upperLeftPoint, width, height, selected, color);
		this.setInnerColor(innerColor);
	}

	public boolean contains(int x, int y) {
		if (upperLeftPoint.getX() <= x && upperLeftPoint.getY() <= y && x <= upperLeftPoint.getX() + width
				&& y <= upperLeftPoint.getY() + height) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Rectangle) {
			return this.area() - ((Rectangle) o).area();
		}
		return 0;
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.upperLeftPoint.moveBy(byX, byY);
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillRect(this.upperLeftPoint.getX() + 1, this.upperLeftPoint.getY() + 1, this.width - 1, this.height - 1);
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(getColor());
		g.drawRect(this.upperLeftPoint.getX(), this.upperLeftPoint.getY(), this.width, this.height);
		this.fill(g);
		
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.upperLeftPoint.getX()-3, this.upperLeftPoint.getY()-3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX()+this.width-3, this.upperLeftPoint.getY()-3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX()-3, this.upperLeftPoint.getY()+this.height-3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX()+this.width-3, this.upperLeftPoint.getY()+this.height-3, 6, 6);
			
		}

	}

	public boolean contains(Point p) {
		if (upperLeftPoint.getX() <= p.getX() && upperLeftPoint.getY() <= p.getY()
				&& p.getX() <= upperLeftPoint.getX() + width && p.getY() <= upperLeftPoint.getY() + height) {
			return true;
		} else {
			return false;
		}
	}

	public int area() {
		return width * height;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle pomocni = (Rectangle) obj;
			if (this.upperLeftPoint.equals(pomocni.getUpperLeftPoint()) && this.width == pomocni.getWidth()
					&& this.height == pomocni.getHeight()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String toString() {
		// upperLeftPoint=(x,y), width=width, height=height
		return "UpperLeftPoint=" + upperLeftPoint + "," + "widht=" + width + "," + "height=" + height;
	}

}
