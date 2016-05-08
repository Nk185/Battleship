package battleship;

import javax.swing.JLabel;

public class GUI_Setup_DashButton extends JLabel
{

    public final int buttonType;
    public boolean buttonEnabled;

    public GUI_Setup_DashButton(int type)
    {
        this.buttonType = type;
    }
}
