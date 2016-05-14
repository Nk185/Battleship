package battleship;

import java.util.Random;

public class EnemyLogic implements IEnemyLogic
{
    private int _LastHitedX = -1;
    private int _LastHitedY = -1;
    private int _HitedCellsCount = 0;
    private int _MaxBoardShip = 4;
    private boolean _findVertical     = true;
    private boolean _isLeftCellChecked  = false;
    private boolean _isRightCellChecked = false;
    private boolean _isUpCellChecked    = false;
    private boolean _isDownCellChecked  = false;
    @Override
    // MapSettings (userMap) ALLWAYS WILL BE PASSED BY REFERENCE!!!!!
    public void Fire(MapSettings userMap) // TODO: create a logic
    {
        Random rand = new Random();
        boolean success = false;
        boolean moveDone = false;
        byte xCoord;
        byte yCoord;

       
        if (this._LastHitedX == -1 && this._LastHitedY == -1)
        {
            System.out.println("Random fire");
            while (!success)
            {
                xCoord = (byte) (rand.nextInt(10));
                yCoord = (byte) (rand.nextInt(10));
            
                if (userMap.Map[xCoord][yCoord] == ECellStatus.Hited || userMap.Map[xCoord][yCoord] == ECellStatus.Empty)
                    success = false;
                else
                {
                    System.out.println("Random fire: hits ("+(xCoord+1)+" ; "+(yCoord+1)+")");

                    if (userMap.Map[xCoord][yCoord] == ECellStatus.ContainsShip)
                    {
                        userMap.Map[xCoord][yCoord] = ECellStatus.Hited;
                        success = true;

                        if (!isOneBoardShip(xCoord, yCoord, userMap))
                        {
                            this._LastHitedX = xCoord;
                            this._LastHitedY = yCoord;
                        }
                        else SurroundShipWithEmptyCell(xCoord, yCoord, userMap, 'h');
                    }
                    else if (userMap.Map[xCoord][yCoord] == ECellStatus.ClosedEmpty || userMap.Map[xCoord][yCoord] == ECellStatus.LocatedNearShip)
                    {
                        userMap.Map[xCoord][yCoord] = ECellStatus.Empty;
                        success = true;
                    }
                }
            }
        }

        if (this._LastHitedX != -1 && this._LastHitedY != -1)            
        {
            System.out.println("Finder mode: on");

            if (!this._isLeftCellChecked && (this._LastHitedX - 1 >= 0))
            {
                System.out.println("Choosed direction is: left");

                for (int i = this._LastHitedX - 1; i >= this._LastHitedX - 4; i--)
                {
                    if (i >= 0 && (userMap.Map[i][this._LastHitedY] == ECellStatus.ContainsShip))
                    {
                        userMap.Map[i][this._LastHitedY] = ECellStatus.Hited;
                        this._findVertical = false;
                        this._HitedCellsCount++;
                    }
                    else
                    {
                        if (i >= 0 && userMap.Map[i][this._LastHitedY] == ECellStatus.LocatedNearShip)
                        {
                            userMap.Map[i][this._LastHitedY] = ECellStatus.Empty;
                            moveDone = true;
                        }

                        this._isLeftCellChecked = true;
                        break;
                    }
                }

                if (this._LastHitedX + 1 <= 9)
                    if (userMap.Map[this._LastHitedX + 1][this._LastHitedY] == ECellStatus.Empty)                    
                        this._isRightCellChecked = true;
            }
            else if (this._LastHitedX - 1 < 0)
                this._isLeftCellChecked = true;

            if (!this._isRightCellChecked && (this._LastHitedX + 1 <= 9) && !moveDone)
            {
                System.out.println("Choosed direction is: right");

                for (int i = this._LastHitedX + 1; i <= this._LastHitedX + 4; i++)
                {
                    if (i <= 9 && (userMap.Map[i][this._LastHitedY] == ECellStatus.ContainsShip))
                    {
                        userMap.Map[i][this._LastHitedY] = ECellStatus.Hited;
                        this._findVertical = false;
                        this._HitedCellsCount++;
                    }
                    else
                    {
                        if (i <= 9 && userMap.Map[i][this._LastHitedY] == ECellStatus.LocatedNearShip)
                        {
                            userMap.Map[i][this._LastHitedY] = ECellStatus.Empty;
                            moveDone = true;
                        }
                        this._isRightCellChecked = true;
                        break;
                    }
                }
            }
            else if (this._LastHitedX + 1 > 9)
                this._isRightCellChecked = true;

            /*if (this._findVertical)
            {
                if (!this._isUpCellChecked && !moveDone)
                {
                    System.out.println("Choosed direction is: up");

                    for (int i = this._LastHitedY - 1; i >= this._LastHitedY - 4; i--)
                    {
                       if (i >= 0 && (userMap.Map[this._LastHitedX][i] == ECellStatus.ContainsShip))
                       {
                            userMap.Map[this._LastHitedX][i] = ECellStatus.Hited;
                            this._HitedCellsCount++;
                       }
                       else
                       {
                          if (i >= 0 && userMap.Map[this._LastHitedX][i] == ECellStatus.LocatedNearShip)
                            {
                                userMap.Map[this._LastHitedX][i] = ECellStatus.Empty;
                                moveDone = true;
                            }

                            this._isUpCellChecked = true;
                            break;
                        }
                    }

                    if (this._LastHitedY + 1 <= 9)
                        if (userMap.Map[this._LastHitedX][this._LastHitedY + 1] == ECellStatus.Empty)                    
                            this._isDownCellChecked = true;     
                }

                if (!this._isDownCellChecked && !moveDone)
                {
                    System.out.println("Choosed direction is: down");

                    for (int i = this._LastHitedX + 1; i <= this._LastHitedX + 4; i++)
                    {
                        if (i <= 9 && (userMap.Map[this._LastHitedX][i] == ECellStatus.ContainsShip))
                        {
                            userMap.Map[this._LastHitedX][i] = ECellStatus.Hited;
                            this._HitedCellsCount++;
                        }
                        else
                        {
                            if (i <= 9 && userMap.Map[this._LastHitedX][i] == ECellStatus.LocatedNearShip)
                            {
                                userMap.Map[this._LastHitedX][i] = ECellStatus.Empty;
                                moveDone = true;
                            }
                            this._isDownCellChecked = true;
                            break;
                        }
                    }
                }
            }*/

            if (this._isLeftCellChecked && this._isRightCellChecked && !this._findVertical)
            {
                SurroundShipWithEmptyCell(this._LastHitedX, this._LastHitedY, userMap, 'h');

                this._LastHitedX = -1;
                this._LastHitedY = -1;

                this._isLeftCellChecked  = false;
                this._isRightCellChecked = false;
                this._isUpCellChecked    = false; 
                this._isDownCellChecked  = false;
                this._findVertical       = true;

                if (!moveDone)
                    this.Fire(userMap);
            }
            else if (this._isDownCellChecked && this._isUpCellChecked)
            {
                this._LastHitedX = -1;
                this._LastHitedY = -1;

                this._isLeftCellChecked  = false;
                this._isRightCellChecked = false;
                this._isUpCellChecked    = false;
                this._isDownCellChecked  = false;
                this._findVertical       = true;

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
            /*
            switch (boardNumber)
            {
                case 1:
                    if (oneBoardShips < 4)
                        if (MapWorker.SetShipByCoord(ms, xCoord, yCoord, boardNumber, direction))
                            oneBoardShips++;
                    break;
                case 2:
                    if (twoBoardShips < 3)
                        if (MapWorker.SetShipByCoord(ms, xCoord, yCoord, boardNumber, direction))
                            twoBoardShips++;
                    break;
                case 3:
                    if (threeBoardShips < 2)
                        if (MapWorker.SetShipByCoord(ms, xCoord, yCoord, boardNumber, direction))
                            threeBoardShips++;
                    break;
                case 4:
                    if (fourBoardShips < 1)
                        if (MapWorker.SetShipByCoord(ms, xCoord, yCoord, boardNumber, direction))
                            fourBoardShips++;
                    break;
            }*/
        }

        return ms;
    }

    // fix bugs
    private boolean isOneBoardShip(int xCoord, int yCoord, MapSettings userMap) 
    {
        boolean isOneBoardShip = false;

        if (xCoord - 1 >= 0)
        {
            if (userMap.Map[xCoord - 1][yCoord] == ECellStatus.LocatedNearShip || userMap.Map[xCoord - 1][yCoord] == ECellStatus.Empty)
                isOneBoardShip = true;
            else isOneBoardShip = false;
        }
        else if (xCoord - 1 < 0)
            isOneBoardShip = true;

        if (isOneBoardShip)
            if (xCoord + 1 <= 9)
            {
                if (userMap.Map[xCoord + 1][yCoord] == ECellStatus.LocatedNearShip || userMap.Map[xCoord + 1][yCoord] == ECellStatus.Empty)
                    isOneBoardShip = true;
                else isOneBoardShip = false;
            }
            else if (xCoord + 1 > 9)
                isOneBoardShip = true;

        if (isOneBoardShip)
            if (yCoord - 1 >= 0)
            {
                if (userMap.Map[xCoord][yCoord - 1] == ECellStatus.LocatedNearShip || userMap.Map[xCoord][yCoord - 1] == ECellStatus.Empty)
                    isOneBoardShip = true;
                else isOneBoardShip = false;
            }
            else if (yCoord - 1 < 0)
                isOneBoardShip = true;

        if (isOneBoardShip)
            if (yCoord + 1 <= 9)
            {
                if (userMap.Map[xCoord][yCoord + 1] == ECellStatus.LocatedNearShip || userMap.Map[xCoord][yCoord + 1] == ECellStatus.Empty)
                    isOneBoardShip = true;
                else isOneBoardShip = false;
            }
            else if (yCoord + 1 > 9)
                isOneBoardShip = true;

        return isOneBoardShip;
    }

    private void SurroundShipWithEmptyCell(int xCoord, int yCoord, MapSettings userMap, char shipDirection)
    {
        if (shipDirection == 'h') // horizontalShip
        {
            int lastBoardLeft = -1;
            int lastBoardRight = -1;

            for (int i = xCoord; i <= xCoord + 5; i++)
            {
                if (i <= 9)
                {
                    if (userMap.Map[i][yCoord] == ECellStatus.Hited)
                    {
                        if (yCoord - 1 >= 0)
                            userMap.Map[i][yCoord - 1] = ECellStatus.Empty;
                        if (yCoord + 1 <= 9)
                            userMap.Map[i][yCoord + 1] = ECellStatus.Empty;
                    }
                    else 
                    {
                        lastBoardRight = i;
                        break;
                    }
                }
                else
                {
                    lastBoardRight = -1;
                    break;
                }
            }

            for (int i = xCoord; i >= xCoord - 5; i--)
            {
                if (i >= 0)
                {
                    if (userMap.Map[i][yCoord] == ECellStatus.Hited)
                    {
                        if (yCoord - 1 >= 0)
                            userMap.Map[i][yCoord - 1] = ECellStatus.Empty;
                        if (yCoord + 1 <= 9)
                            userMap.Map[i][yCoord + 1] = ECellStatus.Empty;
                    }
                    else 
                    {
                        lastBoardLeft = i;
                        break;
                    }
                }
                else
                {
                    lastBoardLeft = -1;
                    break;
                }
            }

            if (lastBoardLeft != -1)
            {
                userMap.Map[lastBoardLeft][yCoord] = ECellStatus.Empty;
                
                if (yCoord - 1 >= 0)
                    userMap.Map[lastBoardLeft][yCoord - 1] = ECellStatus.Empty;
                
                if (yCoord + 1 <= 9)
                    userMap.Map[lastBoardLeft][yCoord + 1] = ECellStatus.Empty;
            }

            if (lastBoardRight != -1)
            {
                userMap.Map[lastBoardRight][yCoord] = ECellStatus.Empty;
                
                if (yCoord - 1 >= 0)
                    userMap.Map[lastBoardRight][yCoord - 1] = ECellStatus.Empty;
                
                if (yCoord + 1 <= 9)
                    userMap.Map[lastBoardRight][yCoord + 1] = ECellStatus.Empty;
            }

        }
        else if (shipDirection == 'v')
        {
            int lastBoardTop;
            int lastBoardBottom;
        }
    }
}
