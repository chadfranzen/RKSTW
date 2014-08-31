import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class Bullet extends GameObject
{
    public double speedX, speedY; 
    public double speedMultiplier;
    public boolean active = true;
    public int damage;
    public AudioClip wallHit;
    
    public Bullet(double x, double y, Image img, int imgWidth, int imgHeight, int damage, double speedMultiplier, AudioClip wallHit)
    {
        super(x, y, img, imgWidth, imgHeight);
        this.damage = damage;
        this.speedMultiplier = speedMultiplier;
        this.wallHit = wallHit;
    }
    
    public void act(){}
    
    public void move()
    {
        x += speedX;
        y += speedY;
        if (x < arenaX || y < arenaY || x + imgWidth > arenaX + arenaWidth || y + imgHeight > arenaY + arenaHeight)
        {
            deactivate();
            //wallHit.play();
        }
    }
    
    public void deactivate()
    {
        active = false;
    }
    
    public boolean isActive()
    {
        return active;
    }
    
}