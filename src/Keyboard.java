import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	private static Keyboard instance;
	private final boolean[] keys = new boolean[256];


	private Keyboard() {
	}

	// singleton
	public static Keyboard getInstance() {
		if (instance == null)
			instance = new Keyboard();
		return instance;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		keys[event.getKeyCode()] = true;

	}

	@Override
	public void keyReleased(KeyEvent event) {
		keys[event.getKeyCode()] = false;

	}

	public boolean isKeyDown(int keycode) {
		return keys[keycode];
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getKeyChar());

	}


}