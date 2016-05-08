package battleship;
/**
 * An enumeration of all possible cells conditions.
 * @author Nk185 
 */
public enum ECellStatus 
{
    /**
     * Empty opened cell.
     */
    Empty,
    /**
     * Cell contains whole ship or it's part.
     */
    ContainsShip,
    /**
     * Near this cell ship is located.
     * Note: reserved only for map setup process. In game assumed like ClosedEmpty.
     */
    LocatedNearShip,
    /**
     * This cell is not opened by user yet, but it's empty.
     */
    ClosedEmpty,
    /**
     * This cell contains whole ship or it's part and it was hited by an user.
     */
    Hited;
}
