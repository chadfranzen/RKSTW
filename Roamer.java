import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class Roamer extends Enemy
{
    public double destX, destY;
    public double angle;
    public Image bulletImg;
    private Random generator = new Random();
    public ArrayList<EnemyBullet> bullets = new ArrayList<EnemyBullet>();
    public int bulletTimer = 0;
    public AudioClip wallHit;
    
    public Roamer(Player player, double x, double y, Image img, Image bulletImg, AudioClip wallHit, AudioClip death)
    {
        super(player, 20, 10, 150, x, y, img, 30, 30, death);
        destX = (int)x;
        destY = (int)y;
        this.bulletImg = bulletImg;
        this.wallHit = wallHit;
    }
    
    public void act()
    {
        if (isAlive())
        {
            move();
            shoot();
            if (isTouching(player) && player.isAlive())
            {
                player.takeDamage(damage);
                health = 0;
            }
        }
        for (int c = 0; c < bullets.size(); c++)
        {
            if (bullets.get(c).isActive())
            {
                bullets.get(c).updateArena(arenaX, arenaY, arenaWidth, arenaHeight);
                bullets.get(c).act();    
            }
            else
            {
                bullets.remove(c);
                c--;
            }
        }
    }
    
    public void move()
    {
       if (Math.abs(x - destX) < 3 && Math.abs(y - destY) < 3)
       {
           destX = generator.nextInt((int)arenaWidth - imgWidth) + arenaX;
           destY = generator.nextInt((int)arenaHeight - imgHeight) + arenaY;
       }
       angle = Math.atan( Math.abs( (destY - y) / (destX - x) ) );
       if (destX > x)
           x += Math.cos(angle);
       else
           x -= Math.cos(angle);
       if (destY > y)
           y += Math.sin(angle);
       else
           y -= Math.sin(angle);
    }
    
    public void shoot()
    {
        if (bulletTimer >= 160)
        {
            bullets.add(new EnemyBullet(x + (imgWidth / 2) - 5, y + (imgHeight / 2) - 5, player, bulletImg, wallHit));
            bulletTimer = 0;
        }
        else
           bulletTimer++;
    }
    
    public boolean isActive()
    {
        if (isAlive())
            return true;
        else
        {
            for (EnemyBullet b : bullets)
            {
                if (b.isActive())
                    return true;
            }
            return false;
        }
    }
    
    public void draw(Graphics g)
    {
        if (isAlive())
            super.draw(g);
        for (EnemyBullet b : bullets)
            b.draw(g);
    }
    
    public void moveRight(double speed)
    {
        x += speed;
        destX += speed;
        for (EnemyBullet b : bullets)
            b.moveRight(speed);
    }
    
    public void moveLeft(double speed)
    {
        x -= speed;
        destX -= speed;
        for (EnemyBullet b : bullets)
            b.moveLeft(speed);
    }
    
    public void moveUp(double speed)
    {
        y -= speed;
        destY -= speed;
        for (EnemyBullet b : bullets)
            b.moveUp(speed);
    }
    
    public void moveDown(double speed)
    {
        y += speed;
        destY += speed;
        for (EnemyBullet b : bullets)
            b.moveDown(speed);
    }
}