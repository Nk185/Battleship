package battleship;

public interface IUser
{    
    void ShowMessage(String message);

    void ShowSetup(String caption);
    void CloseSetup();
    void OpenGameWindow();
    void CloseGameWindow();
    void Setup_Set_StartGameListener(IStartGameEvent listener);

    MapSettings Setup_Process_GenerateMap();
}
