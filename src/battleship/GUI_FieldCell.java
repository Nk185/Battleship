package battleship;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;

public class GUI_FieldCell extends JLabel implements ComponentListener
{
    private final JLabel _Shadow;
    private boolean _isCellPressed;

    public final int xFieldPos;
    public final int yFieldPos;
    public final JLayeredPane container;
    public ESetupCellStatus setupStatus = ESetupCellStatus.Empty;

    public GUI_FieldCell(JLayeredPane container, int xFieldPos, int yFieldPos)
    {
        this.container = container;
        this.xFieldPos = xFieldPos;
        this.yFieldPos = yFieldPos;
        this.addComponentListener(this);
        
        this._Shadow = new JLabel();
        this._Shadow.setVisible(false);
        this._Shadow.setIcon(MapSupplier.ICO_CELL_SHADOW_ACTIVE);
        this._Shadow.setSize(MapSupplier.ICO_CELL_SHADOW_ACTIVE.getIconWidth(),
                MapSupplier.ICO_CELL_SHADOW_ACTIVE.getIconHeight());
        container.add(this._Shadow);
        container.setLayer(this._Shadow, 1);
    }

    public void setCellShadowType(boolean isCellPressed)
    {
        if (isCellPressed)
        {
            this._Shadow.setIcon(MapSupplier.ICO_CELL_SHADOW_ACTIVE_PRESSED);
            this._Shadow.setSize(MapSupplier.ICO_CELL_SHADOW_ACTIVE_PRESSED.getIconWidth(),
                    MapSupplier.ICO_CELL_SHADOW_ACTIVE_PRESSED.getIconHeight());
        }
        else 
        {
            this._Shadow.setIcon(MapSupplier.ICO_CELL_SHADOW_ACTIVE);
            this._Shadow.setSize(MapSupplier.ICO_CELL_SHADOW_ACTIVE.getIconWidth(),
                    MapSupplier.ICO_CELL_SHADOW_ACTIVE.getIconHeight());
        }
        
        this._Shadow.setLocation(this.getX() - (this._Shadow.getWidth() / 2) + (this.getWidth() / 2),
                this.getY() - (this._Shadow.getHeight() / 2) + (this.getHeight()/ 2));
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
        int ShadowInCentrePosX = source.getX() - (source._Shadow.getWidth() / 2) + (source.getWidth() / 2);
        int ShadowInCentrePosY = source.getY() - (source._Shadow.getHeight() / 2) + (source.getHeight() / 2);
        
        source.setShadowPos(ShadowInCentrePosX + ((source.getX() - 419) / (source._isCellPressed ? 200 : 100)),
                ShadowInCentrePosY + ((source.getY() - 303) / (source._isCellPressed ? 200 : 100)));
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
