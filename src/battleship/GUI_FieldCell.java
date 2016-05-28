package battleship;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;

public class GUI_FieldCell extends JLabel implements ComponentListener
{
    private final JLabel _Shadow;
    private int _LightX;
    private int _LightY;
    private boolean _isCellPressed;

    public final int xFieldPos;
    public final int yFieldPos;
    public final JLayeredPane container;
    public ESetupCellStatus setupStatus = ESetupCellStatus.Empty;

    public GUI_FieldCell(JLayeredPane container, int xFieldPos, int yFieldPos, int lightX, int lightY)
    {
        this.container = container;
        this.xFieldPos = xFieldPos;
        this.yFieldPos = yFieldPos;
        this._LightX = lightX;
        this._LightY = lightY;
        this.addComponentListener(this);
        
        this._Shadow = new JLabel();
        this._Shadow.setVisible(false);
        this._Shadow.setIcon(MapEngine.ICO_CELL_SHADOW_ACTIVE);
        this._Shadow.setSize(MapEngine.ICO_CELL_SHADOW_ACTIVE.getIconWidth(),
                MapEngine.ICO_CELL_SHADOW_ACTIVE.getIconHeight());
        container.add(this._Shadow);
        container.setLayer(this._Shadow, 1);
    }

    public void setCellShadowType(boolean isCellPressed)
    {     
        if (isCellPressed)
        {
            this._Shadow.setIcon(MapEngine.ICO_CELL_SHADOW_ACTIVE_PRESSED);
            this._Shadow.setSize(MapEngine.ICO_CELL_SHADOW_ACTIVE_PRESSED.getIconWidth(),
                    MapEngine.ICO_CELL_SHADOW_ACTIVE_PRESSED.getIconHeight());
        }
        else 
        {
            this._Shadow.setIcon(MapEngine.ICO_CELL_SHADOW_ACTIVE);
            this._Shadow.setSize(MapEngine.ICO_CELL_SHADOW_ACTIVE.getIconWidth(),
                    MapEngine.ICO_CELL_SHADOW_ACTIVE.getIconHeight());
        }
        
        this._Shadow.setLocation(this.getX() + (this.getWidth() / 2) - (this._Shadow.getWidth() / 2),
                this.getY() + (this.getHeight() / 2)- (this._Shadow.getHeight() / 2));
        this._isCellPressed = isCellPressed;
    }

    public void setCellShadowVisible(boolean isShadowVisible)
    {
        this._Shadow.setVisible(isShadowVisible);
    }
    
    private void setShadowPos(int x, int y)
    {
        this._Shadow.setLocation(x, y);
    }
   
    /**
     * Invoked when the component's size changes.
     */
    @Override
    public final void componentResized(ComponentEvent e)
    {

    }

    /**
     * Invoked when the component's position changes.
     */
    @Override
    public final void componentMoved(ComponentEvent e)
    {        
        GUI_FieldCell source = (GUI_FieldCell) e.getComponent();
        int ShadowInCentrePosX = source.getX() + (source.getWidth() / 2) - (source._Shadow.getWidth() / 2);
        int ShadowInCentrePosY = source.getY() + (source.getHeight() / 2) - (source._Shadow.getHeight() / 2);
        
        source.setShadowPos(ShadowInCentrePosX + ((source.getX() - source._LightX) / (source._isCellPressed ? 145 : 140)),
                ShadowInCentrePosY + ((source.getY() - source._LightY) / (source._isCellPressed ? 145 : 140)));
    }

    /**
     * Invoked when the component has been made visible.
     */
    @Override
    public final void componentShown(ComponentEvent e)
    {

    }

    /**
     * Invoked when the component has been made invisible.
     */
    @Override
    public final void componentHidden(ComponentEvent e)
    {

    }
}
