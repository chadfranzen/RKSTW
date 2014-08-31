import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;

public class GameObject
{
    public double x, y;
    public Image img;
    public int imgWidth, imgHeight;
    public double arenaX, arenaY, arenaWidth, arenaHeight;
    public GameObject(double x, double y, Image img, int imgWidth, int imgHeight)
    {
        this.x = x;
        this.y = y;
        this.img = img;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
    }
    
    public void draw(Graphics g)
    {
        g.drawImage(img, (int)x, (int)y, imgWidth, imgHeight, null);
    }
    
    public void updateArena(double arenaX, double arenaY, double arenaWidth, double arenaHeight)
    {
        this.arenaX = arenaX;
        this.arenaY = arenaY;
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;
    }
    
    public boolean isTouching(GameObject o)
    {
        boolean isTouching = false;
        if ( ( x > o.getX() ) && ( x < o.getX() + o.getWidth() ) )
        {
            if ((y + imgHeight > o.getY()) && (y + imgHeight < o.getY() + o.getHeight()))
                isTouching = true;
            if ((y < o.getY() + o.getHeight()) && (y > o.getY()))
                isTouching = true;
        }
        if ((x + imgWidth > o.getX()) && (x + imgWidth < o.getX() + o.getWidth()))
        {
            if ((y + imgHeight > o.getY()) && (y + imgHeight < o.getY() + o.getHeight()))
                isTouching = true;
            if ((y < o.getY() + o.getHeight()) && (y > o.getY()))
                isTouching = true;
        }
        return isTouching;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public double getMiddleX()
    {
        return x + (imgWidth / 2);
    }
    
    public double getMiddleY()
    {
        return y + (imgHeight / 2);
    }
    
    public int getWidth()
    {
        return imgWidth;
    }
    
    public int getHeight()
    {
        return imgHeight;
    }
    
    public void moveRight(double speed)
    {
        x += speed;
    }
    
    public void moveLeft(double speed)
    {
        x -= speed;
    }
    
    public void moveUp(double speed)
    {
        y -= speed;
    }
    
    public void moveDown(double speed)
    {
        y += speed;
    }
}