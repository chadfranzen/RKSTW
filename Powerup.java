import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class Powerup extends GameObject
{
    public Player player;
    public int timer = 800;
    public boolean onField = true, active = false;
    public AudioClip powerupActivate;
    
    public Powerup(Player player, double x, double y, Image img, int imgWidth, int imgHeight, AudioClip powerupActivate)
    {
        super(x, y, img, imgWidth, imgHeight);
        this.player = player;
        this.powerupActivate = powerupActivate;
    }
    
    public void act()
    {
        if (timer > 0 && onField)
        {
            if (isTouching(player))
            {
                onField = false;
                active = true;
                powerupActivate.play();
            }
            else
                timer--;
        }
        else
            onField = false;
        if (active)
            takeEffect();
    }
    
    public boolean isFinished()
    {
        return (!onField && !active);
    }
    
    public void draw(Graphics g)
    {
        if (onField)
            super.draw(g);
    }
    
    public void takeEffect()
    {
        active = false;
    }
}