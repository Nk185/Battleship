package battleship;

public interface IUser
{    
    void showMessage(String message);

    void showSetup(String caption);
    void closeSetup();
    void openGameWindow();
    void closeGameWindow();
    void setup_Set_StartGameListener(IStartGameEvent listener);

    MapSettings setup_Process_GenerateMap();
}
