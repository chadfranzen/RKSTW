import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class EnemyBullet extends Bullet
{
    public Player player;
    public EnemyBullet(double x, double y, Player player, Image img, AudioClip wallHit)
    {
        super(x, y, img, 10, 10, 10, 2, wallHit);
        this.player = player;
        
        speedMultiplier = 2;
        
        double targetX = player.getX() + (player.getWidth() / 2);
        double targetY = player.getY() + (player.getHeight() / 2);
        
        double angle = Math.atan( Math.abs( (targetY - y) / (targetX - x) ) );
        if (targetX > x)
            speedX = Math.cos(angle);
        else
            speedX = -Math.cos(angle);
        if (targetY > y)
            speedY = Math.sin(angle);
        else
            speedY = -Math.sin(angle);
            
        speedX *= speedMultiplier;
        speedY *= speedMultiplier;
    }
    
    public void act()
    {
        move();
        damageCheck();
    }
    
    public void damageCheck()
    {
        if (!player.isAlive())
            return;
        if (isTouching(player))
        {
            player.takeDamage(damage);
            active = false;
        }
    }
}