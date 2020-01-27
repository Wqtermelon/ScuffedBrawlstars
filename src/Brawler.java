import java.awt.Graphics;
import java.awt.Image;

public class Brawler {
	protected int team;
	protected double reloadSpeed;
	protected double reload;
	protected int maxCharge;
	protected int supCharge;
	protected int ammo;
	protected int maxHP;
	protected int HP;
	protected Image img;
	protected int x, y;

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	protected double vx, vy;
	protected double vel;
	protected double theta;
	protected int width, height;
	protected double scale;
	protected boolean showImage;
	protected int xi, yi;
	protected double combatTimer;
	protected boolean isPriority;
	protected double respawnTimer;
	protected boolean isHidden;
	protected int closest;
	protected int furthest;
	protected boolean dHitCrate;
	protected boolean rHitCrate;
	protected boolean lHitCrate;
	protected boolean uHitCrate;

	public Brawler(int t, int[] p) {
		team = t;
		x = p[0];
		y = p[1];
		xi = x;
		yi = y;
		ammo = 3;
		supCharge = 0;
		vel = 3;
		showImage = true;
		isPriority = false;
		isHidden = false;
		lHitCrate = false;
		rHitCrate = false;
		dHitCrate = false;
		rHitCrate = false;
	}

	public void heal() {
		HP += maxHP / 250;
		if (HP > maxHP)
			HP = maxHP;
	}

	public void takeDamage(int damage, int effect) {
		combatTimer = 5;
		HP -= damage;
		System.out.println("HP: " + HP);
		if (HP <= 0) {
			showImage = false;
		}
	}

	public void constrainMove(Crate[] crates) {

		for (int i = 0; i < crates.length; i++) {
			if (crates[i] == null)
				continue;
			Crate c = crates[i];
			if (x + 120 > c.getX() + 5 && x + 5 < c.getX() + 64) {
				if (y + 120 >= c.getY() && y <= c.getY() + 32 && vy > 0) {
					vy = 0;
					dHitCrate = true;

				}
				if (y + 64 >= c.getY() + 32 && y <= c.getY() + 64 && vy < 0) {
					vy = 0;
					uHitCrate = true;

				}
			}
			if (y + 120 > c.getY() + 5 && y + 5 < c.getY() + 64) {
				if (x + 120 >= c.getX() && x <= c.getX() + 32 && vx > 0) {
					vx = 0;
					rHitCrate = true;

				}
				if (x + 64 >= c.getX() + 32 && x <= c.getX() + 64 && vx < 0) {
					vx = 0;
					lHitCrate = true;

				}
			}
		}
	}

	public void hidden(Grass[] bush) {
		for (int i = 0; i < bush.length; i++) {
			if (bush[i] == null)
				continue;
			Grass c = bush[i];
			if (x + 120 > c.getX() + 5 && x + 5 < c.getX() + 64) {
				if (y + 120 >= c.getY() && y <= c.getY() + 32 && vy > 0) {
					isHidden = true;
				}
				if (y + 64 >= c.getY() + 32 && y <= c.getY() + 64 && vy < 0) {
					isHidden = true;
				}
			}
			if (y + 120 > c.getY() + 5 && y + 5 < c.getY() + 64) {
				if (x + 120 >= c.getX() && x <= c.getX() + 32 && vx > 0) {
					isHidden = true;
				}
				if (x + 64 >= c.getX() + 32 && x <= c.getX() + 64 && vx < 0) {
					isHidden = true;
				}
			}

			if (x < 0 && vx < 0) {
				vx = 0;
			}
			if (x > 1220 && vx > 0) {
				vx = 0;
			}
			if (y < -64 && vy < 0) {
				vy = 0;
			}
			if (y + 64 > 1600 && vy > 0) {
				vy = 0;
			}
		}
	}

	public void shoot() {
	};

	public void controlMove(int rl, int ud) {
		// 2 = forward
		// -1 = backwards
		// 0 = stop
		if (rl != -1) {
			if (rl == 2)
				vx = vel;
			if (rl == 1)
				vx = -vel;
			if (rl == 0)
				vx = 0;
		}
		if (ud != -1) {
			if (ud == 2)
				vy = vel;
			if (ud == 1)
				vy = -vel;
			if (ud == 0)
				vy = 0;
		}

	}

	public double getAngle(int mx, int my) {
		return Math.PI / 2 - Math.atan2(y - my + height / 2, x - mx + width / 2);
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void paint(Graphics g) {
	}

}
