package main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {
	
	
	private static final long serialVersionUID = 6691247796639148462L;
	
	public static final int WIDTH =640,HEIGHT = WIDTH/12*9;
	
	private Thread thread;
	private boolean running = false;
	
	private Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	
	public enum STATE {
		Menu,
		Help,
		End,
		Game
	};
	public STATE gameState = STATE.Menu;
	public Game() {
		handler = new Handler();
		menu = new Menu(this, handler);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		
		
		new Window(WIDTH,HEIGHT,"Gra  kwadratem w unikanie kwadratów",this);
		
		hud = new HUD();
		spawner = new Spawn(handler,hud);
		menu = new Menu(this, handler);
		r = new Random();
		if(gameState == STATE.Game)
		{
			handler.addObject(new Player(WIDTH/2-32,HEIGHT/2-32, ID.Player, handler));
			handler.addObject(new BasicEnemy(r.nextInt(WIDTH-50),r.nextInt(HEIGHT-50), ID.BasicEnemy,handler));
		}	
			
			
	}
	
	public synchronized void start(){
	thread = new Thread(this);
	thread.start();
	running = true;
	}
	
	public synchronized void stop(){
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
			}
	}
	
	public void run() {
		this.requestFocus(); // przy uruchomieniu okienko z aplikacj¹ zdobywa atencje 
		long lastTime = System.nanoTime(); // zwraca czas od uruchomienia w nano sekundach
		double amountOfTicks = 60.0; // Iloœæ FPS jaka ma byæ renderowana
		double ns = 1000000000 / amountOfTicks; // Iloœæ nanosekund na tick, 1/60 sekundy przypada na 1 tick, 60 ticków w sekundzie 
		double delta = 0; // deklaracja ró¿nicy pomiedzy czasami
		long timer = System.currentTimeMillis(); // zwraca obecny czas w milisekundach od  pó³nocy 1 stycznia 1970r
		int frames = 0; // licznik klatek na sekudne
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) /ns; // róznica czasu od uruchomiania i poprzednio sprawdzonego czasu od uruchomienia * 60
			lastTime = now;
			while(delta>=1) {
				tick(); // wywo³anie metody tick
				delta--;
				render(); // wywo³anie metody render
				frames++;
			}			
			if(System.currentTimeMillis()- timer > 1000) { // Jeœli ró¿nica teraz i timera > 1 sekundy
				timer += 1000; // zwiekszenie timera o 1 sekunde
				System.out.println("FPS: " + frames); // wypisanie ilosci wyrenderowanych klatek na sekunde
				frames = 0; // zresetowanie licznika klatek na sekudne
			}
		}
		stop();
		
	}
	
	private void tick() {
		handler.tick();
		if(gameState==STATE.Game)
			{
			hud.tick();
			spawner.tick();
			}else if(gameState == STATE.Menu) {
				menu.tick();
			}
		if(HUD.HEALTH == 0) {
			handler.clearEverything();
			gameState = STATE.End;
		}
			
		}
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		if(gameState == STATE.Game) 
			{
			hud.render(g);
			}else if(gameState == STATE.Menu|| gameState == STATE.Help|| gameState==STATE.End) {
				menu.render(g);
			}
		g.dispose();
		bs.show();
		
	}
	public static float clamp(float var,float min, float max) {
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		else
			return var;
	}
	public static void main(String args[]) {
		new Game();
	}
}
