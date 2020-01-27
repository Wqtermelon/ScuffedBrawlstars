import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Crate {

	private int x;
	private int y;
	private int width;
	private int height;
	private Image img;

	public Crate(int paramX, int paramY, String filename) {
		x = paramX;
		y = paramY;
		width = 16;
		height = 16;
		img = getImage(filename);
		init(x, y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHieght() {
		return height;
	}

	public void setHieght(int hieght) {
		this.height = hieght;
	}

	public void setY(int y) {
		this.y = y;
	}

//Rectangle r = new Rectangle(x, y, width, height);
	private AffineTransform tx = AffineTransform.getTranslateInstance(x, y);

//draw the affinetransform
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);
	}

	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(4, 4);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Crate.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
