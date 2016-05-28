package battleship;

import javax.swing.*;

public class GUI implements IUser
{

    private GUI_Setup_SetupWindow _setup;
    private DashBoardInfoGUI _guiSettings;

    public void showMessage(String message)
    {

    }

    public void showSetup(String caption)
    {
        _setup = new GUI_Setup_SetupWindow(caption);
        _setup.setVisible(true);
    }

    public void closeSetup()
    {
        _setup.setVisible(false);
    }

    public void openGameWindow()
    {

    }

    public void closeGameWindow()
    {

    }

    @Override
    public void setup_Set_StartGameListener(IStartGameEvent listener)
    {
        _setup.setStartGameEventListener(listener);
    }

    @Override
    public MapSettings setup_Process_GenerateMap()
    {
        return _setup.generateMap();
    }
}