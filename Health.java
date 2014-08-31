import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class Health extends Powerup
{
    public Health(Player player, double x, double y, Image img, AudioClip powerupActivate)
    {
        super(player, x, y, img, 25, 25, powerupActivate);
    }
    public void takeEffect()
    {
        player.increaseHealth(30);
        active = false;
    }
}