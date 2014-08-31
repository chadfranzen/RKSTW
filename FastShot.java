import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class FastShot extends Powerup
{
    public int effectTimer = 1400;
    public boolean done = false;
    public FastShot(Player player, double x, double y, Image img, AudioClip powerupActivate)
    {
        super(player, x, y, img, 35, 35, powerupActivate);
    }
    public void takeEffect()
    {
        if (!done)
        {
            player.increaseBulletSpeed();
            done = true;
        }
        if (effectTimer <= 0)
        {
            player.decreaseBulletSpeed();
            active = false;
        }
        else
            effectTimer--;
    }
}
