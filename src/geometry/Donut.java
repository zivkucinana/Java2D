package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Donut extends Circle {

	private int innerRadius;
	
	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius); 
		this.innerRadius = innerRadius;
	}
	 
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		setColor(color);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		setInnerColor(innerColor);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}

	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(getColor());
		g.drawOval(getCenter().getX() - this.innerRadius,
					getCenter().getY() - this.innerRadius,
					this.innerRadius*2,
					this.innerRadius*2);
		this.fill(g);
	}

	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(getCenter().getX() - this.innerRadius,
					getCenter().getY() - this.innerRadius,
					this.innerRadius * 2 - 2,
					this.innerRadius * 2 - 2);
	}

	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut pomocni = (Donut) obj;
			if (this.getCenter().equals(pomocni.getCenter()) &&
					this.getRadius() == pomocni.getRadius() &&
					this.innerRadius == pomocni.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	public String toString() {
		return super.toString() + ", inner radius=" + this.innerRadius;
	}
	
}