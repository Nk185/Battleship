package battleship;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class GUI_Setup_SetupWindow extends JFrame implements MouseListener
{

    private JLayeredPane _mainPanel;
    private JLabel _dashboard;
    private GUI_Setup_DashButton _buttonSetShip;
    private GUI_Setup_DashButton _buttonStartGame;
    private GUI_Setup_DashButton _resetButton;
    private JLabel[] _dashboardShipsIco;
    private JLabel[] _dashboardShipsIndicatorIco;
    private JLabel[] _dashboardShipsCountIco;
    private GUI_FieldCell[][] _userCells;
    private DashBoardInfoGUI _dashBoardInfoGUI;
    private IStartGameEvent _StartGameEventListener;

    public GUI_Setup_SetupWindow(String caption)
    {
        super((caption == null) ? "Setup window" : caption);
        super.setResizable(false);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.getContentPane().setBackground(new Color(209, 229, 248));

        _InitializeComponents();
    }

    public void SetStartGameEventListener(IStartGameEvent listener)
    {
        this._StartGameEventListener = listener;
    }

    public MapSettings GenerateMap()
    {
        MapSettings res = new MapSettings();

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                switch (this._userCells[i][j].setupStatus)
                {
                    case ContainsShip:
                        res.Map[i][j] = ECellStatus.ContainsShip;
                        break;
                    case NearShip:
                        res.Map[i][j] = ECellStatus.LocatedNearShip;
                        break;
                    case Empty:
                        res.Map[i][j] = ECellStatus.ClosedEmpty;
                        break;
                }
            }
        }

        return res;
    }

    private void _InitializeComponents()
    {
        this._mainPanel = new JLayeredPane();
        this._dashboard = new JLabel();

        this._buttonStartGame = new GUI_Setup_DashButton(0);
        this._buttonSetShip   = new GUI_Setup_DashButton(1);
        this._resetButton     = new GUI_Setup_DashButton(2);

        this._userCells = new GUI_FieldCell[10][10];

        this._dashboardShipsIco          = new JLabel[4];
        this._dashboardShipsIndicatorIco = new JLabel[4];
        this._dashboardShipsCountIco     = new JLabel[4];

        for (int i = 0; i < 4; i++)
        {
            this._dashboardShipsIco[i]          = new JLabel();
            this._dashboardShipsIndicatorIco[i] = new JLabel();
            this._dashboardShipsCountIco[i]     = new JLabel();
        }

        _dashBoardInfoGUI = new DashBoardInfoGUI();

        _InitSetupGUI();
    }

    private void _InitSetupGUI()
    {
        int monitorWidth  = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
        int monitorHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();

        this._dashboard.setIcon(new ImageIcon("images/setup/dash.png"));
        this._dashboard.setSize(this._dashboard.getIcon().getIconWidth(),
                this._dashboard.getIcon().getIconHeight());
        this._dashboard.setLocation(0, 0);

        this._buttonStartGame.setIcon(MapSupplier.BUTTON_SETUP_STARTGAME_INACTIVE);
        this._buttonStartGame.setSize(this._buttonStartGame.getIcon().getIconWidth(),
                this._buttonStartGame.getIcon().getIconHeight());
        this._buttonStartGame.setLocation((this._dashboard.getWidth() - this._buttonStartGame.getWidth()) / 2 - 13,
                this._dashboard.getHeight() - 235);
        this._buttonStartGame.addMouseListener(this);

        this._buttonSetShip.setIcon(MapSupplier.BUTTON_SETUP_SETSHIP_INACTIVE);
        this._buttonSetShip.setSize(this._buttonSetShip.getIcon().getIconWidth(),
                this._buttonSetShip.getIcon().getIconHeight());
        this._buttonSetShip.setLocation((this._dashboard.getWidth() - this._buttonSetShip.getWidth()) / 2 - 13,
                this._dashboard.getHeight() - 170);
        this._buttonSetShip.addMouseListener(this);

        this._resetButton.setIcon(MapSupplier.BUTTON_SETUP_RESET_ACTIVE);
        this._resetButton.setSize(this._resetButton.getIcon().getIconWidth(),
                this._resetButton.getIcon().getIconHeight());
        this._resetButton.setLocation((this._dashboard.getWidth() - this._resetButton.getWidth()) / 2 - 13,
                this._dashboard.getHeight() - 105);
        this._resetButton.buttonEnabled = true;
        this._resetButton.addMouseListener(this);

        this._dashboardShipsIco[0].setIcon(new ImageIcon("images/setup/dash_shipIco_4.png"));
        this._dashboardShipsIco[0].setSize(this._dashboardShipsIco[0].getIcon().getIconWidth(),
                this._dashboardShipsIco[0].getIcon().getIconHeight());
        this._dashboardShipsIco[0].setLocation(20, 113);

        for (int i = 1; i < 4; i++)
        {
            this._dashboardShipsIco[i].setIcon(new ImageIcon("images/setup/dash_shipIco_"
                    + (4 - i) + ".png"));
            this._dashboardShipsIco[i].setSize(this._dashboardShipsIco[i].getIcon().getIconWidth(),
                    this._dashboardShipsIco[i].getIcon().getIconHeight());
            this._dashboardShipsIco[i].setLocation(20, this._dashboardShipsIco[i - 1].getY() + 64);
        }

        for (int i = 0; i < 4; i++)
        {

            this._dashboardShipsIndicatorIco[i].setIcon(MapSupplier.ICO_SHIP_INDICATOR_FALSE);
            this._dashboardShipsIndicatorIco[i].setSize(this._dashboardShipsIndicatorIco[0].getIcon().getIconWidth(),
                    this._dashboardShipsIndicatorIco[0].getIcon().getIconHeight());
            this._dashboardShipsIndicatorIco[i].setLocation(this._dashboardShipsIco[i].getX() - 15,
                    this._dashboardShipsIco[i].getY() + 14);

            this._dashboardShipsCountIco[i].setText("x" + (i + 1));
            this._dashboardShipsCountIco[i].setSize(90, 90);
            this._dashboardShipsCountIco[i].setFont(new Font("Segoe UI Light", 0, 50));
            this._dashboardShipsCountIco[i].setLocation(this._dashboardShipsIco[i].getX() + this._dashboardShipsIco[i].getWidth() + 5,
                    this._dashboardShipsIco[i].getY() - 28);
            this._dashboardShipsCountIco[i].setForeground(new Color(122, 141, 155));
        }

        MapSupplier.SetGridLabel(this._userCells, this._mainPanel, this._dashboard.getWidth() + 20, 50);

        for (GUI_FieldCell[] cell : this._userCells)
        {
            for (GUI_FieldCell cell2 : cell)
            {
                cell2.addMouseListener(new GUI_Setup_CellsListener(this._userCells, this._buttonSetShip, this._dashBoardInfoGUI));
            }
        }

        for (int i = 0; i < 4; i++)
        {
            this._mainPanel.add(this._dashboardShipsCountIco[i]);
            this._mainPanel.add(this._dashboardShipsIndicatorIco[i]);
            this._mainPanel.add(this._dashboardShipsIco[i]);
        }
        this._mainPanel.add(this._buttonStartGame);
        this._mainPanel.add(this._buttonSetShip);
        this._mainPanel.add(this._resetButton);
        this._mainPanel.add(this._dashboard);
        this._mainPanel.setLayout(null);

        super.getContentPane().add(this._mainPanel);
        super.setSize(this._userCells[9][9].getX() + this._userCells[9][9].getWidth() + 40,
                this._dashboard.getIcon().getIconHeight() - 20);
        super.setLocation((monitorWidth - super.getWidth()) / 2, (monitorHeight - super.getHeight()) / 2);
    }

    private void _RenderDash(DashBoardInfoGUI settings)
    {
        this._dashboardShipsCountIco[0].setText("x" + (1 - settings.installedFourBoardShipsCount));
        this._dashboardShipsCountIco[1].setText("x" + (2 - settings.installedThreeBoardShipsCount));
        this._dashboardShipsCountIco[2].setText("x" + (3 - settings.installedTwoBoardShipsCount));
        this._dashboardShipsCountIco[3].setText("x" + (4 - settings.installedOneBoardShipsCount));

        this._dashboardShipsIndicatorIco[0].setIcon(settings.isFourBoardShipsInstalledAll  ? MapSupplier.ICO_SHIP_INDICATOR_TRUE : MapSupplier.ICO_SHIP_INDICATOR_FALSE);
        this._dashboardShipsIndicatorIco[1].setIcon(settings.isThreeBoardShipsInstalledAll ? MapSupplier.ICO_SHIP_INDICATOR_TRUE : MapSupplier.ICO_SHIP_INDICATOR_FALSE);
        this._dashboardShipsIndicatorIco[2].setIcon(settings.isTwoBoardShipsInstalledAll   ? MapSupplier.ICO_SHIP_INDICATOR_TRUE : MapSupplier.ICO_SHIP_INDICATOR_FALSE);
        this._dashboardShipsIndicatorIco[3].setIcon(settings.isOneBoardShipsInstalledAll   ? MapSupplier.ICO_SHIP_INDICATOR_TRUE : MapSupplier.ICO_SHIP_INDICATOR_FALSE);

        if (settings.isOneBoardShipsInstalledAll && settings.isTwoBoardShipsInstalledAll
                && settings.isThreeBoardShipsInstalledAll && settings.isFourBoardShipsInstalledAll)
        {
            this._buttonStartGame.buttonEnabled = true;
            this._buttonStartGame.setIcon(MapSupplier.BUTTON_SETUP_STARTGAME_ACTIVE);
        } else
        {
            this._buttonStartGame.buttonEnabled = false;
            this._buttonStartGame.setIcon(MapSupplier.BUTTON_SETUP_STARTGAME_INACTIVE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent event)
    {
        GUI_Setup_DashButton button = (GUI_Setup_DashButton) event.getComponent();

        if (button.buttonType == 1 && button.buttonEnabled)
        {
            _SetupShip();

            button.setSize(217, 40);
            button.setLocation(button.getX() + 18, button.getY() + 14);
            button.setIcon(MapSupplier.BUTTON_SETUP_SETSHIP_INACTIVE);
            button.buttonEnabled = false;
        } else if (button.buttonType == 2)
        {
            this._dashBoardInfoGUI.isOneBoardShipsInstalledAll   = false;
            this._dashBoardInfoGUI.isTwoBoardShipsInstalledAll   = false;
            this._dashBoardInfoGUI.isThreeBoardShipsInstalledAll = false;
            this._dashBoardInfoGUI.isFourBoardShipsInstalledAll  = false;

            this._dashBoardInfoGUI.installedOneBoardShipsCount   = 0;
            this._dashBoardInfoGUI.installedTwoBoardShipsCount   = 0;
            this._dashBoardInfoGUI.installedThreeBoardShipsCount = 0;
            this._dashBoardInfoGUI.installedFourBoardShipsCount  = 0;
            
            this._buttonSetShip.buttonEnabled = false;
            this._buttonSetShip.setIcon(MapSupplier.BUTTON_SETUP_SETSHIP_INACTIVE);

            for (GUI_FieldCell[] cells : this._userCells)
            {
                for (GUI_FieldCell cell : cells)
                {
                    cell.setupStatus = ESetupCellStatus.Empty;
                    cell.setIcon(MapSupplier.ICO_CELL);
                }
            }

            _RenderDash(this._dashBoardInfoGUI);
        } else if (button.buttonType == 0 && button.buttonEnabled)
            _StartGameEventListener.StartButtonClicked();
    }

    @Override
    public void mouseEntered(MouseEvent event)
    {
        GUI_Setup_DashButton button = (GUI_Setup_DashButton) event.getComponent();

        if (button.buttonType == 1 && button.buttonEnabled) // setShip Buttton
        {
            button.setSize(253, 68);
            button.setLocation(button.getX() - 18, button.getY() - 14);
            button.setIcon(MapSupplier.BUTTON_SETUP_SETSHIP_ACTIVE_FOCUSED);
        } else if (button.buttonType == 2 && button.buttonEnabled) // reset Button
        {
            button.setSize(252, 68);
            button.setLocation(button.getX() - 18, button.getY() - 14);
            button.setIcon(MapSupplier.BUTTON_SETUP_RESET_ACTIVE_FOCUSED);
        } else if (button.buttonType == 0 && button.buttonEnabled) // Start game button
        {
            button.setSize(252, 68);
            button.setLocation(button.getX() - 18, button.getY() - 14);
            button.setIcon(MapSupplier.BUTTON_SETUP_STARTGAME_ACTIVE_FOCUSED);
        }
    }

    @Override
    public void mouseExited(MouseEvent event)
    {
        GUI_Setup_DashButton button = (GUI_Setup_DashButton) event.getComponent();

        if (button.buttonType == 1 && button.buttonEnabled) // setShip Button 
        {
            button.setSize(217, 40);
            button.setLocation(button.getX() + 18, button.getY() + 14);
            button.setIcon(MapSupplier.BUTTON_SETUP_SETSHIP_ACTIVE);
        } else if (button.buttonType == 2 && button.buttonEnabled) // reset Button
        {
            button.setSize(216, 40);
            button.setLocation(button.getX() + 18, button.getY() + 14);
            button.setIcon(MapSupplier.BUTTON_SETUP_RESET_ACTIVE);
        } else if (button.buttonType == 0 && button.buttonEnabled) // StartGame Button
        {
            button.setSize(216, 40);
            button.setLocation(button.getX() + 18, button.getY() + 14);
            button.setIcon(MapSupplier.BUTTON_SETUP_STARTGAME_ACTIVE);
        }
    }

    @Override
    public void mousePressed(MouseEvent event)
    {
    }

    @Override
    public void mouseReleased(MouseEvent event)
    {
    }

    private void _SetupShip()
    {
        int shipType = 0;
        int firstShipX = -1;
        int firstShipY = -1;
        boolean isValidShip = true;

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (this._userCells[i][j].setupStatus == ESetupCellStatus.SetupInProgress_FirstShip)
                {
                    firstShipX = this._userCells[i][j].xFieldPos;
                    firstShipY = this._userCells[i][j].yFieldPos;
                    shipType++;
                    break;
                }
            }

            if (firstShipX != -1 && firstShipY != -1)
                break;
        }

        if (this._userCells[firstShipX + (firstShipX == 9 ? 0 : 1)][firstShipY].setupStatus == ESetupCellStatus.SetupInProgress
                || this._userCells[firstShipX - (firstShipX == 0 ? 0 : 1)][firstShipY].setupStatus == ESetupCellStatus.SetupInProgress)
        {
            for (int i = firstShipX + 1; i <= firstShipX + 4; i++)
            {
                if (i < 10)
                {
                    if (this._userCells[i][firstShipY].setupStatus == ESetupCellStatus.SetupInProgress)
                    {
                        shipType++;
                    } else
                        break;
                } else
                    break;
            }

            for (int i = firstShipX - 1; i >= firstShipX - 4; i--)
            {
                if (i >= 0)
                {
                    if (this._userCells[i][firstShipY].setupStatus == ESetupCellStatus.SetupInProgress)
                    {
                        shipType++;
                    } else
                        break;
                } else
                    break;
            }

        } else if (this._userCells[firstShipX][firstShipY + (firstShipY == 9 ? 0 : 1)].setupStatus == ESetupCellStatus.SetupInProgress
                || this._userCells[firstShipX][firstShipY - (firstShipY == 0 ? 0 : 1)].setupStatus == ESetupCellStatus.SetupInProgress)
        {
            for (int i = firstShipY + 1; i <= firstShipY + 4; i++)
            {
                if (i < 10)
                {
                    if (this._userCells[firstShipX][i].setupStatus == ESetupCellStatus.SetupInProgress)
                    {
                        shipType++;
                    } else
                        break;
                } else
                    break;
            }

            for (int i = firstShipY - 1; i >= firstShipY - 4; i--)
            {
                if (i >= 0)
                {
                    if (this._userCells[firstShipX][i].setupStatus == ESetupCellStatus.SetupInProgress)
                    {
                        shipType++;
                    } else
                        break;
                } else
                    break;
            }
        }

        switch (shipType)
        {
            case 1:
                if (this._dashBoardInfoGUI.isOneBoardShipsInstalledAll)
                    isValidShip = false;
                break;
            case 2:
                if (this._dashBoardInfoGUI.isTwoBoardShipsInstalledAll)
                    isValidShip = false;
                break;
            case 3:
                if (this._dashBoardInfoGUI.isThreeBoardShipsInstalledAll)
                    isValidShip = false;
                break;
            case 4:
                if (this._dashBoardInfoGUI.isFourBoardShipsInstalledAll)
                    isValidShip = false;
                break;
            default:
                isValidShip = false;
                break;

        }

        if (isValidShip)
            if ((this._userCells[firstShipX + (firstShipX == 9 ? 0 : 1)][firstShipY].setupStatus == ESetupCellStatus.SetupInProgress
                    || this._userCells[firstShipX - (firstShipX == 0 ? 0 : 1)][firstShipY].setupStatus == ESetupCellStatus.SetupInProgress)
                    && (this._userCells[firstShipX][firstShipY + (firstShipY == 9 ? 0 : 1)].setupStatus == ESetupCellStatus.SetupInProgress
                    || this._userCells[firstShipX][firstShipY - (firstShipY == 0 ? 0 : 1)].setupStatus == ESetupCellStatus.SetupInProgress))
                isValidShip = false;

        if (isValidShip)
        {
            this._userCells[firstShipX][firstShipY].setupStatus = ESetupCellStatus.ContainsShip;

            if (firstShipX + 1 < 10)
            {
                if (this._userCells[firstShipX + 1][firstShipY].setupStatus == ESetupCellStatus.SetupInProgress)
                    for (int i = firstShipX + 1; i <= firstShipX + 4; i++)
                    {
                        if (i < 10)
                        {
                            if (this._userCells[i][firstShipY].setupStatus == ESetupCellStatus.SetupInProgress)
                            {
                                this._userCells[i][firstShipY].setupStatus = ESetupCellStatus.ContainsShip;
                            } else if (this._userCells[i][firstShipY].setupStatus == ESetupCellStatus.Empty
                                    || this._userCells[i][firstShipY].setupStatus == ESetupCellStatus.NearShip
                                    || this._userCells[i][firstShipY].setupStatus == ESetupCellStatus.NotAvailable)
                                break;

                        } else
                            break;
                    }
            }

            if (firstShipX - 1 >= 0)
            {
                if (this._userCells[firstShipX - 1][firstShipY].setupStatus == ESetupCellStatus.SetupInProgress)
                    for (int i = firstShipX - 1; i >= firstShipX - 4; i--)
                    {
                        if (i >= 0)
                        {
                            if (this._userCells[i][firstShipY].setupStatus == ESetupCellStatus.SetupInProgress)
                            {
                                this._userCells[i][firstShipY].setupStatus = ESetupCellStatus.ContainsShip;
                            } else if (this._userCells[i][firstShipY].setupStatus == ESetupCellStatus.Empty
                                    || this._userCells[i][firstShipY].setupStatus == ESetupCellStatus.NearShip
                                    || this._userCells[i][firstShipY].setupStatus == ESetupCellStatus.NotAvailable)
                                break;

                        } else
                            break;
                    }
            }

            if (firstShipY + 1 < 10)
            {
                if (this._userCells[firstShipX][firstShipY + 1].setupStatus == ESetupCellStatus.SetupInProgress)
                    for (int i = firstShipY + 1; i <= firstShipY + 4; i++)
                    {
                        if (i < 10)
                        {
                            if (this._userCells[firstShipX][i].setupStatus == ESetupCellStatus.SetupInProgress)
                            {
                                this._userCells[firstShipX][i].setupStatus = ESetupCellStatus.ContainsShip;
                            } else if (this._userCells[firstShipX][i].setupStatus == ESetupCellStatus.Empty
                                    || this._userCells[firstShipX][i].setupStatus == ESetupCellStatus.NearShip
                                    || this._userCells[firstShipX][i].setupStatus == ESetupCellStatus.NotAvailable)
                                break;

                        } else
                            break;
                    }
            }

            if (firstShipY - 1 >= 0)
            {
                if (this._userCells[firstShipX][firstShipY - 1].setupStatus == ESetupCellStatus.SetupInProgress)
                    for (int i = firstShipY - 1; i >= firstShipY - 4; i--)
                    {
                        if (i >= 0)
                        {
                            if (this._userCells[firstShipX][i].setupStatus == ESetupCellStatus.SetupInProgress)
                            {
                                this._userCells[firstShipX][i].setupStatus = ESetupCellStatus.ContainsShip;
                            } else if (this._userCells[firstShipX][i].setupStatus == ESetupCellStatus.Empty
                                    || this._userCells[firstShipX][i].setupStatus == ESetupCellStatus.NearShip
                                    || this._userCells[firstShipX][i].setupStatus == ESetupCellStatus.NotAvailable)
                                break;

                        } else
                            break;
                    }
            }
        }

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (this._userCells[i][j].setupStatus == ESetupCellStatus.NotAvailable
                        || this._userCells[i][j].setupStatus == ESetupCellStatus.SetupInProgress
                        || this._userCells[i][j].setupStatus == ESetupCellStatus.SetupInProgress_FirstShip)
                {
                    this._userCells[i][j].setupStatus = ESetupCellStatus.Empty;
                    this._userCells[i][j].setIcon(MapSupplier.ICO_CELL);
                } else if (this._userCells[i][j].setupStatus == ESetupCellStatus.NearShip)
                    this._userCells[i][j].setIcon(MapSupplier.ICO_CELL);
                else if (this._userCells[i][j].setupStatus == ESetupCellStatus.ContainsShip)
                    this._userCells[i][j].setIcon(MapSupplier.ICO_CELL_WITH_SHIP);
            }
        }

        if (isValidShip)
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    if (this._userCells[i][j].setupStatus == ESetupCellStatus.ContainsShip)
                    {
                        for (int x = i - 1; x <= i + 1; x++)
                        {
                            for (int y = j - 1; y <= j + 1; y++)
                            {
                                if (x >= 0 && x < 10 && y >= 0 && y < 10)
                                    if (this._userCells[x][y].setupStatus == ESetupCellStatus.Empty)
                                        this._userCells[x][y].setupStatus = ESetupCellStatus.NearShip;
                            }
                        }
                    }
                }
            }

        if (!isValidShip)
            JOptionPane.showMessageDialog(null, "Не схоже, що ви можете встановити такий корабель =(", "Попередження", JOptionPane.INFORMATION_MESSAGE);
        else
        {
            switch (shipType)
            {
                case 1:
                {
                    this._dashBoardInfoGUI.installedOneBoardShipsCount++;
                    if (this._dashBoardInfoGUI.installedOneBoardShipsCount == 4)
                        this._dashBoardInfoGUI.isOneBoardShipsInstalledAll = true;
                }
                break;
                case 2:
                {
                    this._dashBoardInfoGUI.installedTwoBoardShipsCount++;
                    if (this._dashBoardInfoGUI.installedTwoBoardShipsCount == 3)
                        this._dashBoardInfoGUI.isTwoBoardShipsInstalledAll = true;
                }
                break;
                case 3:
                {
                    this._dashBoardInfoGUI.installedThreeBoardShipsCount++;
                    if (this._dashBoardInfoGUI.installedThreeBoardShipsCount == 2)
                        this._dashBoardInfoGUI.isThreeBoardShipsInstalledAll = true;
                }
                break;
                case 4:
                {
                    this._dashBoardInfoGUI.installedFourBoardShipsCount++;
                    if (this._dashBoardInfoGUI.installedFourBoardShipsCount == 1)
                        this._dashBoardInfoGUI.isFourBoardShipsInstalledAll = true;
                }
                break;
            }

            this._RenderDash(this._dashBoardInfoGUI);
        }
    }
}
