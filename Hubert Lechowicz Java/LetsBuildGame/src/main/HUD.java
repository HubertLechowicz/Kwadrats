package main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	
	public static float HEALTH = 100;
	private float greenValue = 255f;
	private static int score = 0;
	private static int level = 1;
	
	public void tick() {
		HEALTH =  Game.clamp(HEALTH, 0, 100);
		greenValue = Game.clamp(greenValue, 0,255);
		greenValue = HEALTH*2;
		
		score++;
	}
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		g.setColor(new Color(100,(int)greenValue,0));
		g.fillRect(15, 15, (int)HEALTH*2, 32);
		g.setColor(Color.white);
		g.drawRect(15, 15, 200, 32);
		g.drawString("HP: "+ HEALTH+"%", 15, 15);
		
		
		g.drawString("Score: " + score,15,65 );
		g.drawString("Level: " + level, 15, 80);
	}

	public void score(int score) {
		HUD.score = score;
	}
	public static int getScore() {
		return score;
	}
	public static int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		HUD.level = level;
	}
}
	