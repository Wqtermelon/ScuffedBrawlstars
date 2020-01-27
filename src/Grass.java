import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Grass {
private int x;
private int y;
private int width;
private int height;
private Image img;
public Grass(int paramX, int paramY, String filename){
	x = paramX;
	y = paramY;
	width = 16;
	height = 16;
	img = getImage(filename);
	init(x,y);
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

public void setY(int y) {
	this.y = y;
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
public Image getImg() {
	return img;
}

public void setImg(Image img) {
	this.img = img;
}
private AffineTransform tx = AffineTransform.getTranslateInstance(x, y);

// draw the affinetransform
public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;	float alpha = (float) 0.7;
	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
	g2.setComposite(ac);
	
	g2.drawImage(img, tx, null);
	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));

}
protected void init(double a, double b) {
	tx.setToTranslation(a, b);
	tx.scale(4, 4);
}
private Image getImage(String path) {
	Image tempImage = null;
	try {
		URL imageURL = Grass.class.getResource(path);
		tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return tempImage;
}
	
}
