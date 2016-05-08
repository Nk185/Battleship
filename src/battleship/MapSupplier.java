package battleship;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

class MapSupplier  
{
    public static final ImageIcon ICO_CELL                 = new ImageIcon("images/setup/Cell.png");
    public static final ImageIcon ICO_CELL_FOCUSED         = new ImageIcon("images/setup/Cell_Active_Focused.png");
    public static final ImageIcon ICO_CELL_FOCUSED_PRESSED = new ImageIcon("images/setup/Cell_Active_Focused_Pressed.png");
    public static final ImageIcon ICO_CELL_INACTIVE        = new ImageIcon("images/setup/Cell_Inactive.png");
    public static final ImageIcon ICO_CELL_WITH_SHIP       = new ImageIcon("images/setup/Cell_WithShip.png");
    public static final ImageIcon ICO_CELL_MASKED_SHIP     = new ImageIcon("images/setup/Cell_WithShip_Masked.png");
	
    public static final ImageIcon ICO_SHIP_INDICATOR_TRUE  = new ImageIcon("images/setup/Indicator_True.png");
    public static final ImageIcon ICO_SHIP_INDICATOR_FALSE = new ImageIcon("images/setup/Indicator_False.png");

    public static final ImageIcon BUTTON_SETUP_SETSHIP_INACTIVE         = new ImageIcon("images/setup/SetButton_Inactive.png");
    public static final ImageIcon BUTTON_SETUP_SETSHIP_ACTIVE           = new ImageIcon("images/setup/SetButton_Active.png");
    public static final ImageIcon BUTTON_SETUP_SETSHIP_ACTIVE_FOCUSED   = new ImageIcon("images/setup/SetButton_Active_Focused.png");
    public static final ImageIcon BUTTON_SETUP_RESET_INACTIVE           = new ImageIcon("images/setup/ResetButton_Inactive.png");
    public static final ImageIcon BUTTON_SETUP_RESET_ACTIVE             = new ImageIcon("images/setup/ResetButton_Active.png");
    public static final ImageIcon BUTTON_SETUP_RESET_ACTIVE_FOCUSED     = new ImageIcon("images/setup/ResetButton_Active_Focused.png");
    public static final ImageIcon BUTTON_SETUP_STARTGAME_INACTIVE       = new ImageIcon("images/setup/StartGameButton_Inactive.png");
    public static final ImageIcon BUTTON_SETUP_STARTGAME_ACTIVE         = new ImageIcon("images/setup/StartGameButton_Active.png");
    public static final ImageIcon BUTTON_SETUP_STARTGAME_ACTIVE_FOCUSED = new ImageIcon("images/setup/StartGameButton_Active_Focused.png");
    
    public static final void SetGridLabel(GUI_FieldCell[][] labelsArray, JLayeredPane sourcePanel,
            int startLblPosX, int startLblPosY)
    {
        final int border       = 5;
        final int buttonWidth  = 46;
        final int buttonHeight = 46;

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                labelsArray[i][j] = new GUI_FieldCell(sourcePanel, i, j);
               
                labelsArray[i][j].setLocation(startLblPosX, startLblPosY);
                labelsArray[i][j].setSize(buttonWidth, buttonHeight);
                labelsArray[i][j].setIcon(ICO_CELL);

                sourcePanel.add(labelsArray[i][j]);
            }
        }

        for (int y = 1; y < 10; y++)
        {
            labelsArray[y][0].setLocation(labelsArray[y - 1][0].getX() + labelsArray[y - 1][0].getWidth() + border, startLblPosY);
            labelsArray[0][y].setLocation(labelsArray[0][y].getX(),
                    labelsArray[0][y - 1].getY() + labelsArray[0][y - 1].getHeight() + border);
            for (int x = 1; x < 10; x++)
            {
                labelsArray[x][y].setLocation(labelsArray[x - 1][y].getX() + labelsArray[x - 1][y].getWidth() + border,
                        labelsArray[x][y - 1].getY() + labelsArray[x][y - 1].getHeight() + border);
            }
        }      
    }
}