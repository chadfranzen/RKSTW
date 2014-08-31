import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class Seeker extends Enemy
{
    private double speedMultiplier = 1.3;
    
    public Seeker(Player player, double x, double y, Image img, AudioClip death)
    {
        super(player, 20, 15, 100, x, y, img, 20, 20, death);
    }
    
    public void act()
    {
        move();
        damageCheck();
    }
    
    public void move()
    {
        double angle = Math.atan(Math.abs((getMiddleY() - player.getMiddleY()) / (getMiddleX() - player.getMiddleX())));
        if (player.getMiddleX() > getMiddleX())
            x += speedMultiplier * Math.cos(angle);
        else
            x -= speedMultiplier * Math.cos(angle);
        if (player.getMiddleY() > getMiddleY())
            y += speedMultiplier * Math.sin(angle);
        else
            y -= speedMultiplier * Math.sin(angle);
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