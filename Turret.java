import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class Turret extends Enemy
{
    public AudioClip wallHit;
    public Image bulletImg;
    public ArrayList<EnemyBullet> bullets = new ArrayList<EnemyBullet>();
    public int bulletTimer = 0;
    public boolean takenDamage = false;
    public Turret(Player player, double x, double y, Image img, Image bulletImg, AudioClip wallHit, AudioClip death)
    {
        super(player, 30, 10, 200, x, y, img, 30, 30, death);
        this.bulletImg = bulletImg;
        this.wallHit = wallHit;
    }
    public void act()
    {
        if (isAlive())
        {
            shoot();
            if (isTouching(player) && !takenDamage)
            {
                player.takeDamage(damage);
                health -= 15;
                takenDamage = true;
            }
            if (!isTouching(player) && takenDamage)
            {
                takenDamage = false;
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
    public void shoot()
    {
        if (bulletTimer >= 100)
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
        for (EnemyBullet b : bullets)
            b.moveRight(speed);
    }
    
    public void moveLeft(double speed)
    {
        x -= speed;
        for (EnemyBullet b : bullets)
            b.moveLeft(speed);
    }
    
    public void moveUp(double speed)
    {
        y -= speed;
        for (EnemyBullet b : bullets)
            b.moveUp(speed);
    }
    
    public void moveDown(double speed)
    {
        y += speed;
        for (EnemyBullet b : bullets)
            b.moveDown(speed);
    }
    
}