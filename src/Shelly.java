import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;

public class Shelly extends Brawler {
	double scale;

	public Shelly(int t, int[] p) {
		super(t, p);
		maxCharge = 4620;
		reloadSpeed = 1.25;
		maxHP = 5040;
		HP = maxHP;
		scale = 2;
		img = getImage("shelly.png");
		width = 128 * 2 / 3;
		height = 256;
		// range = 7
		combatTimer = 0;
		init(p[0], p[1]);
		respawnTimer = 0;
	}

	public void shoot(ArrayList<Bullet> bullets) {
		// System.out.println("shelly shot");
		if (ammo > 0) {
			for (int i = 0; i < 5; i++) {
				bullets.add(new Bullet(team, x + 64, y + 64, 10, theta - Math.PI / 8 + (i * Math.PI / 20), 420, 2, 0));
			}
			ammo--;
			reload = reloadSpeed;
			combatTimer = 3;
		}
	}

	public void update(int fps, ArrayList<Bullet> bullets) {
		if (reload > 0) {
			reload -= 1 / (double) fps;
			if (reload <= 0) {
				ammo++;
				if (ammo != 3)
					reload = reloadSpeed;
			}
		}
		if (ammo > 3)
			ammo = 3;
		if (combatTimer > 0) {
			combatTimer -= 1 / (double) fps;
		}
		if (combatTimer <= 0) {
			heal();
		}
		if (HP <= 0) {
			HP = maxHP;
			x = xi;
			y = yi;
			ammo = 3;
			spin(0);
			init(x, y);
		}
		move();
	}

	public void runBot(ArrayList<Bullet> bullets, Brawler brl1, Brawler brl2, Brawler brl3, Safe safe) {
		Brawler tar1 = brl1;
		Brawler tar2 = brl2;
		Brawler tar3 = brl3;
		// closest
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
		// furthest
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
		if (closest == 1) {
			tar1 = brl1;
		} else if (closest == 2) {
			tar1 = brl2;
		} else if (closest == 3) {
			tar1 = brl3;
		}

		if (furthest == 1) {
			tar3 = brl1;
		} else if (furthest == 2) {
			tar3 = brl2;
		} else if (furthest == 3) {
			tar3 = brl3;
		}

		if (team != safe.team) {
			if (Math.abs(safe.getX() - x) < 100 && Math.abs(safe.getY() - y) < 100) {
				isPriority = true;
			}
		}
		if ((x - safe.getX()) * (x - safe.getX()) + (y - safe.getY()) * (y - safe.getY()) <= 400 * 400) {
			tar1 = safe;
		}
		if ((x - tar1.getX()) * (x - tar1.getX()) + (y - tar1.getY()) * (y - tar1.getY()) <= 300 * 300) {
			spin(getAngle(tar1.getX() + 64, tar1.getY() + 128));
			if (ammo == 3) {
				shoot(bullets);
			}
		} else { // if noone is in range, automove till it finds someone
			if (x > 0 && x < 1220 && y > -64 && y < 1600)
				controlMove(0, 2);

		}
		if (tar1.getX() > x + 16) {
			controlMove(2, -1);
		} else if (tar1.getX() + 16 < x) {
			controlMove(1, -1);
		} else {
			controlMove(0, -1);
		}

		if (tar1.getY() > y + 16) {
			controlMove(-1, 2);
		} else if (tar1.getY() + 16 < x) {
			controlMove(-1, 1);
		} else {
			controlMove(-1, 0);
		}
		if ((x - safe.getX()) * (x - safe.getX()) + (y - safe.getY()) * (y - safe.getY()) <= 400 * 400) {
			tar2 = safe;
		}
		if ((x - tar2.getX()) * (x - tar2.getX()) + (y - tar2.getY()) * (y - tar2.getY()) <= 300 * 300) {
			spin(getAngle(tar2.getX() + 64, tar2.getY() + 128));
			if (ammo == 3) {
				shoot(bullets);
			}
		}else { // if noone is in range, automove till it finds someone
			if (x > 0 && x < 1220 && y > -64 && y < 1600)
				controlMove(0, 2);

		}
		if (tar2.getX() > x + 16)
			controlMove(2, -1);
		else if (tar2.getX() + 16 < x)
			controlMove(1, -1);
		else
			controlMove(0, -1);

		if (tar2.getY() > y + 16)
			controlMove(-1, 2);
		else if (tar2.getY() + 16 < x)
			controlMove(-1, 1);
		else
			controlMove(-1, 0);

		if ((x - safe.getX()) * (x - safe.getX()) + (y - safe.getY()) * (y - safe.getY()) <= 400 * 400) {
			tar3 = safe;
		}
		if ((x - tar3.getX()) * (x - tar3.getX()) + (y - tar3.getY()) * (y - tar3.getY()) <= 300 * 300) {
			spin(getAngle(tar3.getX() + 64, tar3.getY() + 128));
			if (ammo == 3) {
				shoot(bullets);
			}
		}else { // if noone is in range, automove till it finds someone
			if (x > 0 && x < 1220 && y > -64 && y < 1600)
				controlMove(0, 2);

		}
		if (tar3.getX() > x + 16)
			controlMove(2, -1);
		else if (tar3.getX() + 16 < x)
			controlMove(1, -1);
		else
			controlMove(0, -1);

		if (tar3.getY() > y + 16)
			controlMove(-1, 2);
		else if (tar3.getY() + 16 < x)
			controlMove(-1, 1);
		else
			controlMove(-1, 0);
	}

	// MOVEMENT
	public void move() {
		tx.translate(vy * Math.cos(Math.PI / 2 + theta) / scale, vy * Math.sin(Math.PI / 2 + theta) / scale);
		tx.translate(vx * Math.cos(theta) / scale, vx * Math.sin(theta) / scale);
		x += vx;
		y += vy;
	}

	public void spin(double a) {
		// By @ArkyLi
		// System.out.println(theta);
		double oldangle = theta;
		theta = a;
		tx.rotate(oldangle - theta, width / 2 / scale, height / 3 / scale);
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