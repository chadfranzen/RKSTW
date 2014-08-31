import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class Player extends GameObject
{
    public double speed;
    public int maxHealth, health;
    public int score = 0;
    public int shotType = 1;
    public ArrayList<PlayerBullet> bullets = new ArrayList<PlayerBullet>();

    public Image bulletImg;
    public AudioClip playerShoot, hurt, hit, wallHit;
    
    public double bulletTimer = 10, bulletCounter = 0;
    public Player(double x, double y, Image img, int imgWidth, int imgHeight, Image bulletImg, AudioClip playerShoot, AudioClip hurt, AudioClip hit, AudioClip wallHit)
    {
        super(x, y, img, imgWidth, imgHeight);
        speed = 1.5;
        this.bulletImg = bulletImg;
        this.playerShoot = playerShoot;
        this.hurt = hurt;
        maxHealth = 100;
        health = maxHealth;
        this.hit = hit;
        this.wallHit = wallHit;
    }
    
    public void act(boolean up, boolean down, boolean left, boolean right, boolean shootUp, boolean shootDown, boolean shootLeft, boolean shootRight, ArrayList<Enemy> enemies)
    {
        move(up, down, left, right);
        
        for (int c = 0; c < bullets.size(); c++)
        {
            if (bullets.get(c).isActive())
            {
                bullets.get(c).updateArena(arenaX, arenaY, arenaWidth, arenaHeight);
                bullets.get(c).act(enemies);    
            }
            else
            {
                bullets.remove(c);
                c--;
            }
        }
        
        shoot(shootUp, shootDown, shootLeft, shootRight);
    }
    
    public void move(boolean up, boolean down, boolean left, boolean right)
    {
        if (up)
        {
            if (y - speed > arenaY)
                y -= speed;
            else
                y = arenaY;
        }
        if (down)
        {
            if (y + imgHeight + speed < arenaY + arenaHeight)
                y += speed;
            else
                y = arenaY + arenaHeight - imgHeight;
        }
        if (left)
        {
            if (x - speed > arenaX)
                x -= speed;
            else 
                x = arenaX;
        }
        if (right)
        {
            if (x + imgWidth + speed < arenaX + arenaWidth)
                x += speed;
            else
                x = arenaX + arenaWidth - imgWidth;
        }
    }
    
    public void shoot(boolean up, boolean down, boolean left, boolean right)
    {
        if (bulletCounter >= bulletTimer)
        {
            if (!(up && down) && !(left && right) && (up || down || left || right))
            {
                bullets.add(new PlayerBullet(x + ((double)imgWidth / 2) - 5, y + ((double)imgHeight / 2) - 5, up, down, left, right, bulletImg, hit, wallHit));
                if (shotType == 2)
                {
                    bullets.add(new PlayerBullet(x + ((double)imgWidth / 2) - 5, y + ((double)imgHeight / 2) - 5, up, down, left, right, bulletImg, hit, wallHit, "upper"));
                    bullets.add(new PlayerBullet(x + ((double)imgWidth / 2) - 5, y + ((double)imgHeight / 2) - 5, up, down, left, right, bulletImg, hit, wallHit, "lower"));
                }
                playerShoot.play();
                bulletCounter = 0;
            }
        }
        else
            bulletCounter++;
    }
    
    public void takeDamage(int dmg)
    {
        health -= dmg;
        hurt.play();
    }
    
    public void draw(Graphics g)
    {
        for (PlayerBullet b : bullets)
            b.draw(g);
        super.draw(g);
    }
    
    public boolean isAlive()
    {
        return health > 0;
    }
    
    public double getSpeed()
    {
        return speed;
    }
    
    public int getHealth()
    {
        return health;
    }
    
    public double getPercentHealth()
    {
        return (double)health / maxHealth;
    }
    
    public void increaseHealth(int amt)
    {
        if (health + amt > maxHealth)
            health = maxHealth;
        else
            health += amt;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public void addScore(int s)
    {
        score += s;
    }
    
    public void changeShotType(int num)
    {
        shotType = num;
    }
    
    public void increaseBulletSpeed()
    {
        bulletTimer /= 2;
    }
    
    public void decreaseBulletSpeed()
    {
        bulletTimer *= 2;
    }
    
    public void moveRight()
    {
        x += speed;
        for (Bullet b: bullets)
            b.moveRight(speed);
    }
    
    public void moveLeft()
    {
        x -= speed;
        for (Bullet b: bullets)
            b.moveLeft(speed);
    }
    
    public void moveUp()
    {
        y -= speed;
        for (Bullet b: bullets)
            b.moveUp(speed);
    }
    
    public void moveDown()
    {
        y += speed;
        for (Bullet b: bullets)
            b.moveDown(speed);
    }
}