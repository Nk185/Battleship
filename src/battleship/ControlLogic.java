package battleship;

import javax.swing.*;

public class ControlLogic implements IControlLogic, IStartGameEvent
{

    private final IUser _view;


    public ControlLogic(IUser _view)
    {
        this._view = _view;
    }

    @Override
    public void StartTheGame()
    {
        this._view.ShowSetup("Встановіть свої кораблі");
        this._view.Setup_Set_StartGameListener(this);
    }

    @Override
    public void StartButtonClicked()
    {
        MapSettings ms;

        JOptionPane.showMessageDialog(null, "Кнопка \"Розпочати гру\" була натиснута!!!",
                "Попередження", JOptionPane.INFORMATION_MESSAGE);

        ms = _view.Setup_Process_GenerateMap();
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (ms.Map[j][i] == ECellStatus.ContainsShip)
                    System.out.print("[S]");
                else if (ms.Map[j][i] == ECellStatus.LocatedNearShip)
                    System.out.print("[-]");
                else
                    System.out.print("[ ]");
            }
            System.out.print("\n");
        }

    }
}
