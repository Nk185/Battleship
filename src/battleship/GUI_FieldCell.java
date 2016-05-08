package battleship;

import javax.swing.*;

public class GUI_FieldCell extends JLabel
{

    public final int xFieldPos;
    public final int yFieldPos;
    public final JLayeredPane container;
    public ESetupCellStatus setupStatus = ESetupCellStatus.Empty;

    public GUI_FieldCell(JLayeredPane container, int xFieldPos, int yFieldPos)
    {
        this.container = container;
        this.xFieldPos = xFieldPos;
        this.yFieldPos = yFieldPos;
    }
}
