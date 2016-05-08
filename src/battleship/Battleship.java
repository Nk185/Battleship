package battleship;

public class Battleship
{
    public static void main(String[] args)
    {
        IUser user          = new GUI();
        IEnemyLogic enemy   = new EnemyLogic();
        IControlLogic logic = new ControlLogic(user);
        
        logic.StartTheGame(); 
    }    
}
