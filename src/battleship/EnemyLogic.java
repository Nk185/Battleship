package battleship;

import java.util.Random;

public class EnemyLogic implements IEnemyLogic
{
    class EnemyFireHistory 
    {
        private int _LastHitedX = -1;
        private int _LastHitedY = -1;
        private boolean _findVertical     = true;
        private boolean _isLeftCellChecked  = false;
        private boolean _isRightCellChecked = false;
        private boolean _isUpCellChecked    = false;
        private boolean _isDownCellChecked  = false;
    }

    @Override
    // MapSettings (userMap) ALLWAYS WILL BE PASSED BY REFERENCE!!!!!
    public void Fire(MapSettings userMap)
    {
        Random rand = new Random();
        EnemyFireHistory history = new EnemyFireHistory();
        boolean success = false;
        boolean moveDone = false;
        byte xCoord;
        byte yCoord;

       
        if (history._LastHitedX == -1 && history._LastHitedY == -1)
        {
            while (!success)
            {
                xCoord = (byte) (rand.nextInt(10));
                yCoord = (byte) (rand.nextInt(10));
            
                if (userMap.Map[xCoord][yCoord] == ECellStatus.Hited || userMap.Map[xCoord][yCoord] == ECellStatus.Empty)
                    success = false;
                else
                {
                    if (userMap.Map[xCoord][yCoord] == ECellStatus.ContainsShip)
                    {
                        userMap.Map[xCoord][yCoord] = ECellStatus.Hited;
                        success = true;

                        if (!MapEngine.isOneBoardShip(xCoord, yCoord, userMap))
                        {
                            history._LastHitedX = xCoord;
                            history._LastHitedY = yCoord;
                        }
                        else
                        {
                            MapEngine.SurroundShipWithEmptyCell(xCoord, yCoord, userMap, 'h');
                            success = false;
                        }
                    }
                    else if (userMap.Map[xCoord][yCoord] == ECellStatus.ClosedEmpty || userMap.Map[xCoord][yCoord] == ECellStatus.LocatedNearShip)
                    {
                        userMap.Map[xCoord][yCoord] = ECellStatus.Empty;
                        success = true;
                    }
                }
            }
        }



        if (history._LastHitedX != -1 && history._LastHitedY != -1)            
        {
            if (!history._isLeftCellChecked && (history._LastHitedX - 1 >= 0))
            {
                for (int i = history._LastHitedX - 1; i >= history._LastHitedX - 4; i--)
                {
                    if (i >= 0 && (userMap.Map[i][history._LastHitedY] == ECellStatus.ContainsShip))
                    {
                        userMap.Map[i][history._LastHitedY] = ECellStatus.Hited;
                        history._findVertical = false;
                    }
                    else
                    {
                        if (i >= 0 && userMap.Map[i][history._LastHitedY] == ECellStatus.LocatedNearShip && !MapEngine.isShipDestroyed(i, history._LastHitedY, userMap, 'h'))
                        {
                            userMap.Map[i][history._LastHitedY] = ECellStatus.Empty;
                            moveDone = true;
                        }
                        else if (i >= 0 && MapEngine.isShipDestroyed(i, history._LastHitedY, userMap, 'h'))
                        {
                            history._isRightCellChecked = true;
                            moveDone = false;
                        }

                        history._isLeftCellChecked = true;
                        break;
                    }
                }

                if (history._LastHitedX + 1 <= 9)
                {
                    if (userMap.Map[history._LastHitedX + 1][history._LastHitedY] == ECellStatus.Empty)                    
                        history._isRightCellChecked = true;
                    else if (userMap.Map[history._LastHitedX + 1][history._LastHitedY] == ECellStatus.LocatedNearShip)
                        history._isRightCellChecked = true;
                }
            }
            else if (history._LastHitedX - 1 < 0)
                history._isLeftCellChecked = true;



            if (!history._isRightCellChecked && (history._LastHitedX + 1 <= 9) && !moveDone)
            {
                for (int i = history._LastHitedX + 1; i <= history._LastHitedX + 4; i++)
                {
                    if (i <= 9 && (userMap.Map[i][history._LastHitedY] == ECellStatus.ContainsShip))
                    {
                        userMap.Map[i][history._LastHitedY] = ECellStatus.Hited;
                        history._findVertical = false;
                    }
                    else
                    {
                        if (i <= 9 && userMap.Map[i][history._LastHitedY] == ECellStatus.LocatedNearShip && !MapEngine.isShipDestroyed(i, history._LastHitedY, userMap, 'h'))
                        {
                            userMap.Map[i][history._LastHitedY] = ECellStatus.Empty;
                            moveDone = true;
                        }
                        else if (i <= 9 && MapEngine.isShipDestroyed(i, history._LastHitedY, userMap, 'h'))
                            moveDone = false;
                        

                        history._isRightCellChecked = true;
                        break;
                    }
                }
            }
            else if (history._LastHitedX + 1 > 9)
                history._isRightCellChecked = true;



            if (history._findVertical == true) // fix down direction
            {
                if (!history._isUpCellChecked && (history._LastHitedY - 1 >= 0) && !moveDone)
                {
                    for (int i = history._LastHitedY - 1; i >= history._LastHitedY - 4; i--)
                    {
                       if (i >= 0 && (userMap.Map[history._LastHitedX][i] == ECellStatus.ContainsShip))
                       {
                            userMap.Map[history._LastHitedX][i] = ECellStatus.Hited;
                       }
                       else
                       {
                            if (i >= 0 && userMap.Map[history._LastHitedX][i] == ECellStatus.LocatedNearShip && !MapEngine.isShipDestroyed(history._LastHitedX, i, userMap, 'v'))
                            {
                                userMap.Map[history._LastHitedX][i] = ECellStatus.Empty;
                                moveDone = true;
                            }
                            else if (i >= 0 && MapEngine.isShipDestroyed(history._LastHitedX, i, userMap, 'v'))
                            {
                                history._isDownCellChecked = true;
                                moveDone = false;
                            }

                            history._isUpCellChecked = true;
                            break;
                        }
                    }

                    if (history._LastHitedY + 1 <= 9)
                    {
                        if (userMap.Map[history._LastHitedX][history._LastHitedY + 1] == ECellStatus.Empty
                                || userMap.Map[history._LastHitedX][history._LastHitedY + 1] == ECellStatus.LocatedNearShip)
                            history._isDownCellChecked = true;
                    }

                }
                else if (history._LastHitedY - 1 < 0)
                    history._isUpCellChecked = true;

                if (!history._isDownCellChecked && (history._LastHitedY + 1 <= 9) && !moveDone)
                {
                    for (int i = history._LastHitedY + 1; i <= history._LastHitedY + 4; i++)
                    {
                        if (i <= 9 && (userMap.Map[history._LastHitedX][i] == ECellStatus.ContainsShip))
                        {
                            userMap.Map[history._LastHitedX][i] = ECellStatus.Hited;
                        }
                        else
                        {
                            if (i <= 9 && userMap.Map[history._LastHitedX][i] == ECellStatus.LocatedNearShip && !MapEngine.isShipDestroyed(history._LastHitedX, i, userMap, 'v'))
                            {
                                userMap.Map[history._LastHitedX][i] = ECellStatus.Empty;
                                moveDone = true;
                            }
                            else if (i <= 9 && MapEngine.isShipDestroyed(history._LastHitedX, i, userMap, 'v'))
                                moveDone = false;

                            history._isDownCellChecked = true;
                            break;
                        }
                    }
                }
                else if (history._LastHitedY + 1 > 9)
                    history._isDownCellChecked = true;
            }

            if (history._isLeftCellChecked && history._isRightCellChecked && !history._findVertical)
            {
                MapEngine.SurroundShipWithEmptyCell(history._LastHitedX, history._LastHitedY, userMap, 'h');

                history._LastHitedX = -1;
                history._LastHitedY = -1;

                history._isLeftCellChecked  = false;
                history._isRightCellChecked = false;
                history._isUpCellChecked    = false; 
                history._isDownCellChecked  = false;
                history._findVertical       = true;

                if (!moveDone)
                    this.Fire(userMap);
            }
            else if (history._isDownCellChecked && history._isUpCellChecked)
            {
                MapEngine.SurroundShipWithEmptyCell(history._LastHitedX, history._LastHitedY, userMap, 'v');

                history._LastHitedX = -1;
                history._LastHitedY = -1;

                history._isLeftCellChecked  = false;
                history._isRightCellChecked = false;
                history._isUpCellChecked    = false;
                history._isDownCellChecked  = false;
                history._findVertical       = true;

                if (!moveDone)
                    this.Fire(userMap);                
            }
        }
    }

    @Override
    public MapSettings GenerateEnemyMap()
    {
        MapSettings ms = new MapSettings();
        Random rand    = new Random();
        byte oneBoardShips   = 0; // Total count 4.
        byte twoBoardShips   = 0; // Total count 3.
        byte threeBoardShips = 0; // Total count 2.
        byte fourBoardShips  = 0; // Total count 1.
        byte xCoord; // For ship set-up. X coordinate of ship [1..10].
        byte yCoord; // For ship set-up. Y coordinate of ship [1..10].
        byte boardNumber; // For ship set-up. Boards number [1..4].
        String direction; // For ship set-up. Ship direction l, r, u or d.

        for (int i = 0; i<10; i++) 
            for (int j = 0; j<10; j++)
                ms.Map[i][j] = ECellStatus.ClosedEmpty;

        while ((oneBoardShips < 4) || (twoBoardShips < 3) || (threeBoardShips < 2)
                || (fourBoardShips < 1))
        {
            boardNumber = (byte) (rand.nextInt(4) + 1);
            xCoord      = (byte) (rand.nextInt(10) + 1);
            yCoord      = (byte) (rand.nextInt(10) + 1);

            switch (rand.nextInt(4))
            {
                case 0:
                    direction = "l";
                    break;
                case 1:
                    direction = "r";
                    break;
                case 2:
                    direction = "u";
                    break;
                case 3:
                    direction = "d";
                    break;
                default:
                    direction = "l";
                    break;
            }

            switch (boardNumber)
            {
                case 1:
                    if (oneBoardShips < 4)
                        if (MapEngine.SetShipByCoord(ms, xCoord, yCoord, boardNumber, direction))
                            oneBoardShips++;
                    break;
                case 2:
                    if (twoBoardShips < 3)
                        if (MapEngine.SetShipByCoord(ms, xCoord, yCoord, boardNumber, direction))
                            twoBoardShips++;
                    break;
                case 3:
                    if (threeBoardShips < 2)
                        if (MapEngine.SetShipByCoord(ms, xCoord, yCoord, boardNumber, direction))
                            threeBoardShips++;
                    break;
                case 4:
                    if (fourBoardShips < 1)
                        if (MapEngine.SetShipByCoord(ms, xCoord, yCoord, boardNumber, direction))
                            fourBoardShips++;
                    break;
            }
        }

        return ms;
    }
}
