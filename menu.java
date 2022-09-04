import java.util.Random;
import java.util.Scanner;

public class menu {

    private leaderboard Leaderboard;
    private game Game;
    private bestMove Best;
    private Scanner input = new Scanner(System.in);
    private Random coinFlip = new Random();
    private String Player1name;
	private String Player2name;

    public menu(){
        Leaderboard = new leaderboard();
        Game = new game();
        Best = new bestMove();
        mainMenu();
    }


    public void setPlayer1name(String p1){
        if (p1.equals("")){
            System.out.println("Name cannot be empty!");
            System.out.println("Enter Player 1 name:");
            setPlayer1name(input.nextLine());
        }
        else Player1name = p1;
    }
    
    public void setPlayer2name(String p2){
        
        if (p2.equals("")){
            System.out.println("Name cannot be empty!");
            System.out.println("Enter Player 2 name:");
            setPlayer2name(input.nextLine());
        }
        else Player2name = p2;
    }

    public String getPlayer1name(){
        return Player1name;
    }
    
    public String getPlayer2name(){
        return Player2name;
    }
    
    public void mainMenu(){
        System.out.println("Mancala");
        System.out.println("Top 3 Highscore");
        System.out.println("==============");
        Leaderboard.showHighscore();
        System.out.println("Press [1] to play...");

        String menu = input.nextLine();

        if (menu.equals("1") ){
            difficultySelection();
        }
        else {
            mainMenu();
        }
    }


    public void difficultySelection(){
        System.out.println("[1] Beginner: 4 seeds, 6 cups");
        System.out.println("[2] Intermediate: changable seeds and cups");
        System.out.println("[3] Advance: changable seeds, cups and recomended move");
        System.out.println("[4] Back");

        String selection = input.nextLine();

        if (selection.equals("1") ){
            System.out.println("Enter Player 1 name:");
            setPlayer1name(input.nextLine());

            System.out.println("Enter Player 2 name:");
            setPlayer2name(input.nextLine());

            Game.setHoles(6);
            Game.setSeeds(4);

            firstTurn();

        }
        else if (selection.equals("2")){
            System.out.println("Enter Player 1 name:");
            setPlayer1name(input.nextLine());

            System.out.println("Enter Player 2 name:");
            setPlayer2name(input.nextLine());

            System.out.println("Enter number of holes:");
            Game.setHoles(input.nextInt());

            System.out.println("Enter number of seeds:");
            Game.setSeeds(input.nextInt());

            firstTurn();
        }
        else if (selection.equals("3")){
            System.out.println("Enter Player 1 name:");
            setPlayer1name(input.nextLine());

            System.out.println("Enter Player 2 name:");
            setPlayer2name(input.nextLine());

            System.out.println("Enter number of holes:");
            Game.setHoles(input.nextInt());

            System.out.println("Enter number of seeds:");
            Game.setSeeds(input.nextInt());

            firstTurn2();

        }
        else if (selection.equals("4")){
            mainMenu();
        }
        else {
            difficultySelection();
        }
    }

    //Coinflip feature
    public void firstTurn(){
        int choose = coinFlip.nextInt(2);

        if (choose == 1){
            System.out.println("Player 1 pick [1] Heads or [2] Tails:");
            
            int pick = input.nextInt();
        
            int result  = coinFlip.nextInt(2);

            if (result == pick){
                System.out.println("Player 1 first turn!");
                player1Turn();

            } else {
                System.out.println("Player 2 first turn!");
                player2Turn();
        }

        } else {
            System.out.println("Player 2 pick [1] Heads or [2] Tails:");

            int pick = input.nextInt();
        
            int result  = coinFlip.nextInt(2);

            if (result == pick){
                System.out.println("Player 2 first turn!");
                player2Turn();

            } else {
                System.out.println("Player 1 first turn!");
                player1Turn();
        }


        }
        
        

    }

    //Intermediate
    public void player1Turn(){
        Game.createTopBoard();
        System.out.println("");
        Game.createBottomBoard();
        System.out.println("");
        System.out.println("Player 1, " + getPlayer1name() + "'s turn! Please choose a hole from top side:");



        int turn = input.nextInt();

        //checks whether the input is appropriate
        while(turn >= Game.topRow.size() || Game.topRow.get(turn) == 0 || turn == 0 ){
            System.out.println("Invalid! Enter again!");
            turn = input.nextInt();
        } 

        Game.moveTopBoard(Game.topRow.size()- turn - 1);
        
        nextPlayer2();
    }

    public void player2Turn(){

        Game.createTopBoard();
        System.out.println("");
        Game.createBottomBoard();
        System.out.println("");
        System.out.println("Player 2, " + getPlayer2name() + "'s turn! Please choose a hole from bottom side:");
        
        int turn = input.nextInt();

        while(turn >= Game.bottomRow.size() || Game.bottomRow.get(turn-1) == 0 || turn == 0 ){
            System.out.println("Invalid! Enter again!");
            turn = input.nextInt();
        } 

        Game.moveBottomBoard(turn-1);
        
        nextPlayer1();
    }

    public void nextPlayer1(){
        System.out.println("");
        System.out.println("Player 1, " + getPlayer1name() + "'s turn! Please choose a hole from top side:");

        int turn = input.nextInt();

        while(turn >= Game.topRow.size() || Game.topRow.get(turn) == 0 || turn == 0){
            System.out.println("Invalid! Enter again!");
            turn = input.nextInt();
        } 

        Game.moveTopBoard(Game.topRow.size()- turn - 1);

        //checks whether are there any available legal moves
        int check = 0;
        for(int i = 1; i<Game.topRow.size() ; i++){
            if (Game.topRow.get(i) == 0) {
                check++;
            }

            if (check == Game.topRow.size()-1){
                for (int x = 0 ; x < Game.bottomRow.size()-1; x++){
                    Game.bottomRow.set(Game.bottomRow.size()-1, Game.bottomRow.get(Game.bottomRow.size()-1) + Game.bottomRow.get(x));
                    Game.bottomRow.set(x ,0);
                }

                System.out.println("");
                System.out.println("Out of moves!");
                System.out.println("");
                Game.updatedBoard();
                winnerCheck();
            }
        }
        
        nextPlayer2();
    }

    public void nextPlayer2(){
        System.out.println("");
        System.out.println("Player 2 " + getPlayer2name() + "'s turn! Please choose a hole from bottom side:");

        int turn = input.nextInt();

        while(turn >= Game.bottomRow.size() || Game.bottomRow.get(turn-1) == 0 || turn == 0){
            System.out.println("Invalid! Enter again!");
            turn = input.nextInt();
        } 

        Game.moveBottomBoard(turn-1);

        int check = 0;
        for(int i = 0; i< Game.bottomRow.size()-1 ; i++){
            if (Game.bottomRow.get(i) == 0) {
                check++;
            }

            if (check == Game.bottomRow.size()-1){
                for (int x = 1 ; x < Game.topRow.size(); x++){
                    Game.topRow.set(0, Game.topRow.get(0) + Game.topRow.get(x));
                    Game.topRow.set(x , 0);
                }

                System.out.println("");
                System.out.println("Out of moves!");
                System.out.println("");
                Game.updatedBoard();
                winnerCheck();
            }
        }

        nextPlayer1();
    }

    
    //Advanced mode
    public void firstTurn2(){
        int choose = coinFlip.nextInt(2);

        if (choose == 1){
            System.out.println("Player 1 pick [1] Heads or [2] Tails:");
            
            int pick = input.nextInt();
        
            int result  = coinFlip.nextInt(2);

            if (result == pick){
                System.out.println("Player 1 first turn!");
                player1Turn2();

            } else {
                System.out.println("Player 2 first turn!");
                player2Turn2();
        }

        } else {
            System.out.println("Player 2 pick [1] Heads or [2] Tails:");

            int pick = input.nextInt();
        
            int result  = coinFlip.nextInt(2);

            if (result == pick){
                System.out.println("Player 2 first turn!");
                player2Turn2();

            } else {
                System.out.println("Player 1 first turn!");
                player1Turn2();
        }


        }
        
        

    }
    
    public void player1Turn2(){
        Game.createTopBoard();
        System.out.println("");
        Game.createBottomBoard();
        System.out.println("");
        
        Best.findBestTopMove(Game.topRow, Game.bottomRow);
        System.out.println("Best move: " +  Best.getBestMove());   
       
        
        System.out.println("Player 1, " + getPlayer1name() + "'s turn! Please choose a hole from top side:");


        int turn = input.nextInt();

        //checks whether the input is appropriate
        while(turn >= Game.topRow.size() || Game.topRow.get(turn) == 0 || turn == 0  ){
            System.out.println("Invalid! Enter again!");
            turn = input.nextInt();
        } 

        Game.moveTopBoard2(Game.topRow.size()- turn - 1);
        
        nextPlayer2_2();
    }

    public void player2Turn2(){

        Game.createTopBoard();
        System.out.println("");
        Game.createBottomBoard();
        System.out.println("");
        
        Best.findBestBotMove(Game.topRow, Game.bottomRow);
        System.out.println("Best move: " +  Best.getBestMove());

        System.out.println("Player 2, " + getPlayer2name() + "'s turn! Please choose a hole from bottom side:");
        
        int turn = input.nextInt();

        while(turn >= Game.bottomRow.size() || Game.bottomRow.get(turn-1) == 0 || turn == 0 ){
            System.out.println("Invalid! Enter again!");
            turn = input.nextInt();
        } 

        Game.moveBottomBoard2(turn-1);
        
        nextPlayer1_2();
    }

    public void nextPlayer1_2(){
        
        System.out.println("");
        Best.findBestTopMove(Game.topRow, Game.bottomRow);
        System.out.println("Best move: " +  Best.getBestMove());

        System.out.println("Player 1, " + getPlayer1name() + "'s turn! Please choose a hole from top side:");

        int turn = input.nextInt();

        while(turn >= Game.topRow.size() || Game.topRow.get(turn) == 0 || turn == 0  ){
            System.out.println("Invalid! Enter again!");
            turn = input.nextInt();
        } 

        Game.moveTopBoard2(Game.topRow.size()- turn - 1);

        //checks whether are there any available legal moves
        int check = 0;
        for(int i = 1; i<Game.topRow.size() ; i++){
            if (Game.topRow.get(i) == 0) {
                check++;
            }

            if (check == Game.topRow.size()-1){
                for (int x = 0 ; x < Game.bottomRow.size()-1; x++){
                    Game.bottomRow.set(Game.bottomRow.size()-1, Game.bottomRow.get(Game.bottomRow.size()-1) + Game.bottomRow.get(x));
                    Game.bottomRow.set(x ,0);
                }

                System.out.println("");
                System.out.println("Out of moves!");
                System.out.println("");
                Game.updatedBoard();
                winnerCheck();
            }
        }
        
        nextPlayer2_2();
    }

    public void nextPlayer2_2(){
        
        System.out.println("");
        Best.findBestBotMove(Game.topRow, Game.bottomRow);
        System.out.println("Best move: " +  Best.getBestMove());
        
        System.out.println("Player 2 " + getPlayer2name() + "'s turn! Please choose a hole from bottom side:");

        int turn = input.nextInt();

        while(turn >= Game.bottomRow.size() || Game.bottomRow.get(turn-1) == 0 || turn == 0){
            System.out.println("Invalid! Enter again!");
            turn = input.nextInt();
        } 

        Game.moveBottomBoard2(turn-1);

        int check = 0;

        for(int i = 0; i< Game.bottomRow.size()-1 ; i++){
            if (Game.bottomRow.get(i) == 0) {
                check++;
            }

            if (check == Game.bottomRow.size()-1){
                for (int x = 1 ; x < Game.topRow.size(); x++){
                    Game.topRow.set(0, Game.topRow.get(0) + Game.topRow.get(x));
                    Game.topRow.set(x , 0);
                }

                System.out.println("");
                System.out.println("Out of moves!");
                System.out.println("");
                Game.updatedBoard();
                winnerCheck();
            }
        }

        nextPlayer1_2();
    }
    
    
    
    public void winnerCheck(){
        
        
        if (Game.getTopScoreHole() > Game.getBotScoreHole()){
            System.out.println("");
            System.out.println("Player 1 is the winner!");
            Leaderboard.storeIntoLeaderboard(getPlayer1name(), Game.getTopScoreHole());
            
            Game.clearGameBoards();
            System.out.println("");
            mainMenu();
            
        }

        else if(Game.getTopScoreHole() < Game.getBotScoreHole()){
            System.out.println("");
            System.out.println("Player 2 is the winner!");
            Leaderboard.storeIntoLeaderboard(getPlayer2name(), Game.getBotScoreHole());
            
            Game.clearGameBoards();
            System.out.println("");
            mainMenu();
            
        }

        else if (Game.getTopScoreHole() == Game.getBotScoreHole()){
            System.out.println("");
            System.out.println("It's a tie!");
            Leaderboard.storeIntoLeaderboard(getPlayer1name(), Game.getTopScoreHole());
            Leaderboard.storeIntoLeaderboard(getPlayer2name(), Game.getBotScoreHole());
            
            Game.clearGameBoards();
            System.out.println("");
            mainMenu();
            
        }
    }



}
