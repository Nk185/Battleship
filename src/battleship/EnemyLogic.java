package battleship;

import java.util.Random;

public class EnemyLogic implements IEnemyLogic
{

    @Override
    // MapSettings (userMap) ALLWAYS WILL BE PASSED BY REFERENCE!!!!!
    public void Fire(MapSettings userMap) // TODO: create a logic
    {
        Random rand = new Random();
        byte xCoord;
        byte yCoord;

        xCoord = (byte) (rand.nextInt(10));
        yCoord = (byte) (rand.nextInt(10));

        if (userMap.Map[xCoord][yCoord] == ECellStatus.ContainsShip)
            userMap.Map[xCoord][yCoord] = ECellStatus.Hited;
        else if (userMap.Map[xCoord][yCoord] == ECellStatus.ClosedEmpty || userMap.Map[xCoord][yCoord] == ECellStatus.LocatedNearShip)
            userMap.Map[xCoord][yCoord] = ECellStatus.Empty;
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
}
