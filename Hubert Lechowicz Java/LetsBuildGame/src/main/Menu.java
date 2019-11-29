package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import main.Game.STATE;

public class Menu extends MouseAdapter{
private Game game;
private Handler handler;
private Random r = new Random();



public Menu(Game game, Handler handler) {
	this.game = game;
	this.handler = handler;
}
public void mousePressed(MouseEvent e) {
	int mx = e.getX();
	int my = e.getY();
	//PLAY
	if(game.gameState == STATE.Menu){
		if(mouseOver(mx,my,220, 120, 200, 64)) {
			game.gameState = STATE.Game;
			handler.addObject(new Player(Game.WIDTH/2-32,Game.HEIGHT/2-32, ID.Player, handler));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50), ID.BasicEnemy,handler));
			return;
		}	
	}
	//HELP
	if(game.gameState == STATE.Menu && mouseOver(mx,my,220, 230, 200, 64)){
	 {
			game.gameState = STATE.Help;
		}
	}
	if(game.gameState == STATE.Help && mouseOver(mx,my,220, 340, 200, 64)){
			game.gameState=STATE.Menu;
			return;
	}
	//QUIT
	if(game.gameState == STATE.Menu && mouseOver(mx,my,220, 340, 200, 64)){
		System.exit(1);
	}	
	if(game.gameState == STATE.End && mouseOver(mx,my,230, 340, 200, 64)){
		game.gameState=STATE.Menu;
		HUD.HEALTH = 100;
		return;
		
	}	
	
}

public void mouseReleased(MouseEvent e) {
	}

private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
	if(mx > x && mx < x+width) {
		if(my > y && my < y+height) {
			return true;
		}else return false;
	}else return false;
}
public void tick() {
	
	}
public void render(Graphics g) {
 if(game.gameState == STATE.Menu) {
	Font fnt = new Font("arial",1,50);
	Font fnt2 = new Font("arial",1,30);
	g.setFont(fnt);
	g.setColor(Color.white);
	g.drawString("Menu",260,60);
	
	g.setFont(fnt2);
	g.drawString("Play",290,160);
	g.drawRect(220, 120, 200, 64);
	
	g.drawString("Help",290,270);
	g.drawRect(220, 230, 200, 64);
	
	g.drawString("Quit",290,380);
	g.drawRect(220, 340, 200, 64);
 	}
 else if(game.gameState == STATE.End) {
		Font fnt = new Font("arial",1,50);
		Font fnt2 = new Font("arial",1,30);
		g.setFont(fnt);
		g.setColor(Color.white);
		g.drawString("Game Over",200,60);
		
		g.setFont(fnt2);
		
		g.drawString("Final Score: "+ HUD.getScore()  ,230,160);
		g.drawString("Death on "+ HUD.getLevel() + " Level"  ,230,230);
		
		g.drawString("Menu",290,380);
		g.drawRect(230, 340, 200, 64);
	
	}
 else if(game.gameState == STATE.Help) {
		Font fnt = new Font("arial",1,50);
		Font fnt3 = new Font("arial",1,11);
		Font fnt2 = new Font("arial",1,30);
		g.setFont(fnt);
		g.setColor(Color.white);
		g.drawString("Help",260,90);
		
		g.setFont(fnt3);
		g.drawString("Use WSAD keys to move your white square, dodge enemies",160,220);
		g.drawString("and survive as long you can!", 240, 240);
		
		g.setFont(fnt2);
		g.drawString("Back",285,380);
		g.drawRect(220, 340, 200, 64);
}
}
}
