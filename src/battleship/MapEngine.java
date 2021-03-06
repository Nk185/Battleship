package battleship;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

class MapEngine  
{
    public static final ImageIcon ICO_CELL                 = new ImageIcon("images/setup/Cell.png");
    public static final ImageIcon ICO_CELL_FOCUSED         = new ImageIcon("images/setup/Cell_Active_Focused.png");    
    public static final ImageIcon ICO_CELL_FOCUSED_PRESSED = new ImageIcon("images/setup/Cell_Active_Focused_Pressed.png");
    public static final ImageIcon ICO_CELL_INACTIVE        = new ImageIcon("images/setup/Cell_Inactive.png");
    public static final ImageIcon ICO_CELL_WITH_SHIP       = new ImageIcon("images/setup/Cell_WithShip.png");
    public static final ImageIcon ICO_CELL_MASKED_SHIP     = new ImageIcon("images/setup/Cell_WithShip_Masked.png");
    
    public static final ImageIcon ICO_CELL_SHADOW_ACTIVE         = new ImageIcon("images/setup/CellShadow_Active.png");
    public static final ImageIcon ICO_CELL_SHADOW_ACTIVE_PRESSED = new ImageIcon("images/setup/CellShadow_Active_Pressed.png");
	
    public static final ImageIcon ICO_SHIP_INDICATOR_TRUE  = new ImageIcon("images/setup/Indicator_True.png");
    public static final ImageIcon ICO_SHIP_INDICATOR_FALSE = new ImageIcon("images/setup/Indicator_False.png");

    public static final ImageIcon ICO_DASH_SHIP_NORMAL_4         = new ImageIcon("images/setup/dash_shipIco_4.png");
    public static final ImageIcon ICO_DASH_SHIP_NORMAL_3         = new ImageIcon("images/setup/dash_shipIco_3.png");
    public static final ImageIcon ICO_DASH_SHIP_NORMAL_2         = new ImageIcon("images/setup/dash_shipIco_2.png");
    public static final ImageIcon ICO_DASH_SHIP_NORMAL_1         = new ImageIcon("images/setup/dash_shipIco_1.png");
    public static final ImageIcon ICO_DASH_SHIP_ACTIVE_4         = new ImageIcon("images/setup/dash_shipIco_active_4.png");
    public static final ImageIcon ICO_DASH_SHIP_ACTIVE_3         = new ImageIcon("images/setup/dash_shipIco_active_3.png");
    public static final ImageIcon ICO_DASH_SHIP_ACTIVE_2         = new ImageIcon("images/setup/dash_shipIco_active_2.png");
    public static final ImageIcon ICO_DASH_SHIP_ACTIVE_1         = new ImageIcon("images/setup/dash_shipIco_active_1.png");
    public static final ImageIcon ICO_DASH_SHIP_INACTIVE_4       = new ImageIcon("images/setup/dash_shipIco_inactive_4.png");
    public static final ImageIcon ICO_DASH_SHIP_INACTIVE_3       = new ImageIcon("images/setup/dash_shipIco_inactive_3.png");
    public static final ImageIcon ICO_DASH_SHIP_INACTIVE_2       = new ImageIcon("images/setup/dash_shipIco_inactive_2.png");
    public static final ImageIcon ICO_DASH_SHIP_INACTIVE_1       = new ImageIcon("images/setup/dash_shipIco_inactive_1.png");
    public static final ImageIcon ICO_DASH_SHIP_INACTIVE_WRONG_4 = new ImageIcon("images/setup/dash_shipIco_inactive_wrong_4.png");
    public static final ImageIcon ICO_DASH_SHIP_INACTIVE_WRONG_3 = new ImageIcon("images/setup/dash_shipIco_inactive_wrong_3.png");
    public static final ImageIcon ICO_DASH_SHIP_INACTIVE_WRONG_2 = new ImageIcon("images/setup/dash_shipIco_inactive_wrong_2.png");
    public static final ImageIcon ICO_DASH_SHIP_INACTIVE_WRONG_1 = new ImageIcon("images/setup/dash_shipIco_inactive_wrong_1.png");
    public static final ImageIcon ICO_DASH_SHIP_ERROR_4          = new ImageIcon("images/setup/dash_shipIco_error_4.png");
    public static final ImageIcon ICO_DASH_SHIP_ERROR_3          = new ImageIcon("images/setup/dash_shipIco_error_3.png");
    public static final ImageIcon ICO_DASH_SHIP_ERROR_2          = new ImageIcon("images/setup/dash_shipIco_error_2.png");
    public static final ImageIcon ICO_DASH_SHIP_ERROR_1          = new ImageIcon("images/setup/dash_shipIco_error_1.png");


    public static final ImageIcon BUTTON_SETUP_SETSHIP_INACTIVE         = new ImageIcon("images/setup/SetButton_Inactive.png");
    public static final ImageIcon BUTTON_SETUP_SETSHIP_ACTIVE           = new ImageIcon("images/setup/SetButton_Active.png");
    public static final ImageIcon BUTTON_SETUP_SETSHIP_ACTIVE_FOCUSED   = new ImageIcon("images/setup/SetButton_Active_Focused.png");
    public static final ImageIcon BUTTON_SETUP_RESET_INACTIVE           = new ImageIcon("images/setup/ResetButton_Inactive.png");
    public static final ImageIcon BUTTON_SETUP_RESET_ACTIVE             = new ImageIcon("images/setup/ResetButton_Active.png");
    public static final ImageIcon BUTTON_SETUP_RESET_ACTIVE_FOCUSED     = new ImageIcon("images/setup/ResetButton_Active_Focused.png");
    public static final ImageIcon BUTTON_SETUP_STARTGAME_INACTIVE       = new ImageIcon("images/setup/StartGameButton_Inactive.png");
    public static final ImageIcon BUTTON_SETUP_STARTGAME_ACTIVE         = new ImageIcon("images/setup/StartGameButton_Active.png");
    public static final ImageIcon BUTTON_SETUP_STARTGAME_ACTIVE_FOCUSED = new ImageIcon("images/setup/StartGameButton_Active_Focused.png");


    
    public static final void setGridLabel(GUI_FieldCell[][] labelsArray, JLayeredPane sourcePanel,
            int startLblPosX, int startLblPosY, int lightX, int lightY)
    {
        final int border       = 5;
        final int buttonWidth  = 46;
        final int buttonHeight = 46;

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                labelsArray[i][j] = new GUI_FieldCell(sourcePanel, i, j, lightX, lightY);
               
                labelsArray[i][j].setLocation(startLblPosX, startLblPosY);
                labelsArray[i][j].setIcon(ICO_CELL);
                labelsArray[i][j].setSize(ICO_CELL.getIconWidth(), ICO_CELL.getIconHeight());
                sourcePanel.add(labelsArray[i][j]);
            }
        }

        for (int i = 1; i < 10; i++)
        {
            labelsArray[i][0].setLocation(labelsArray[i - 1][0].getX() + labelsArray[i - 1][0].getWidth() + border, startLblPosY);
            labelsArray[0][i].setLocation(labelsArray[0][i].getX(),
                    labelsArray[0][i - 1].getY() + labelsArray[0][i - 1].getHeight() + border);
            for (int j = 1; j < 10; j++)
            {
                labelsArray[j][i].setLocation(labelsArray[j - 1][i].getX() + labelsArray[j - 1][i].getWidth() + border,
                        labelsArray[j][i - 1].getY() + labelsArray[j][i - 1].getHeight() + border);
            }
        }
    }
}