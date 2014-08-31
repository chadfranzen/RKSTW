import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class Spawner extends Enemy
{
    public double speedMultiplier = 1.5;
    public double speedX, speedY;
    private Random generator = new Random();
    public Spawner(Player player, double x, double y, Image img, AudioClip death)
    {
        super(player, 35, 10, 100, x, y, img, 30, 40, death);
        speedX = (generator.nextDouble() * 2) - 1;
        speedY = Math.sqrt(1 - Math.pow(speedX, 2));
        if (generator.nextInt(2) == 0)
            speedY *= -1;
        speedX *= speedMultiplier;
        speedY *= speedMultiplier;
    }
    public void act()
    {
        if (isAlive())
        {
            move();
            damageCheck();
        }
    }
    public void move()
    {
        x += speedX;
        y += speedY;
        if (x <= arenaX || x + imgWidth >= arenaX + arenaWidth)
            speedX *= -1;
        if (y <= arenaY || y + imgHeight >= arenaY + arenaHeight)
            speedY *= -1;
    }
    public void damageCheck()
    {
        if (isTouching(player) && player.isAlive())
        {
            player.takeDamage(damage);
            health = 0;
        }
    }
}