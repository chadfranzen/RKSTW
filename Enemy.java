import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class Enemy extends GameObject
{
    public int health, maxHealth;
    public int damage;
    public int score;
    public Player player;
    public AudioClip death;
    public Enemy(Player player, int maxHealth, int damage, int score, double x, double y, Image img, int imgWidth, int imgHeight, AudioClip death)
    {
        super(x, y, img, imgWidth, imgHeight);
        this.maxHealth = maxHealth;
        health = maxHealth;
        this.score = score;
        this.damage = damage;
        this.player = player;
        this.death = death;
    }
    public boolean isAlive()
    {
        return (health > 0);
    }
    public boolean isActive()
    {
        return isAlive();
    }
    public void takeDamage(int dmg)
    {
        health -= dmg;
        if (health <= 0)
        {
            player.addScore(score);
            death.play();
        }
    }
    public void draw(Graphics g)
    {
        super.draw(g);
        g.setColor(Color.RED);
        g.fillRect((int)(x - 10), (int)(y + imgHeight), (int)((((double)health) / maxHealth) * (imgWidth + 20)), 5);
        g.setColor(Color.BLACK);
        g.drawRect((int)(x - 10), (int)(y + imgHeight), imgWidth + 20, 5);
    }
    public void act() {}
}