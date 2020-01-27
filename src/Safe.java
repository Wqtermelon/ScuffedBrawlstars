
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;



public class Safe extends Brawler {
	double scale;
	public Safe(int t, int[] p) {
		super(t, p);

		maxHP = 40000;
		HP = maxHP;
		scale = 6;
		img = getImage("safe.png");
		width = 128;
		height = 128;
		init(p[0],p[1]);
	}
	
			//DRAWING
			private AffineTransform tx = AffineTransform.getTranslateInstance(x, y);

			// draw the affinetransform
				public void paint(Graphics g) {
					Graphics2D g2 = (Graphics2D) g;

					g2.drawImage(img, tx, null);
					
				}


				protected void init(double a, double b) {
					tx.setToTranslation(a, b);
					tx.scale(scale, scale);
				}

				// converts image to make it drawable in paint
				protected Image getImage(String path) {
					Image tempImage = null;
					try {
						URL imageURL = Shelly.class.getResource(path);
						tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
					} catch (Exception e) {
						e.printStackTrace();
					}
				return tempImage;
				}
	
}
