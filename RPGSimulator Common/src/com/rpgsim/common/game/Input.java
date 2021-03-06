package com.rpgsim.common.game;

import com.rpgsim.common.Vector2;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, FocusListener
{
    private static final ArrayList<Integer> pressedKeys = new ArrayList<>();
    private static final ArrayList<Integer> heldKeys = new ArrayList<>();
    
    private static final ArrayList<Integer> pressedButtons = new ArrayList<>();
    private static final ArrayList<Integer> heldButtons = new ArrayList<>();
    
    private static int mouseX, mouseY;
    private static double mouseWheel;
    
    private static boolean mouseMoved = false;
    private static boolean mouseDragging = false;
    
    private static Transform screenTransform;

    public Input(Transform screenTransform)
    {
        Input.screenTransform = screenTransform;
    }
    
    public static boolean getKey(KeyCode code)
    {
        return heldKeys.contains(code.getCode());
    }
    
    public static boolean getKeyDown(KeyCode code)
    {
        return pressedKeys.contains(code.getCode()) && !heldKeys.contains(code.getCode());
    }
    
    public static boolean getKeyUp(KeyCode code)
    {
        return !pressedKeys.contains(code.getCode()) && heldKeys.contains(code.getCode());
    }
    
    public static boolean getMouseButton(int code)
    {
        return heldButtons.contains(code);
    }
    
    public static boolean getMouseButtonDown(int code)
    {
        return pressedButtons.contains(code) && !heldButtons.contains(code);
    }
    
    public static boolean getMouseButtonUp(int code)
    {
        return !pressedButtons.contains(code) && heldButtons.contains(code);
    }
    
    public static boolean mouseMoved()
    {
        return mouseMoved;
    }
    
    public static boolean mouseDragged()
    {
        return mouseDragging && mouseMoved;
    }
    
    public static Vector2 mousePosition()
    {
        return new Vector2((mouseX - screenTransform.position().x) / screenTransform.scale().x, 
                (mouseY - screenTransform.position().y) / screenTransform.scale().y);
    }
    
    public static double mouseWheel()
    {
        return mouseWheel;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (!pressedKeys.contains(e.getKeyCode()))
            pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        pressedKeys.remove(new Integer(e.getKeyCode()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (!pressedButtons.contains(e.getButton()))
            pressedButtons.add(e.getButton());
        mouseDragging = true;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        pressedButtons.remove(new Integer(e.getButton()));
        mouseDragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
        mouseMoved = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        mouseX = e.getX();
        mouseY = e.getY();
        mouseMoved = true;
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        mouseWheel = e.getPreciseWheelRotation();
    }
    
    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent e)
    {
        heldButtons.clear();
        heldKeys.clear();
        mouseDragging = false;
        mouseMoved = false;
        pressedButtons.clear();
        pressedKeys.clear();
    }
    
    public void update()
    {
        heldKeys.clear();
        for (Integer pk : pressedKeys)
            heldKeys.add(pk);
        heldButtons.clear();
        for (Integer pd : pressedButtons)
            heldButtons.add(pd);
        mouseMoved = false;
        mouseWheel = 0;
    }
    
}
