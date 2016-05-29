package battleship;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

final class GUI_Setup_CellsListener implements MouseListener //TODO: optimize animation
{

    private Timer _tm;
    private int _defCellPosX;
    private int _defCellPosY;

    private static int _firstShipXCoord;
    private static int _firstShipYCoord;
    private static DashBoardInfoGUI _dashBoardInfoGUI;
    private static GUI_Setup_DashButton _setButton;
    private static GUI_FieldCell[][] _cells;
    private static JLabel[] _dashboardShipsIco;

    public GUI_Setup_CellsListener(GUI_FieldCell[][] cells,
            GUI_Setup_DashButton setButton, DashBoardInfoGUI mapSettings, JLabel[] dashboardShipsIco)
    {
        GUI_Setup_CellsListener._cells = cells;
        GUI_Setup_CellsListener._setButton = setButton;
        GUI_Setup_CellsListener._dashBoardInfoGUI = mapSettings;
        GUI_Setup_CellsListener._dashboardShipsIco = dashboardShipsIco;
    }

    @Override
    public void mouseClicked(MouseEvent event)
    {
        GUI_FieldCell cell = (GUI_FieldCell) event.getComponent();

        if (this._tm != null)
        {
            this._tm.stop();
            cell.setCellShadowVisible(false);
            cell.setLocation(this._defCellPosX, this._defCellPosY);
            cell.setSize(MapEngine.ICO_CELL.getIconWidth(),
                    MapEngine.ICO_CELL.getIconHeight());
            cell.container.setLayer(event.getComponent(), 0);
        }

        if (!(GUI_Setup_CellsListener._dashBoardInfoGUI.isOneBoardShipsInstalledAll
                && GUI_Setup_CellsListener._dashBoardInfoGUI.isTwoBoardShipsInstalledAll
                && GUI_Setup_CellsListener._dashBoardInfoGUI.isThreeBoardShipsInstalledAll
                && GUI_Setup_CellsListener._dashBoardInfoGUI.isFourBoardShipsInstalledAll))
        {

            if (cell.setupStatus == ESetupCellStatus.Empty)
            {
                cell.setupStatus = GUI_Setup_CellsListener._setButton.buttonEnabled == true
                        ? ESetupCellStatus.SetupInProgress : ESetupCellStatus.SetupInProgress_FirstShip;
                _UpdateCells(cell);
            } else if (cell.setupStatus == ESetupCellStatus.NotAvailable)
            {
                cell.setupStatus = ESetupCellStatus.Error;
                _UpdateCells(cell);
            } else if (cell.setupStatus == ESetupCellStatus.SetupInProgress
                    || cell.setupStatus == ESetupCellStatus.ContainsShip
                    || cell.setupStatus == ESetupCellStatus.SetupInProgress_FirstShip)
            {
                JOptionPane.showMessageDialog(null, "Ви вже встановили тут частину корабля",
                        "Попередження", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (cell.setupStatus == ESetupCellStatus.NearShip)
            {
                JOptionPane.showMessageDialog(null, "Занадто близько до іншого корабля. Спробуйте десь в іншому місці...",
                        "Попередження", JOptionPane.INFORMATION_MESSAGE);
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Ви вже налаштували поле. Натисніть кнопку \"Розпочати гру\" щоб продовжити.",
                    "Попередження", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void mouseEntered(MouseEvent event)
    {
        GUI_FieldCell cell = (GUI_FieldCell) event.getComponent();

        if (cell.setupStatus == ESetupCellStatus.Empty)
        {
            this._defCellPosX = event.getComponent().getX();
            this._defCellPosY = event.getComponent().getY();

            this._tm = new Timer(50, new Animation_Rotation((JLabel) event.getComponent()));

            cell.setSize(50, 50);
            cell.container.setLayer(event.getComponent(), 2);
            cell.setIcon(MapEngine.ICO_CELL_FOCUSED);
            cell.setCellShadowType(false);
            cell.setCellShadowVisible(true);
            this._tm.start();
        }
    }

    @Override
    public void mouseExited(MouseEvent event)
    {
        GUI_FieldCell cell = (GUI_FieldCell) event.getComponent();

        if (cell.setupStatus == ESetupCellStatus.Empty)
        {
            this._tm.stop();
            cell.setCellShadowVisible(false);
            cell.setLocation(this._defCellPosX, this._defCellPosY);
            cell.setIcon(MapEngine.ICO_CELL);
            cell.setSize(MapEngine.ICO_CELL.getIconWidth(),
                    MapEngine.ICO_CELL.getIconHeight());
            cell.container.setLayer(event.getComponent(), 0);
        }
    }

    @Override
    public void mousePressed(MouseEvent event)
    {
        GUI_FieldCell cell = (GUI_FieldCell) event.getComponent();

        if (cell.setupStatus == ESetupCellStatus.Empty)
        {
            cell.setIcon(MapEngine.ICO_CELL_FOCUSED_PRESSED);
            cell.setCellShadowType(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent event)
    {

    }

    private static void _UpdateCells(GUI_FieldCell currentCell)
    {
        boolean isError = false;
        int errorXCoord = 0;
        int errorYCoord = 0;        

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (GUI_Setup_CellsListener._cells[i][j].setupStatus == ESetupCellStatus.Error)
                {
                    isError = true;
                    errorXCoord = i;
                    errorYCoord = j;
                }
            }
        }

        if (isError)
        {
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    if (GUI_Setup_CellsListener._cells[i][j].setupStatus == ESetupCellStatus.NotAvailable
                            || GUI_Setup_CellsListener._cells[i][j].setupStatus == ESetupCellStatus.SetupInProgress
                            || GUI_Setup_CellsListener._cells[i][j].setupStatus == ESetupCellStatus.SetupInProgress_FirstShip)
                    {
                        GUI_Setup_CellsListener._cells[i][j].setupStatus = ESetupCellStatus.Empty;
                    }
                }
            }

            GUI_Setup_CellsListener._cells[errorXCoord][errorYCoord].setupStatus = ESetupCellStatus.SetupInProgress_FirstShip;
            _UpdateCells(GUI_Setup_CellsListener._cells[errorXCoord][errorYCoord]);
        } else if (currentCell.setupStatus == ESetupCellStatus.SetupInProgress_FirstShip)
        {
            int boardCount = 0; // To hint max available ship's cells in each direction

            currentCell.setIcon(MapEngine.ICO_CELL_WITH_SHIP);

            GUI_Setup_CellsListener._setButton.setIcon(MapEngine.BUTTON_SETUP_SETSHIP_ACTIVE);            
            GUI_Setup_CellsListener._setButton.buttonEnabled = true;
            
            GUI_Setup_CellsListener._firstShipXCoord = currentCell.xFieldPos;
            GUI_Setup_CellsListener._firstShipYCoord = currentCell.yFieldPos;

            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    if (GUI_Setup_CellsListener._cells[i][j].setupStatus == ESetupCellStatus.Empty)
                    {
                        GUI_Setup_CellsListener._cells[i][j].setupStatus = ESetupCellStatus.NotAvailable;
                        GUI_Setup_CellsListener._cells[i][j].setIcon(MapEngine.ICO_CELL_INACTIVE);
                    } else if (GUI_Setup_CellsListener._cells[i][j].setupStatus == ESetupCellStatus.NearShip)
                    {
                        GUI_Setup_CellsListener._cells[i][j].setIcon(MapEngine.ICO_CELL_INACTIVE);
                    } else if (GUI_Setup_CellsListener._cells[i][j].setupStatus == ESetupCellStatus.ContainsShip)
                        GUI_Setup_CellsListener._cells[i][j].setIcon(MapEngine.ICO_CELL_MASKED_SHIP);
                }
            }

            if (!GUI_Setup_CellsListener._dashBoardInfoGUI.isFourBoardShipsInstalledAll)
                boardCount = 4;
            else if (!GUI_Setup_CellsListener._dashBoardInfoGUI.isThreeBoardShipsInstalledAll)
                boardCount = 3;
            else if (!GUI_Setup_CellsListener._dashBoardInfoGUI.isTwoBoardShipsInstalledAll)
                boardCount = 2;
            else if (!GUI_Setup_CellsListener._dashBoardInfoGUI.isOneBoardShipsInstalledAll)
                boardCount = 1;

            for (int i = GUI_Setup_CellsListener._firstShipXCoord; i < GUI_Setup_CellsListener._firstShipXCoord + boardCount; i++)
            {
                if (i <= 9)
                {
                    if (GUI_Setup_CellsListener._cells[i][GUI_Setup_CellsListener._firstShipYCoord].setupStatus == ESetupCellStatus.NearShip)
                        break;
                    else if (GUI_Setup_CellsListener._cells[i][GUI_Setup_CellsListener._firstShipYCoord].setupStatus == ESetupCellStatus.NotAvailable)
                    {
                        GUI_Setup_CellsListener._cells[i][GUI_Setup_CellsListener._firstShipYCoord].setupStatus = ESetupCellStatus.Empty;
                        GUI_Setup_CellsListener._cells[i][GUI_Setup_CellsListener._firstShipYCoord].setIcon(MapEngine.ICO_CELL);
                    }
                }
            }

            for (int i = GUI_Setup_CellsListener._firstShipXCoord; i > GUI_Setup_CellsListener._firstShipXCoord - boardCount; i--)
            {
                if (i >= 0)
                {
                    if (GUI_Setup_CellsListener._cells[i][GUI_Setup_CellsListener._firstShipYCoord].setupStatus == ESetupCellStatus.NearShip)
                        break;
                    else if (GUI_Setup_CellsListener._cells[i][GUI_Setup_CellsListener._firstShipYCoord].setupStatus == ESetupCellStatus.NotAvailable)
                    {
                        GUI_Setup_CellsListener._cells[i][GUI_Setup_CellsListener._firstShipYCoord].setupStatus = ESetupCellStatus.Empty;
                        GUI_Setup_CellsListener._cells[i][GUI_Setup_CellsListener._firstShipYCoord].setIcon(MapEngine.ICO_CELL);
                    }
                }
            }

            for (int i = GUI_Setup_CellsListener._firstShipYCoord; i < GUI_Setup_CellsListener._firstShipYCoord + boardCount; i++)
            {
                if (i <= 9)
                {
                    if (GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][i].setupStatus == ESetupCellStatus.NearShip)
                        break;
                    else if (GUI_Setup_CellsListener._cells[_firstShipXCoord][i].setupStatus == ESetupCellStatus.NotAvailable)
                    {
                        GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][i].setupStatus = ESetupCellStatus.Empty;
                        GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][i].setIcon(MapEngine.ICO_CELL);
                    }
                }
            }

            for (int i = GUI_Setup_CellsListener._firstShipYCoord; i > GUI_Setup_CellsListener._firstShipYCoord - boardCount; i--)
            {
                if (i >= 0)
                {
                    if (GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][i].setupStatus == ESetupCellStatus.NearShip)
                        break;
                    else if (GUI_Setup_CellsListener._cells[_firstShipXCoord][i].setupStatus == ESetupCellStatus.NotAvailable)
                    {
                        GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][i].setupStatus = ESetupCellStatus.Empty;
                        GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][i].setIcon(MapEngine.ICO_CELL);
                    }
                }
            }
        } else if (currentCell.setupStatus == ESetupCellStatus.SetupInProgress)
        {
            currentCell.setIcon(MapEngine.ICO_CELL_WITH_SHIP);
            currentCell.setupStatus = ESetupCellStatus.SetupInProgress;
        }

        if (!isError) // To display current ship type on dashboard
        {
            if ((GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord + (GUI_Setup_CellsListener._firstShipXCoord == 9 ? 0 : 1)][GUI_Setup_CellsListener._firstShipYCoord].setupStatus == ESetupCellStatus.SetupInProgress
                    || GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord - (GUI_Setup_CellsListener._firstShipXCoord == 0 ? 0 : 1)][GUI_Setup_CellsListener._firstShipYCoord].setupStatus == ESetupCellStatus.SetupInProgress)
                    && (GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][GUI_Setup_CellsListener._firstShipYCoord + (GUI_Setup_CellsListener._firstShipYCoord == 9 ? 0 : 1)].setupStatus == ESetupCellStatus.SetupInProgress
                    || GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][GUI_Setup_CellsListener._firstShipYCoord - (GUI_Setup_CellsListener._firstShipYCoord == 0 ? 0 : 1)].setupStatus == ESetupCellStatus.SetupInProgress))
            {
                GUI_Setup_CellsListener._dashboardShipsIco[0].setIcon(MapEngine.ICO_DASH_SHIP_ERROR_4);
                GUI_Setup_CellsListener._dashboardShipsIco[1].setIcon(MapEngine.ICO_DASH_SHIP_ERROR_3);
                GUI_Setup_CellsListener._dashboardShipsIco[2].setIcon(MapEngine.ICO_DASH_SHIP_ERROR_2);
                GUI_Setup_CellsListener._dashboardShipsIco[3].setIcon(MapEngine.ICO_DASH_SHIP_ERROR_1);
            } else
            {
                int boardCount = 1;
                int offset   = 1;

                if (GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord + (GUI_Setup_CellsListener._firstShipXCoord == 9 ? 0 : 1)][GUI_Setup_CellsListener._firstShipYCoord].setupStatus == ESetupCellStatus.SetupInProgress
                        || GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord - (GUI_Setup_CellsListener._firstShipXCoord == 0 ? 0 : 1)][GUI_Setup_CellsListener._firstShipYCoord].setupStatus == ESetupCellStatus.SetupInProgress)
                {                    

                    while ((GUI_Setup_CellsListener._firstShipXCoord + offset <= 9)
                            && GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord + offset][GUI_Setup_CellsListener._firstShipYCoord].setupStatus == ESetupCellStatus.SetupInProgress)
                    {
                        boardCount++;
                        offset++;
                    }

                    offset = 1;

                    while ((GUI_Setup_CellsListener._firstShipXCoord - offset >= 0)
                            && GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord - offset][GUI_Setup_CellsListener._firstShipYCoord].setupStatus == ESetupCellStatus.SetupInProgress)
                    {
                        boardCount++;
                        offset++;
                    }
                } else if (GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][GUI_Setup_CellsListener._firstShipYCoord + (GUI_Setup_CellsListener._firstShipYCoord == 9 ? 0 : 1)].setupStatus == ESetupCellStatus.SetupInProgress
                        || GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][GUI_Setup_CellsListener._firstShipYCoord - (GUI_Setup_CellsListener._firstShipYCoord == 0 ? 0 : 1)].setupStatus == ESetupCellStatus.SetupInProgress)
                {

                    while ((GUI_Setup_CellsListener._firstShipYCoord + offset <= 9)
                            && GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][GUI_Setup_CellsListener._firstShipYCoord + offset].setupStatus == ESetupCellStatus.SetupInProgress)
                    {
                        boardCount++;
                        offset++;
                    }

                    offset = 1;

                    while ((GUI_Setup_CellsListener._firstShipYCoord - offset >= 0)
                            && GUI_Setup_CellsListener._cells[GUI_Setup_CellsListener._firstShipXCoord][GUI_Setup_CellsListener._firstShipYCoord - offset].setupStatus == ESetupCellStatus.SetupInProgress)
                    {
                        boardCount++;
                        offset++;
                    }
                }

                if (boardCount > 4)
                {
                    GUI_Setup_CellsListener._dashboardShipsIco[0].setIcon(MapEngine.ICO_DASH_SHIP_ERROR_4);
                    GUI_Setup_CellsListener._dashboardShipsIco[1].setIcon(MapEngine.ICO_DASH_SHIP_ERROR_3);
                    GUI_Setup_CellsListener._dashboardShipsIco[2].setIcon(MapEngine.ICO_DASH_SHIP_ERROR_2);
                    GUI_Setup_CellsListener._dashboardShipsIco[3].setIcon(MapEngine.ICO_DASH_SHIP_ERROR_1);
                } else
                {
                    GUI_Setup_CellsListener._dashboardShipsIco[0].setIcon(GUI_Setup_CellsListener._dashBoardInfoGUI.isFourBoardShipsInstalledAll
                            ? MapEngine.ICO_DASH_SHIP_INACTIVE_4 : MapEngine.ICO_DASH_SHIP_NORMAL_4);
                    GUI_Setup_CellsListener._dashboardShipsIco[1].setIcon(GUI_Setup_CellsListener._dashBoardInfoGUI.isThreeBoardShipsInstalledAll
                            ? MapEngine.ICO_DASH_SHIP_INACTIVE_3 : MapEngine.ICO_DASH_SHIP_NORMAL_3);
                    GUI_Setup_CellsListener._dashboardShipsIco[2].setIcon(GUI_Setup_CellsListener._dashBoardInfoGUI.isTwoBoardShipsInstalledAll
                            ? MapEngine.ICO_DASH_SHIP_INACTIVE_2 : MapEngine.ICO_DASH_SHIP_NORMAL_2);
                    GUI_Setup_CellsListener._dashboardShipsIco[3].setIcon(GUI_Setup_CellsListener._dashBoardInfoGUI.isOneBoardShipsInstalledAll
                            ? MapEngine.ICO_DASH_SHIP_INACTIVE_1 : MapEngine.ICO_DASH_SHIP_NORMAL_1);

                    switch (boardCount)
                    {
                        case 4:
                            GUI_Setup_CellsListener._dashboardShipsIco[0].setIcon(GUI_Setup_CellsListener._dashBoardInfoGUI.isFourBoardShipsInstalledAll
                                    ? MapEngine.ICO_DASH_SHIP_INACTIVE_WRONG_4 : MapEngine.ICO_DASH_SHIP_ACTIVE_4);
                            break;
                        case 3:
                            GUI_Setup_CellsListener._dashboardShipsIco[1].setIcon(GUI_Setup_CellsListener._dashBoardInfoGUI.isThreeBoardShipsInstalledAll
                                    ? MapEngine.ICO_DASH_SHIP_INACTIVE_WRONG_3 : MapEngine.ICO_DASH_SHIP_ACTIVE_3);
                            break;
                        case 2:
                            GUI_Setup_CellsListener._dashboardShipsIco[2].setIcon(GUI_Setup_CellsListener._dashBoardInfoGUI.isTwoBoardShipsInstalledAll
                                    ? MapEngine.ICO_DASH_SHIP_INACTIVE_WRONG_2 : MapEngine.ICO_DASH_SHIP_ACTIVE_2);
                            break;
                        case 1:
                            GUI_Setup_CellsListener._dashboardShipsIco[3].setIcon(GUI_Setup_CellsListener._dashBoardInfoGUI.isOneBoardShipsInstalledAll
                                    ? MapEngine.ICO_DASH_SHIP_INACTIVE_WRONG_1 : MapEngine.ICO_DASH_SHIP_ACTIVE_1);
                            break;
                    }
                }
            }
        }
    }

    private class Animation_Rotation implements ActionListener
    {

        private final JLabel __dist;
        private final int __defButtonPosX;
        private final int __defButtonPosY;
        private double __angle = 0.0;

        public Animation_Rotation(JLabel distCell)
        {
            __dist = distCell;
            __defButtonPosX = distCell.getX() - 2;
            __defButtonPosY = distCell.getY() - 4;
            __dist.setLocation(__defButtonPosX, __defButtonPosY);
        }

        @Override
        public void actionPerformed(ActionEvent a)
        {
            __dist.setLocation(__dist.getX() + (int) (Math.cos(__angle) * 1.5),
                    __dist.getY() + (int) (Math.sin(__angle) * 1.5));
            __angle += 0.47;

            if (__angle >= 2 * Math.PI)
            {
                __angle = 0.0;
                __dist.setLocation(__defButtonPosX, __defButtonPosY);
            }

        }
    }
}
