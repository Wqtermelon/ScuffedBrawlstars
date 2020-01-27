import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;

public class Bea extends Brawler {
	double scale;
	boolean charged;
	public Bea(int t, int[] p) {
		super(t, p);
		reloadSpeed = 0.9;
		maxHP = 3360;
		HP = maxHP;
		scale = 2;
		width = 128;
		height = 128;
		img = getImage("bea.png");
		combatTimer =0;
		init(p[0], p[1]);
	}

	public void shoot(ArrayList<Bullet> bullets) {
	//	System.out.println("bea shot");
		if (ammo > 0) {
			if (charged == true) {
				bullets.add(new Bullet(team, x + 57, y + 60, 16, theta, 3080, 2, 0));
				System.out.println("Bea CHARGE");
			} else {
				bullets.add(new Bullet(team, x + 57, y + 60, 16, theta, 1120, 2, 0));
			}
			ammo--;
			reload = reloadSpeed;
			combatTimer = 3;
			charged= !charged;
		}
	}

	public void update(int fps, ArrayList<Bullet> bullets){
		if (combatTimer>0){
			combatTimer-=1/(double)fps;
		}
		if (combatTimer<=0){
			heal();
		}
		if (reload>0){
			reload-=1/(double)fps;
			if (reload<=0){
				ammo++;
				if (ammo != 3) reload=reloadSpeed;
			}
		}
		if (ammo>3) ammo = 3;
		if (charged == true) {
			img = getImage("beaC.png");
		} else {
			img = getImage("bea.png");
		}
		if (HP<=0){
			HP = maxHP;
			x = xi;
			y = yi;
			spin(0);
			ammo = 3;
			init(x,y);
		}
		move();
	}
	//bot
	public void runBot(ArrayList<Bullet> bullets, Brawler brl1,Brawler brl2, Brawler brl3, Safe safe) {
		Brawler tar1 = brl1;
		Brawler tar2 = brl2;
		Brawler tar3 = brl3;
		//closest
		if (Math.abs(brl1.getX() - x) < Math.abs(brl2.getX() - x)
				&& Math.abs(brl1.getY() - y) < Math.abs(brl2.getY() - y)) {
			closest = 1;
		} else if (Math.abs(brl2.getX() - x) < Math.abs(brl3.getX() - x)
				&& Math.abs(brl2.getY() - y) < Math.abs(brl3.getY() - y)) {
			closest = 2;
		} else if (Math.abs(brl3.getX() - x) < Math.abs(brl1.getX() - x)
				&& Math.abs(brl3.getY() - y) < Math.abs(brl1.getY() - y)) {
			closest = 3;
		}
		//furthest
		if (Math.abs(brl1.getX() - x) > Math.abs(brl2.getX() - x)
				&& Math.abs(brl1.getY() - y) > Math.abs(brl2.getY() - y)) {
			furthest = 1;
		} else if (Math.abs(brl2.getX() - x) > Math.abs(brl3.getX() - x)
				&& Math.abs(brl2.getY() - y) > Math.abs(brl3.getY() - y)) {
			furthest = 2;
		} else if (Math.abs(brl3.getX() - x) > Math.abs(brl1.getX() - x)
				&& Math.abs(brl3.getY() - y) > Math.abs(brl1.getY() - y)) {
			furthest = 3;
		}
		if(closest ==1) {
			 tar1 = brl1;
		} else if(closest == 2) {
			 tar1 = brl2;
		} else if(closest == 3) {
			 tar1 = brl3;
		}

		if(furthest ==1) {
			 tar3 = brl1;
		} else if(furthest == 2) {
			 tar3 = brl2;
		} else if(furthest == 3) {
			 tar3 = brl3;
		}

		if(team !=safe.team ) {
			if(Math.abs(safe.getX()-x)<100&&Math.abs(safe.getY()-y)<100) {
				isPriority = true;
			}
		}

		if ((x - safe.getX()) * (x - safe.getX()) + (y - safe.getY()) * (y - safe.getY()) <= 300 * 300) {
			tar1= safe;
		}
		if ((x - tar1.getX()) * (x - tar1.getX()) + (y - tar1.getY()) * (y - tar1.getY()) <= 600 * 600) {
			spin(getAngle(tar1.getX() + 64, tar1.getY() + 64));
			if (ammo == 3) {
				shoot(bullets);
			}
		}else { // if noone is in range, automove till it finds someone
			if (x > 0 && x < 1220 && y > -64 && y < 1600)
				controlMove(0, 2);

		}
		if (tar1.getX() > x + 256)
			controlMove(2, -1);
		else if (tar1.getX() + 256 < x)
			controlMove(1, -1);
		else
			controlMove(0, -1);

		if (tar1.getY() > y + 256)
			controlMove(-1, 2);
		else if (tar1.getY() + 256 < x)
			controlMove(-1, 1);
		else
			controlMove(-1, 0);
		if ((x - safe.getX()) * (x - safe.getX()) + (y - safe.getY()) * (y - safe.getY()) <= 300 * 300) {
			tar2= safe;
		}
		if ((x - tar2.getX()) * (x - tar2.getX()) + (y - tar2.getY()) * (y - tar2.getY()) <= 600 * 600) {
			spin(getAngle(tar2.getX() + 64, tar2.getY() + 64));
			if (ammo == 3) {
				shoot(bullets);
			}
		}else { // if noone is in range, automove till it finds someone
			if (x > 0 && x < 1220 && y > -64 && y < 1600)
				controlMove(0, 2);

		}
		if (tar2.getX() > x + 256)
			controlMove(2, -1);
		else if (tar2.getX() + 256 < x)
			controlMove(1, -1);
		else
			controlMove(0, -1);

		if (tar2.getY() > y + 256)
			controlMove(-1, 2);
		else if (tar2.getY() + 256 < x)
			controlMove(-1, 1);
		else
			controlMove(-1, 0);

		if ((x - safe.getX()) * (x - safe.getX()) + (y - safe.getY()) * (y - safe.getY()) <= 300 * 300) {
			tar3= safe;
		}
		if ((x - tar3.getX()) * (x - tar3.getX()) + (y - tar3.getY()) * (y - tar3.getY()) <= 600 * 600) {
			spin(getAngle(tar3.getX() + 64, tar3.getY() + 64));
			if (ammo == 3) {
				shoot(bullets);
			}
		}else { // if noone is in range, automove till it finds someone
			if (x > 0 && x < 1220 && y > -64 && y < 1600)
				controlMove(0, 2);

		}
		if (tar3.getX() > x + 256)
			controlMove(2, -1);
		else if (tar3.getX() + 256 < x)
			controlMove(1, -1);
		else
			controlMove(0, -1);

		if (tar3.getY() > y + 256)
			controlMove(-1, 2);
		else if (tar3.getY() + 256 < x)
			controlMove(-1, 1);
		else
			controlMove(-1, 0);
	}
	
	// MOVEMENT
	public void move() {
		tx.translate(vy * Math.cos(Math.PI / 2 + theta) / scale,
				vy * Math.sin(Math.PI / 2 + theta) / scale);
		tx.translate(vx * Math.cos(theta) / scale, vx * Math.sin(theta) / scale);
		x += vx;
		y += vy;
	}

	public void spin(double a) {
		// By @ArkyLi
		double oldangle = theta;
		theta = a;
		tx.rotate(oldangle - theta, width / 2 / scale, height / 2 / scale);
	}

	// DRAWING
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