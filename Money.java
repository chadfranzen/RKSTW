import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class Money extends Powerup
{
    public Money(Player player, double x, double y, Image img, AudioClip powerupActivate)
    {
        super(player, x, y, img, 25, 25, powerupActivate);
    }
    public void takeEffect()
    {
        player.addScore(1000);
        active = false;
    }
}