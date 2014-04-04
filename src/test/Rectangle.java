package test;

public class Rectangle {
	private double width;
	private double height;

	// 无参构造器
	public Rectangle() {
	}

	// 有参构造器
	public Rectangle(double width, double height) {
		this.width = width;
		this.height = height;
	}

	// 属性的get和set方法定义
	public void setWidth(double width) {
		this.width = width;
	}

	public double getWidth() {
		return this.width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getHeight() {
		return this.height;
	}

	// 计算周长的方法
	private double getPerimeter() {
		return (width + height) * 2;
	}

	// 计算面积的方法
	private double getArea() {
		return width * height;
	}

	public static void main(String[] args) {
		Rectangle rec = new Rectangle(3.6, 5.8);
		System.out.println("周长:" + rec.getPerimeter());
		System.out.println("面积:" + rec.getArea());
	}
}