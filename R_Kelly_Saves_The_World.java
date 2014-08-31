import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;


public class R_Kelly_Saves_The_World extends Applet implements KeyListener, Runnable, MouseListener, MouseMotionListener
{
    Random generator = new Random();
    Thread mythread;
    public boolean game = true;
    public int screenX = 800, screenY = 600, speed = 10;
    public Image img;
    public Graphics buffer;
    public Player player;
    
    public Image rum, kelly, camera, gavel, enemyBullet, playerBullet, record, kardashian, kanye, money, microphone, cigar, title;
    public AudioClip playerShoot, hurt, hit, wallHit, death, newWave, powerupActivate, powerupSpawn;
    
    public boolean left, right, up, down, shootLeft = false, shootRight = false, shootUp = false, shootDown = false;
    public final double deadZone = 0.3;
    
    public int waveTimer = 0;
    public int powerupTimer = 0;
    
    public ArrayList<Enemy> enemies;
    public ArrayList<Powerup> powerups;
    
    public double arenaX = -(screenX * 0.2), arenaY = -(screenY * 0.2), arenaWidth = (1.4 * screenX), arenaHeight = (1.4 * screenY);
    
    public int fps = 0, fpsCounter = 0;
    public long timer = System.currentTimeMillis();
    public int highScore = 0;
    public boolean playing = false;
    
    public void init()
    {
        setBackground (Color.white);
        setSize(screenX, screenY);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        img = createImage(screenX, screenY);
        buffer = img.getGraphics();
        
        rum = getImage(getDocumentBase(), "rum.jpg");
        record = getImage(getDocumentBase(), "record.jpg");
        kardashian = getImage(getDocumentBase(), "kardashian.jpg");
        kelly = getImage(getDocumentBase(), "kellyFace.jpg");
        enemyBullet = getImage(getDocumentBase(), "enemyBullet.png");
        playerBullet = getImage(getDocumentBase(), "playerBullet.png");
        kanye = getImage(getDocumentBase(), "kanye.jpg");
        camera = getImage(getDocumentBase(), "camera.jpg");
        gavel = getImage(getDocumentBase(), "gavel.jpg");
        money = getImage(getDocumentBase(), "money.jpg");
        microphone = getImage(getDocumentBase(), "microphone.jpg");
        cigar = getImage(getDocumentBase(), "cigar.jpg");
        title = getImage(getDocumentBase(), "title.jpg");
        
        playerShoot = getAudioClip(getDocumentBase(), "playerShoot.wav");
        hurt = getAudioClip(getDocumentBase(), "hurt.wav");
        hit = getAudioClip(getDocumentBase(), "hit.wav");
        wallHit = getAudioClip(getDocumentBase(), "wallHit.wav");
        death = getAudioClip(getDocumentBase(), "death.wav");
        newWave = getAudioClip(getDocumentBase(), "newWave.wav");
        powerupActivate = getAudioClip(getDocumentBase(), "powerupActivate.wav");
        powerupSpawn = getAudioClip(getDocumentBase(), "powerupSpawn.wav");
        
        enemies = new ArrayList<Enemy>();
        powerups = new ArrayList<Powerup>();
        player = new Player(400, 300, kelly, 30, 30, playerBullet, playerShoot, hurt, hit, wallHit);       
    }
    
    public void start() 
    {
        mythread = new Thread(this);
        mythread.start();
    }

    public void run()
    {
        while(game == true)
        {        
            try 
            {
                Thread.sleep(speed); 
            } 
            catch (InterruptedException e) { ; }
            repaint();
        }
    }
    
    public void mouseClicked (MouseEvent e)
    {    
        if (!player.isAlive() && playing)
            playing = false;
        else if (!playing)
        {
            playing = true;
            enemies = new ArrayList<Enemy>();
            powerups = new ArrayList<Powerup>();
            player = new Player(400, 300, kelly, 30, 30, playerBullet, playerShoot, hurt, hit, wallHit);
        }
    }               
    public void mouseReleased (MouseEvent e){  }
    public void mouseEntered (MouseEvent e){  }
    public void mouseExited (MouseEvent e){  }      
    public void mousePressed (MouseEvent e){  }
    public void mouseDragged (MouseEvent e){  }  
    public void mouseMoved (MouseEvent e){  }
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W: up = true;
                                break;
            case KeyEvent.VK_A: left = true;
                                break;
            case KeyEvent.VK_S: down = true;
                                break;
            case KeyEvent.VK_D: right = true;
                                break;
            case KeyEvent.VK_I: shootUp = true;
                                break;
            case KeyEvent.VK_J: shootLeft = true;
                                break;
            case KeyEvent.VK_K: shootDown = true;
                                break;
            case KeyEvent.VK_L: shootRight = true;
                                break;
        }
    }
    public void keyReleased(KeyEvent e){ 
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W: up = false;
                                break;
            case KeyEvent.VK_A: left = false;
                                break;
            case KeyEvent.VK_S: down = false;
                                break;
            case KeyEvent.VK_D: right = false;
                                break;
            case KeyEvent.VK_I: shootUp = false;
                                break;
            case KeyEvent.VK_J: shootLeft = false;
                                break;
            case KeyEvent.VK_K: shootDown = false;
                                break;
            case KeyEvent.VK_L: shootRight = false;
                                break;                    
        }
    }
    public void keyTyped(KeyEvent e){  }

    public void paint (Graphics page)
    {
        super.paint(buffer);
        Graphics2D buffer2 = (Graphics2D)buffer;
        buffer.setColor(Color.WHITE);
        buffer.fillRect(0, 0, screenX, screenY);
        
        if (playing)
        {
            drawArena(buffer);
            
            if (player.isAlive())
                player.draw(buffer);
            
            for (Enemy e: enemies)
                e.draw(buffer);
                
            for (Powerup p: powerups)
                p.draw(buffer);
    
            drawUI(buffer);
        }
        else
        {
            buffer.drawImage(title, 0, 0, 797, 597, this);
        }
            
        showStatus("" + fps + " " + player.getHealth());
        
        page.drawImage(img,0,0,this);
    }
    public void update(Graphics page)
    {       
        if (System.currentTimeMillis() - timer > 1000)
        {
            timer = System.currentTimeMillis();
            fps = fpsCounter;
            fpsCounter = 0;
        }
        
        if (playing)
        {
            moveScreen();
            
            if (waveTimer > 500)
            {
                if (generator.nextInt(50) == 0)
                {
                    generateWave();
                    waveTimer = 0;
                }
            }
            else
                waveTimer++;
            
            if (player.isAlive())
            {
                player.updateArena(arenaX, arenaY, arenaWidth, arenaHeight);
                player.act(up, down, left, right, shootUp, shootDown, shootLeft, shootRight, enemies);
            }
            
            for (int c = 0; c < enemies.size(); c++)
            {
                Enemy e = enemies.get(c);
                e.updateArena(arenaX, arenaY, arenaWidth, arenaHeight);
                e.act();
                if (!e.isActive())
                {
                    if (e instanceof Spawner)
                    {
                        spawnChildren(e);
                    }
                    enemies.remove(c);
                    c--;
                }
            }
            
            if (powerupTimer >= 1000)
            {
                spawnPowerup();
                powerupTimer = 0;
            }
            else
                powerupTimer++;
                
            for (int c = 0; c < powerups.size(); c++)
            {
                if (powerups.get(c).isFinished())
                {
                    powerups.remove(c);
                    c--;
                }
                else
                    powerups.get(c).act();
            }
        }
        
        paint(page);
        
        fpsCounter++;
    }
    public void generateWave()
    {
        int waveType = generator.nextInt(4);
        if (waveType == 0)
        {
            for (int c = 0; c < 9; c++)
                enemies.add(new Roamer(player, genX(), genY(), record, enemyBullet, wallHit, death));
        }
        if (waveType == 1)
        {
            for (int c = 0; c < 10; c++)
                enemies.add(new Seeker(player, genX(), genY(), camera, death));
        }
        if (waveType == 2)
        {
            for (int c = 0; c < 7; c++)
                enemies.add(new Spawner(player, genX(), genY(), kardashian, death));
        }
        if (waveType == 3)
        {
            for (int c = 0; c < 3; c++)
                enemies.add(new Turret(player, genX(), genY(), gavel, enemyBullet, wallHit, death));
        }
        newWave.play();
    }
    public void spawnPowerup()
    {
        int num = generator.nextInt(13);
        if (num < 3)
            powerups.add(new Health(player, genPowerupX(), genPowerupY(), rum, powerupActivate));
        else if (num < 5)
            powerups.add(new Money(player, genPowerupX(), genPowerupY(), money, powerupActivate));
        else if (num < 8)
            powerups.add(new FastShot(player, genPowerupX(), genPowerupY(), microphone, powerupActivate));
        else if (num < 11)
            powerups.add(new SpreadShot(player, genPowerupX(), genPowerupY(), cigar, powerupActivate));
        else
            return;
        powerupSpawn.play();
    }
    public void moveScreen()
    {
        if (player.getX() < deadZone * screenX)
        {
            player.moveRight();
            arenaX += player.getSpeed();
            for (Enemy e : enemies)
                e.moveRight(player.getSpeed());
            for (Powerup p : powerups)
                p.moveRight(player.getSpeed());
        }
        if (player.getX() > (1 - deadZone) * screenX)
        {
            player.moveLeft();
            arenaX -= player.getSpeed();
            for (Enemy e: enemies)
                e.moveLeft(player.getSpeed());
            for (Powerup p : powerups)
                p.moveLeft(player.getSpeed());
        }
        if (player.getY() < deadZone * screenY)
        {
            player.moveDown();
            arenaY += player.getSpeed();
            for (Enemy e: enemies)
                e.moveDown(player.getSpeed());
            for (Powerup p : powerups)
                p.moveDown(player.getSpeed());
        }
        if (player.getY() > (1 - deadZone) * screenY)
        {
            player.moveUp();
            arenaY -= player.getSpeed();
            for (Enemy e: enemies)
                e.moveUp(player.getSpeed());
            for (Powerup p : powerups)
                p.moveUp(player.getSpeed());
        }
    }
    public void drawArena(Graphics buffer)
    {
        buffer.setColor(Color.BLACK);
        buffer.fillRect((int)arenaX, (int)arenaY - 10, (int)arenaWidth, 10);
        buffer.fillRect((int)arenaX, (int)(arenaY + arenaHeight), (int)arenaWidth, 10);
        buffer.fillRect((int)arenaX - 10, (int)arenaY, 10, (int)arenaHeight);
        buffer.fillRect((int)(arenaX + arenaWidth), (int)arenaY, 10, (int)arenaHeight);
    }
    public void drawUI(Graphics buffer)
    {
        buffer.setColor(Color.RED);
        buffer.fillRect(0, screenY - 20, (int)(200 * player.getPercentHealth()), 20);
        buffer.setColor(Color.BLACK);
        buffer.drawRect(0, screenY - 20, 200, 20);
        if (player.getScore() > highScore)
            highScore = player.getScore();
        buffer.drawString("Score: " + player.getScore() + "   High Score: " + highScore, 0, screenY - 20);
        if (!player.isAlive())
            buffer.drawString("Click to Restart!", screenX / 2 - 40, screenY / 2 + 10);
    }
    public double genX()
    {
        double x = (generator.nextDouble() * (arenaWidth - 40)) + arenaX;
        if (Math.abs(x - player.getX()) < 100)
            return genX();
        else
            return x;
    }
    public double genY()
    {
        double y = (generator.nextDouble() * (arenaHeight - 40)) + arenaY;
        if (Math.abs(y - player.getY()) < 100)
            return genY();
        else
            return y;
    }
    public double genPowerupX()
    {
        return (generator.nextDouble() * (arenaWidth - 40)) + arenaX;
    }
    public double genPowerupY()
    {
        return (generator.nextDouble() * (arenaHeight - 40)) + arenaY;
    }
    public void spawnChildren(Enemy s)
    {
        for (int c = 0; c < 4; c++)
            enemies.add(new Child(player, s.getMiddleX() - 5, s.getMiddleY() - 5, kanye, death));
    }
}