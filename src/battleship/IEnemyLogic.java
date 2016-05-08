package battleship;

public interface IEnemyLogic
{   
    void Fire(MapSettings userMap);
    MapSettings GenerateEnemyMap();
}