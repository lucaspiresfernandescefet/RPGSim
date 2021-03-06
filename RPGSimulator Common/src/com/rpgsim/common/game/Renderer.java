package com.rpgsim.common.game;

import com.rpgsim.common.Screen;
import com.rpgsim.common.Vector2;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Renderer
{
    private final NetworkGameObject gameObject;
    private String imagePath;
    private Image image;

    public Renderer(NetworkGameObject gameObject)
    {
        this.gameObject = gameObject;
        this.image = null;
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }
    
    public void render(int clientID, Screen screen)
    {
        AffineTransform transform = new AffineTransform();
        
        Vector2 t = gameObject.transform().position();
        Vector2 s = gameObject.transform().scale();
        
        if (clientID == gameObject.getClientID())
            transform.setTransform(s.x, 0.0, 0.0, s.y, t.x, t.y);
        else
            transform.setTransform(s.x, 0.0, 0.0, s.y, t.x, t.y);
        
        transform.rotate(Math.toRadians(gameObject.transform().rotation()), image.getWidth(null) * 0.5f, image.getHeight(null) * 0.5f);
        
        if (gameObject.transform().flipX())
        {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            transform.concatenate(tx);
        }
        
        if (gameObject.transform().flipY())
        {
            AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
            tx.translate(0, -image.getHeight(null));
            transform.concatenate(tx);
        }
        
        screen.drawImage(image, transform);
    }
    
}
