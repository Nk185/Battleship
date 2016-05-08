package battleship;

import javax.swing.*;

public class GUI implements IUser
{

    private GUI_Setup_SetupWindow _setup;
    private DashBoardInfoGUI _guiSettings;

    public void ShowMessage(String message)
    {

    }

    public void ShowSetup(String caption)
    {
        _setup = new GUI_Setup_SetupWindow(caption);
        _setup.setVisible(true);
    }

    public void CloseSetup()
    {
        _setup.setVisible(false);
    }

    public void OpenGameWindow()
    {

    }

    public void CloseGameWindow()
    {

    }

    @Override
    public void Setup_Set_StartGameListener(IStartGameEvent listener)
    {
        _setup.SetStartGameEventListener(listener);
    }

    @Override
    public MapSettings Setup_Process_GenerateMap()
    {
        return _setup.GenerateMap();
    }
}