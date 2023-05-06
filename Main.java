import java.util.*;

public class Main {
    public static int numRows = 10;
    public static int numCols = 10;
    public static int playerShips;
    public static int computerShips;
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];

    public static void main(String[] args){
        System.out.println("**** Welcome to Battle Ships game ****");
        System.out.println("Right now, sea is empty\n");

        createOceanMap();

        deployPlayerShips();

        deployComputerShips();

        do {
            Battle();
        }while(Main.playerShips != 0 && Main.computerShips != 0);

        gameOver();
    }

    public static void createOceanMap(){
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
                System.out.print(i);
        System.out.println();

        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
                if (j == 0)
                    System.out.print(i + "|" + grid[i][j]);
                else if (j == grid[i].length - 1)
                    System.out.print(grid[i][j] + "|" + i);
                else
                    System.out.print(grid[i][j]);
            }
            System.out.println();
        }

        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }

    public static void deployPlayerShips(){
        Scanner input = new Scanner(System.in);

        System.out.println("\nDeploy your ships:");
        Main.playerShips = 5;
        for (int i = 1; i <= Main.playerShips; ) {
            System.out.print("Enter X coordinate for your " + i + " ship: ");
            int x = input.nextInt();
            System.out.print("Enter Y coordinate for your " + i + " ship: ");
            int y = input.nextInt();

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
            {
                grid[x][y] =   "@";
                i++;
            }
            else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "@")
                System.out.println("You can't place two or more ships on the same location");
            else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
        }
        printOceanMap();
    }

    public static void deployComputerShips(){
        System.out.println("\nComputer is deploying ships");
        Main.computerShips = 5;
        for (int i = 1; i <= Main.computerShips; ) {
            int x = (int)(Math.random() * 10);
            int y = (int)(Math.random() * 10);

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
            {
                grid[x][y] =   "x";
                System.out.println(i + ". ship DEPLOYED");
                i++;
            }
        }
        printOceanMap();
    }

    public static void Battle(){
        playerTurn();
        computerTurn();

        printOceanMap();

        System.out.println();
        System.out.println("Your ships: " + Main.playerShips + " | Computer ships: " + Main.computerShips);
        System.out.println();
    }

    public static void playerTurn(){
        System.out.println("\nYOUR TURN");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter X coordinate: ");
            x = input.nextInt();
            System.out.print("Enter Y coordinate: ");
            y = input.nextInt();

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) 
            {
                if (grid[x][y] == "x") 
                {
                    System.out.println("Boom! You sunk the ship!");
                    grid[x][y] = "!"; 
                    --Main.computerShips;
                }
                else if (grid[x][y] == "@") {
                    System.out.println("Oh no, you sunk your own ship :(");
                    grid[x][y] = "x";
                    --Main.playerShips;
                    ++Main.computerShips;
                }
                else if (grid[x][y] == " ") {
                    System.out.println("Sorry, you missed");
                    grid[x][y] = "-";
                }
            }
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  
                System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols)); 
    }

    public static void computerTurn(){
        System.out.println("\nCOMPUTER'S TURN");
        
        int x = -1, y = -1;
        do {
            x = (int)(Math.random() * 10);
            y = (int)(Math.random() * 10);

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
            {
                if (grid[x][y] == "@")
                {
                    System.out.println("The Computer sunk one of your ships!");
                    grid[x][y] = "x";
                    --Main.playerShips;
                    ++Main.computerShips;
                }
                else if (grid[x][y] == "x") {
                    System.out.println("The Computer sunk one of its own ships");
                    grid[x][y] = "!";
                }
                else if (grid[x][y] == " ") {
                    System.out.println("Computer missed");
                    
                    if(missedGuesses[x][y] != 1)
                        missedGuesses[x][y] = 1;
                }
            }
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols)); 
    }

    public static void gameOver(){
        System.out.println("Your ships: " + Main.playerShips + " | Computer ships: " + Main.computerShips);
        if(Main.playerShips > 0 && Main.computerShips <= 0)
            System.out.println("Hooray! You won the battle :)");
        else
            System.out.println("Sorry, you lost the battle");
        System.out.println();
   }

    public static void printOceanMap(){
        System.out.println();
        
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

       
        for(int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < grid[x].length; y++){
                System.out.print(grid[x][y]);
            }

            System.out.println("|" + x);
        }

        
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }
}
