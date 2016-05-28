package battleship;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

class GUI_Setup_CellsListener implements MouseListener
{

    private Timer _tm;
    private int _defCellPosX;
    private int _defCellPosY;
    private final DashBoardInfoGUI _dashBoardInfoGUI;
    private final GUI_Setup_DashButton _setButton;
    private final GUI_FieldCell[][] _cells;

    public GUI_Setup_CellsListener(GUI_FieldCell[][] cells,
            GUI_Setup_DashButton setButton, DashBoardInfoGUI mapSettings)
    {
        this._cells = cells;
        this._setButton = setButton;
        this._dashBoardInfoGUI = mapSettings;
    }

    @Override
    public void mouseClicked(MouseEvent event)
    {
        GUI_FieldCell cell = (GUI_FieldCell) event.getComponent();

        if (_tm != null)
        {
            _tm.stop();
            cell.setCellShadowVisible(false);
            cell.setLocation(this._defCellPosX, this._defCellPosY);
            cell.setSize(MapEngine.ICO_CELL.getIconWidth(),
                    MapEngine.ICO_CELL.getIconHeight());            
            cell.container.setLayer(event.getComponent(), 0);
        }

        if (!(this._dashBoardInfoGUI.isOneBoardShipsInstalledAll && this._dashBoardInfoGUI.isTwoBoardShipsInstalledAll
                && this._dashBoardInfoGUI.isThreeBoardShipsInstalledAll && this._dashBoardInfoGUI.isFourBoardShipsInstalledAll))
        {

            if (cell.setupStatus == ESetupCellStatus.Empty)
            {
                cell.setupStatus = this._setButton.buttonEnabled == true ? ESetupCellStatus.SetupInProgress : ESetupCellStatus.SetupInProgress_FirstShip;
                _UpdateCells(cell);
            } else if (cell.setupStatus == ESetupCellStatus.NotAvailable)
            {
                cell.setupStatus = ESetupCellStatus.Error;
                _UpdateCells(cell);
            } else if (cell.setupStatus == ESetupCellStatus.SetupInProgress
                    || cell.setupStatus == ESetupCellStatus.ContainsShip
                    || cell.setupStatus == ESetupCellStatus.SetupInProgress_FirstShip)
                JOptionPane.showMessageDialog(null, "Ви вже встановили тут частину корабля", "Попередження", JOptionPane.INFORMATION_MESSAGE);
            else if (cell.setupStatus == ESetupCellStatus.NearShip)
                JOptionPane.showMessageDialog(null, "Занадто близько до іншого корабля. Спробуйте десь в іншому місці...", "Попередження", JOptionPane.INFORMATION_MESSAGE);
        }
        else JOptionPane.showMessageDialog(null, "Ви вже налаштували поле. Натисніть кнопку \"Розпочати гру\" щоб продовжити.",
                "Попередження", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mouseEntered(MouseEvent event)
    {
        GUI_FieldCell cell = (GUI_FieldCell) event.getComponent();

        if (cell.setupStatus == ESetupCellStatus.Empty)
        {
            this._defCellPosX = event.getComponent().getX();
            this._defCellPosY = event.getComponent().getY();

            _tm = new Timer(50, new Animation_Rotation((JLabel) event.getComponent()));

            cell.setSize(50, 50);
            cell.container.setLayer(event.getComponent(), 2);
            cell.setIcon(MapEngine.ICO_CELL_FOCUSED);
            cell.setCellShadowType(false);
            cell.setCellShadowVisible(true);
            _tm.start();
        }
    }

    @Override
    public void mouseExited(MouseEvent event)
    {
        GUI_FieldCell cell = (GUI_FieldCell) event.getComponent();

        if (cell.setupStatus == ESetupCellStatus.Empty)
        {
            _tm.stop();
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

    private void _UpdateCells(GUI_FieldCell currentCell)
    {
        boolean isError = false;
        int errorXCoord = 0;
        int errorYCoord = 0;
        int firstShipXCoord = 0;
        int firstShipYCoord = 0;
        int boardCount = 0;

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (this._cells[i][j].setupStatus == ESetupCellStatus.Error)
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
                    if (this._cells[i][j].setupStatus    == ESetupCellStatus.NotAvailable
                        || this._cells[i][j].setupStatus == ESetupCellStatus.SetupInProgress
                        || this._cells[i][j].setupStatus == ESetupCellStatus.SetupInProgress_FirstShip)
                    {
                        this._cells[i][j].setupStatus = ESetupCellStatus.Empty;
                    }
                }            
            }

            this._cells[errorXCoord][errorYCoord].setupStatus = ESetupCellStatus.SetupInProgress_FirstShip;
            _UpdateCells(this._cells[errorXCoord][errorYCoord]);
        } else if (currentCell.setupStatus == ESetupCellStatus.SetupInProgress_FirstShip)
        {
            this._setButton.buttonEnabled = true;
            this._setButton.setIcon(MapEngine.BUTTON_SETUP_SETSHIP_ACTIVE);

            currentCell.setIcon(MapEngine.ICO_CELL_WITH_SHIP);
            firstShipXCoord = currentCell.xFieldPos;
            firstShipYCoord = currentCell.yFieldPos;


            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    if (this._cells[i][j].setupStatus == ESetupCellStatus.Empty)
                    {
                        this._cells[i][j].setupStatus = ESetupCellStatus.NotAvailable;
                        this._cells[i][j].setIcon(MapEngine.ICO_CELL_INACTIVE);
                    } 
                    else if (this._cells[i][j].setupStatus == ESetupCellStatus.NearShip)
                    {
                        this._cells[i][j].setIcon(MapEngine.ICO_CELL_INACTIVE);                    
                    }
                    else if (this._cells[i][j].setupStatus == ESetupCellStatus.ContainsShip)
                        this._cells[i][j].setIcon(MapEngine.ICO_CELL_MASKED_SHIP);                    
                }
            }

            if (!this._dashBoardInfoGUI.isFourBoardShipsInstalledAll)
                boardCount = 4;
            else if (!this._dashBoardInfoGUI.isThreeBoardShipsInstalledAll)
                boardCount = 3;
            else if (!this._dashBoardInfoGUI.isTwoBoardShipsInstalledAll)
                boardCount = 2;
            else if (!this._dashBoardInfoGUI.isOneBoardShipsInstalledAll)
                boardCount = 1;

            for (int i = firstShipXCoord; i < firstShipXCoord + boardCount; i++)
            {
                if (i <= 9)
                {
                    if (this._cells[i][firstShipYCoord].setupStatus == ESetupCellStatus.NearShip)
                        break;
                    else if (this._cells[i][firstShipYCoord].setupStatus == ESetupCellStatus.NotAvailable)
                    {
                        this._cells[i][firstShipYCoord].setupStatus = ESetupCellStatus.Empty;
                        this._cells[i][firstShipYCoord].setIcon(MapEngine.ICO_CELL);
                    }
                }
            }

            for (int i = firstShipXCoord; i > firstShipXCoord - boardCount; i--)
            {
                if (i >= 0)
                {
                    if (this._cells[i][firstShipYCoord].setupStatus == ESetupCellStatus.NearShip)
                        break;
                    else if (this._cells[i][firstShipYCoord].setupStatus == ESetupCellStatus.NotAvailable)
                    {
                        this._cells[i][firstShipYCoord].setupStatus = ESetupCellStatus.Empty;
                        this._cells[i][firstShipYCoord].setIcon(MapEngine.ICO_CELL);
                    }
                }
            }

            for (int i = firstShipYCoord; i < firstShipYCoord + boardCount; i++)
            {
                if (i <= 9)
                {
                    if (this._cells[firstShipXCoord][i].setupStatus == ESetupCellStatus.NearShip)
                        break;
                    else if (this._cells[firstShipXCoord][i].setupStatus == ESetupCellStatus.NotAvailable)
                    {
                        this._cells[firstShipXCoord][i].setupStatus = ESetupCellStatus.Empty;
                        this._cells[firstShipXCoord][i].setIcon(MapEngine.ICO_CELL);
                    }
                }
            }

            for (int i = firstShipYCoord; i > firstShipYCoord - boardCount; i--)
            {
                if (i >= 0)
                {
                    if (this._cells[firstShipXCoord][i].setupStatus == ESetupCellStatus.NearShip)
                        break;
                    else if (this._cells[firstShipXCoord][i].setupStatus == ESetupCellStatus.NotAvailable)
                    {
                        this._cells[firstShipXCoord][i].setupStatus = ESetupCellStatus.Empty;
                        this._cells[firstShipXCoord][i].setIcon(MapEngine.ICO_CELL);
                    }
                }
            }
        } else if (currentCell.setupStatus == ESetupCellStatus.SetupInProgress)
        {
            currentCell.setIcon(MapEngine.ICO_CELL_WITH_SHIP);
            currentCell.setupStatus = ESetupCellStatus.SetupInProgress;
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
            __defButtonPosX = distCell.getX()-2/* - 12*/;
            __defButtonPosY = distCell.getY() - 4/* - 21*/;
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
