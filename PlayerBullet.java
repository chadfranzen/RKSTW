import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class PlayerBullet extends Bullet
{
    public AudioClip hit;
    public PlayerBullet(double x, double y, boolean up, boolean down, boolean left, boolean right, Image img, AudioClip hit, AudioClip wallHit)
    {
        super(x, y, img, 10, 10, 7, 3, wallHit);
        this.hit = hit;
        if (up && left)
        {
            speedX = -(Math.sqrt(2) / 2);
            speedY = -(Math.sqrt(2) / 2);
        }
        else if (up && right)
        {
            speedX = (Math.sqrt(2) / 2);
            speedY = -(Math.sqrt(2) / 2);
        }
        else if (down && right)
        {
            speedX = (Math.sqrt(2) / 2);
            speedY = (Math.sqrt(2) / 2);
        }
        else if (down && left)
        {
            speedX = -(Math.sqrt(2) / 2);
            speedY = (Math.sqrt(2) / 2);
        }
        else if (left)
            speedX = -1;
        else if (right)
            speedX = 1;
        else if (up)
            speedY = -1;
        else if (down)
            speedY = 1;
            
        speedX *= speedMultiplier;
        speedY *= speedMultiplier;
    }
    
    public PlayerBullet(double x, double y, boolean up, boolean down, boolean left, boolean right, Image img, AudioClip hit, AudioClip wallHit, String s)
    {
        super(x, y, img, 10, 10, 7, 3, wallHit);
        this.hit = hit;
        if (up && left)
        {
            if (s.equals("upper"))
                speedY = -1;
            if (s.equals("lower"))
                speedX = -1;
        }
        else if (up && right)
        {
            if (s.equals("upper"))
                speedY = -1;
            if (s.equals("lower"))
                speedX = 1;
        }
        else if (down && right)
        {
            if (s.equals("upper"))
                speedX = 1;
            if (s.equals("lower"))
                speedY = 1;
        }
        else if (down && left)
        {
            if (s.equals("upper"))
                speedX = -1;
            if (s.equals("lower"))
                speedY = 1;
        }
        else if (left)
        {
            if (s.equals("upper"))
            {
                speedX = -(Math.sqrt(2) / 2);
                speedY = -(Math.sqrt(2) / 2);
            }
            if (s.equals("lower"))
            {
                speedX = -(Math.sqrt(2) / 2);
                speedY = (Math.sqrt(2) / 2);
            }
        }
        else if (right)
        {
            if (s.equals("upper"))
            {
                speedX = (Math.sqrt(2) / 2);
                speedY = -(Math.sqrt(2) / 2);
            }
            if (s.equals("lower"))
            {
                speedX = (Math.sqrt(2) / 2);
                speedY = (Math.sqrt(2) / 2);
            }
        }
        else if (up)
        {
            if (s.equals("upper"))
            {
                speedX = -(Math.sqrt(2) / 2);
                speedY = -(Math.sqrt(2) / 2);
            }
            if (s.equals("lower"))
            {
                speedX = (Math.sqrt(2) / 2);
                speedY = -(Math.sqrt(2) / 2);
            }
        }
        else if (down)
        {
            if (s.equals("upper"))
            {
                speedX = -(Math.sqrt(2) / 2);
                speedY = (Math.sqrt(2) / 2);
            }
            if (s.equals("lower"))
            {
                speedX = (Math.sqrt(2) / 2);
                speedY = (Math.sqrt(2) / 2);
            }
        }
        
        speedX *= speedMultiplier;
        speedY *= speedMultiplier;
    }
    
    public void act(ArrayList<Enemy> enemies)
    {
        move();
        damageCheck(enemies);
    }
    
    public void damageCheck(ArrayList<Enemy> enemies)
    {
        for (Enemy e : enemies)
        {
            if (e.isAlive())
            {
                if (isTouching(e))
                {
                    e.takeDamage(damage);
                    active = false;
                    //hit.play();
                }
            }
        }
    }
}