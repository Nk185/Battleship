package battleship;

public interface IEnemyLogic
{   
    void fire(MapSettings userMap);
    MapSettings generateEnemyMap();
}