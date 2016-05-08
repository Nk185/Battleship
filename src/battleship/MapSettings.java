package battleship;

public class MapSettings
{ 
    public ECellStatus[][] Map;       
    
    public MapSettings ()
    {
        this.Map = new ECellStatus[10][10]; // first - x, second - y. You should to use [x][y] when you 
        									// want to request or set data.
    }
}