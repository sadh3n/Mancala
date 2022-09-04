import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class game {
    private int holes;
    private int seeds;
    private int position;
    private int moveCounter;
    private int remainingMoves;
    private int amount;
    private bestMove Best;
    private Scanner input = new Scanner(System.in);
    ArrayList<Integer> topRow = new ArrayList<Integer>();
    ArrayList<Integer> bottomRow = new ArrayList<Integer>();
    ArrayList<Integer> temp = new ArrayList<Integer>();
    ArrayList<Integer> fakeBot = new ArrayList<Integer>();
    ArrayList<Integer> fakeTop = new ArrayList<Integer>();
    
    public game(){
        Best = new bestMove();
    }


    public void setHoles(int x){
        holes = x;
    }
    
    public void setSeeds(int y) {
        seeds = y;
    }

    public int getHoles(){
        return holes;
    }

    public int getSeeds(){
        return seeds;
    }

    public void createTopBoard(){
        holes = getHoles();
        seeds = getSeeds();
        
        topRow.add(0);

        
        for (int i = 1; i < holes + 1 ; i++){
            topRow.add(seeds);
        }

        
        System.out.print(topRow.get(0) + " ");

        for (int i = 1; i < topRow.size(); i++){
            System.out.print("|" + topRow.get(i) + "|");
        
    }
    }
    
    public void createBottomBoard(){

        holes = getHoles();
        seeds = getSeeds();
        
        
        
        for (int i = 0; i < holes; i++){
            bottomRow.add(seeds);
        }

        bottomRow.add(0);

        for (int i = 0; i < bottomRow.size()-1; i++) {
			System.out.print("|" + bottomRow.get(i) + "|");
		}
        System.out.print(" " + bottomRow.get(bottomRow.size()-1));
    }


    //Basic and intermediate mode
    public void moveTopBoard(int x){
        
        position = x;
        moveCounter = 0 ;
        
        //creates a temporary arraylist
        if (temp.isEmpty()){
        for (int i = 0; i < bottomRow.size();i++){
                fakeBot.add(0);
        }

        Collections.copy(fakeBot,bottomRow);
        fakeBot.remove(fakeBot.size()-1);
            
        for (int i = 0; i <topRow.size();i++){
                temp.add(0);
        }
            
        Collections.copy(temp,topRow);
        Collections.reverse(temp);
        temp.addAll(fakeBot);
        }

        amount = temp.get(position);

        temp.set(position, 0);

        for (int i = 0; i < amount ; i++){
            
            if (position == temp.size()-1){
                break;
            }

            temp.set(position+1, temp.get(position+1)+1); 
            moveCounter++;
            position++;
        }
        
       
        remainingMoves = amount - moveCounter;

        // checks whether there are any more moves to be made
        if (remainingMoves >= 1){
            remainingTop(remainingMoves);
        } 

        else if(remainingMoves == 0){

            //extra turn for landing in the store
            if(position == topRow.size()-1){ 
                updateAfterP1();
                updatedBoard();

                //checks whether there are any legal moves available
                int check= 1;

                for(int i = 1; i<topRow.size() ; i++){
                    if (topRow.get(i) == 0) {
                        check++;
                    }

                    if (check == topRow.size()-1){
                        return;
                    }       

                }
                
                System.out.println("");
                System.out.println("You get another turn Player 1! Pick another hole:");
                
                int turn = input.nextInt();
                
                while(turn >= topRow.size() || topRow.get(turn) == 0 || turn == 0 ){
                    System.out.println("Invalid! Enter again!");
                    turn = input.nextInt();
                } 

                clearTempBoards();
                moveTopBoard(topRow.size() - turn - 1);
                
            }


            //if the last position has more than 1 seeds, the loop is continued 
            else if (temp.get(position) > 1){
               moveTopBoard(position);
            }


            else{
                //set both top board and bottom board with the newest elements from the temporary arraylist
                updateAfterP1();
                

                // checks whether the opposite of the board has 1 or more seeds that the current player can 'eat' when landing their last seed over their side
                if (position < topRow.size()-1 && topRow.get(topRow.size()-1-position) == 1 && bottomRow.get(topRow.size()-1-position-1) >=1){
                    clearTempBoards();
                    updatedBoard();
                    topRow.set(0, topRow.get(0) + 1 + bottomRow.get(topRow.size()-1-position-1));
                    System.out.println("");
                    System.out.println("Player 1 has eaten a hole at " + (topRow.size()-1-position) + " for a total of " + (bottomRow.get(topRow.size()-1-position-1)+1) + "!");

                    topRow.set(topRow.size()-1-position, 0);
                    bottomRow.set(topRow.size()-1-position-1 , 0);

                }
                
                clearTempBoards();
                updatedBoard();
                 
                
            }
        }

    }

    public void remainingTop(int x){
        amount = x;
        position = 0;
        moveCounter = 0;

        for (int i = 0 ; i < amount; i++ ){
            
            if (i == temp.size()-1){
                break;
            }

            temp.set(i, temp.get(i)+1);
            moveCounter++;
            position++;
        }

        remainingMoves = amount - moveCounter;
        
        if (remainingMoves >= 1){
            remainingTop(remainingMoves);
        }

        else if(remainingMoves == 0){

            if(position - 1 == topRow.size()-1){
                updateAfterP1();
                updatedBoard();

                int check = 1;

                for(int i = 1; i < topRow.size() ; i++){
                    if (topRow.get(i) == 0) {
                        check++;
                    }

                    if (check == topRow.size()-1){
                        return;
                    }       

                }

                System.out.println("");
                System.out.println("You get another turn Player 1! Pick another hole:");
                
                int turn = input.nextInt();
                
                while(turn >= topRow.size() || topRow.get(turn) == 0 || turn == 0){
                    System.out.println("Invalid! Enter again!");
                    turn = input.nextInt();
                } 

                clearTempBoards();
                moveTopBoard(topRow.size() - turn - 1);
                
            }


            else if (temp.get(position-1) > 1){
               moveTopBoard(position-1);
            }


            else{
                updateAfterP1();

                if (position - 1 < topRow.size()-1 && topRow.get((topRow.size()-1) - (position-1)) == 1 && bottomRow.get((topRow.size()-1)- (position-1) - 1) >=1){
                    clearTempBoards();
                    updatedBoard();
                    topRow.set(0, topRow.get(0) + 1 + bottomRow.get((topRow.size()-1)- (position-1) - 1));
                    System.out.println("");
                    System.out.println("Player 1 has eaten a hole at " + ((topRow.size()-1) - (position-1))+ " for a total of " + (bottomRow.get((topRow.size()-1)- (position-1) - 1)+1) + "!");

                    topRow.set((topRow.size()-1) - (position-1), 0);
                    bottomRow.set((topRow.size()-1)- (position-1) - 1, 0);

                }

                clearTempBoards();
                updatedBoard();
            }

        }

        
    }

    public void moveBottomBoard(int x){
        position = x;
        moveCounter = 0;
        
        if (temp.isEmpty()){

            for (int i = 0; i <topRow.size();i++){
                fakeTop.add(0);
            }

            Collections.copy(fakeTop,topRow);
            fakeTop.remove(0);
            Collections.reverse(fakeTop);
            
            for (int i = 0; i <bottomRow.size();i++){
                temp.add(0);
            }
            
            Collections.copy(temp, bottomRow);
            temp.addAll(fakeTop);
        }

        
        amount = temp.get(position);

        temp.set(position, 0);

        for (int i = 0; i < amount ; i++){
            
            if (position == temp.size()-1){
                break;
            }

            temp.set(position+1, temp.get(position+1)+1); 
            moveCounter++;
            position++;
        }
        
        
        remainingMoves = amount - moveCounter;
        

        
        if (remainingMoves >= 1){
            remainingBottom(remainingMoves);
        } 

        else if(remainingMoves == 0){

            
            if(position == bottomRow.size()-1){
                updateAfterP2();
                updatedBoard();
                
                int check = 0;
                
                for(int i = 0; i < bottomRow.size()-1 ; i++){
                    if (bottomRow.get(i) == 0) {
                        check++;
                
                    }

                    if (check == bottomRow.size()-1){
                        return;
                    }       

                }
                
                System.out.println("");
                System.out.println("You get another turn Player 2! Pick another hole:");
                
                int turn = input.nextInt();
                
                while(turn-1 >= bottomRow.size() || bottomRow.get(turn-1) == 0 || turn == 0 ){
                    System.out.println("Invalid! Enter again!");
                    turn = input.nextInt();
                } 

                clearTempBoards();
                moveBottomBoard(turn - 1);
                
            }


            else if (temp.get(position) > 1){
               moveBottomBoard(position);
            }

            else{
                updateAfterP2();

                
                if (position < bottomRow.size()-1 && bottomRow.get(position) == 1 && topRow.get(position+1) >=1){
                    clearTempBoards();
                    updatedBoard();
                    bottomRow.set(bottomRow.size()-1, bottomRow.get(bottomRow.size()-1) + 1 + topRow.get(position+1) );
                    System.out.println("");
                    System.out.println("Player 2 has eaten a hole at " + (position+1) + " for a total of " + (topRow.get(position+1)) + "!");

                    bottomRow.set(position, 0);
                    topRow.set(position+1, 0);

                }

                clearTempBoards();
                updatedBoard();
            }
        }





    }

    public void remainingBottom(int x){
        amount = x;
        position = 0;
        moveCounter = 0;

        for (int i = 0 ; i < amount; i++ ){
            
            if (i == temp.size()-1){
                break;
            }

            temp.set(i, temp.get(i)+1);
            moveCounter++;
            position++;
        }

        remainingMoves = amount - moveCounter;
       

        if (remainingMoves >= 1){
            remainingBottom(remainingMoves);
        }

        else if(remainingMoves == 0){

            if(position - 1 == bottomRow.size()-1){
                updateAfterP2();
                updatedBoard();

                int check = 0;
                
                for(int i = 0; i < bottomRow.size()-1 ; i++){
                    if (bottomRow.get(i) == 0) {
                    check++;
                
                    }

                    if (check == bottomRow.size()-1){
                        return;
                    }       

                }

                System.out.println("");
                System.out.println("You get another turn Player 2! Pick another hole:");
                
                int turn = input.nextInt();
                
                while(turn-1 >= bottomRow.size() || bottomRow.get(turn-1) == 0 || turn == 0){
                    System.out.println("Invalid! Enter again!");
                    turn = input.nextInt();
                } 

                clearTempBoards();
                moveBottomBoard(turn - 1);
                
            }


            else if (temp.get(position-1) > 1){
               moveBottomBoard(position-1);
            }

            else{
                updateAfterP2();

                if (position - 1 < bottomRow.size()-1 && bottomRow.get(position-1) == 1 && topRow.get(position+1-1) >=1){
                    clearTempBoards();
                    updatedBoard();
                    bottomRow.set(bottomRow.size()-1, bottomRow.get(bottomRow.size()-1) + 1 + topRow.get(position+1-1) );
                    System.out.println("");
                    System.out.println("Player 2 has eaten a hole at " + (position) + " for a total of " + (topRow.get(position+1-1)+1) + "!");

                    bottomRow.set(position-1, 0);
                    topRow.set(position+1-1, 0);

                }

                clearTempBoards();
                updatedBoard();
            }

        }
    }


    
    // Advanced mode
    public void moveTopBoard2(int x){
        
        position = x;
        moveCounter = 0 ;
        
        //creates a temporary arraylist
        if (temp.isEmpty()){
        for (int i = 0; i < bottomRow.size();i++){
                fakeBot.add(0);
        }

        Collections.copy(fakeBot,bottomRow);
        fakeBot.remove(fakeBot.size()-1);
            
        for (int i = 0; i <topRow.size();i++){
                temp.add(0);
        }
            
        Collections.copy(temp,topRow);
        Collections.reverse(temp);
        temp.addAll(fakeBot);
        }

        amount = temp.get(position);

        temp.set(position, 0);

        for (int i = 0; i < amount ; i++){
            
            if (position == temp.size()-1){
                break;
            }

            temp.set(position+1, temp.get(position+1)+1); 
            moveCounter++;
            position++;
        }
        
       
        remainingMoves = amount - moveCounter;

        // checks whether there are any more moves to be made
        if (remainingMoves >= 1){
            remainingTop2(remainingMoves);
        } 

        else if(remainingMoves == 0){

            //extra turn for landing in the store
            if(position == topRow.size()-1){ 
                updateAfterP1();
                updatedBoard();

                //checks whether there are any legal moves available
                int check= 1;

                for(int i = 1; i<topRow.size() ; i++){
                    if (topRow.get(i) == 0) {
                        check++;
                    }

                    if (check == topRow.size()-1){
                        return;
                    }       

                }
                
                System.out.println("");
                Best.findBestTopMove(topRow, bottomRow);
                System.out.println("Best move: " +  Best.getBestMove()); 
                
                System.out.println("You get another turn Player 1! Pick another hole:");
                
                int turn = input.nextInt();
                
                while(turn >= topRow.size() || topRow.get(turn) == 0 || turn == 0 ){
                    System.out.println("Invalid! Enter again!");
                    turn = input.nextInt();
                } 

                clearTempBoards();
                moveTopBoard2(topRow.size() - turn - 1);
                
            }


            //if the last position has more than 1 seeds, the loop is continued 
            else if (temp.get(position) > 1){
               moveTopBoard2(position);
            }


            else{
                //set both top board and bottom board with the newest elements from the temporary arraylist
                updateAfterP1();
                

                // checks whether the opposite of the board has 1 or more seeds that the current player can 'eat' when landing their last seed over their side
                if (position < topRow.size()-1 && topRow.get(topRow.size()-1-position) == 1 && bottomRow.get(topRow.size()-1-position-1) >=1){
                    clearTempBoards();
                    updatedBoard();
                    topRow.set(0, topRow.get(0) + 1 + bottomRow.get(topRow.size()-1-position-1));
                    System.out.println("");
                    System.out.println("Player 1 has eaten a hole at " + (topRow.size()-1-position) + " for a total of " + (bottomRow.get(topRow.size()-1-position-1)+1) + "!");

                    topRow.set(topRow.size()-1-position, 0);
                    bottomRow.set(topRow.size()-1-position-1 , 0);

                }
                
                clearTempBoards();
                updatedBoard();
                 
                
            }
        }

    }

    public void remainingTop2(int x){
        amount = x;
        position = 0;
        moveCounter = 0;

        for (int i = 0 ; i < amount; i++ ){
            
            if (i == temp.size()-1){
                break;
            }

            temp.set(i, temp.get(i)+1);
            moveCounter++;
            position++;
        }

        remainingMoves = amount - moveCounter;
        
        if (remainingMoves >= 1){
            remainingTop2(remainingMoves);
        }

        else if(remainingMoves == 0){

            if(position - 1 == topRow.size()-1){
                updateAfterP1();
                updatedBoard();

                int check = 1;

                for(int i = 1; i < topRow.size() ; i++){
                    if (topRow.get(i) == 0) {
                        check++;
                    }

                    if (check == topRow.size()-1){
                        return;
                    }       

                }

                System.out.println("");
                Best.findBestTopMove(topRow, bottomRow);
                System.out.println("Best move: " +  Best.getBestMove()); 

               
                System.out.println("You get another turn Player 1! Pick another hole:");
                
                int turn = input.nextInt();
                
                while(turn >= topRow.size() || topRow.get(turn) == 0 || turn == 0){
                    System.out.println("Invalid! Enter again!");
                    turn = input.nextInt();
                } 

                clearTempBoards();
                moveTopBoard2(topRow.size() - turn - 1);
                
            }


            else if (temp.get(position-1) > 1){
               moveTopBoard2(position-1);
            }


            else{
                updateAfterP1();

                if (position - 1 < topRow.size()-1 && topRow.get((topRow.size()-1) - (position-1)) == 1 && bottomRow.get((topRow.size()-1)- (position-1) - 1) >=1){
                    clearTempBoards();
                    updatedBoard();
                    topRow.set(0, topRow.get(0) + 1 + bottomRow.get((topRow.size()-1)- (position-1) - 1));
                    System.out.println("");
                    System.out.println("Player 1 has eaten a hole at " + ((topRow.size()-1) - (position-1))+ " for a total of " + (bottomRow.get((topRow.size()-1)- (position-1) - 1)+1) + "!");

                    topRow.set((topRow.size()-1) - (position-1), 0);
                    bottomRow.set((topRow.size()-1)- (position-1) - 1, 0);

                }

                clearTempBoards();
                updatedBoard();
            }

        }

        
    }

    public void moveBottomBoard2(int x){
        position = x;
        moveCounter = 0;
        
        if (temp.isEmpty()){

            for (int i = 0; i <topRow.size();i++){
                fakeTop.add(0);
            }

            Collections.copy(fakeTop,topRow);
            fakeTop.remove(0);
            Collections.reverse(fakeTop);
            
            for (int i = 0; i <bottomRow.size();i++){
                temp.add(0);
            }
            
            Collections.copy(temp, bottomRow);
            temp.addAll(fakeTop);
        }

        
        amount = temp.get(position);

        temp.set(position, 0);

        for (int i = 0; i < amount ; i++){
            
            if (position == temp.size()-1){
                break;
            }

            temp.set(position+1, temp.get(position+1)+1); 
            moveCounter++;
            position++;
        }
        
        
        remainingMoves = amount - moveCounter;
        

        
        if (remainingMoves >= 1){
            remainingBottom2(remainingMoves);
        } 

        else if(remainingMoves == 0){

            
            if(position == bottomRow.size()-1){
                updateAfterP2();
                updatedBoard();
                
                int check = 0;
                
                for(int i = 0; i < bottomRow.size()-1 ; i++){
                    if (bottomRow.get(i) == 0) {
                    check++;
                
                    }

                    if (check == bottomRow.size()-1){
                        return;
                    }       

                }
                
                System.out.println("");
                Best.findBestBotMove(topRow, bottomRow);
                System.out.println("Best move: " +  Best.getBestMove()); 

                System.out.println("You get another turn Player 2! Pick another hole:");
                
                int turn = input.nextInt();
                
                while(turn-1 >= bottomRow.size() || bottomRow.get(turn-1) == 0 || turn == 0 ){
                    System.out.println("Invalid! Enter again!");
                    turn = input.nextInt();
                } 

                clearTempBoards();
                moveBottomBoard2(turn - 1);
                
            }


            else if (temp.get(position) > 1){
               moveBottomBoard2(position);
            }

            else{
                updateAfterP2();

                if (position < bottomRow.size()-1 && bottomRow.get(position) == 1 && topRow.get(position+1) >=1){
                    clearTempBoards();
                    updatedBoard();
                    bottomRow.set(bottomRow.size()-1, bottomRow.get(bottomRow.size()-1) + 1 + topRow.get(position+1) );
                    System.out.println("");
                    System.out.println("Player 2 has eaten a hole at " + (position+1) + " for a total of " + (topRow.get(position+1)) + "!");

                    bottomRow.set(position, 0);
                    topRow.set(position+1, 0);

                }

                clearTempBoards();
                updatedBoard();
            }
        }





    }

    public void remainingBottom2(int x){
        amount = x;
        position = 0;
        moveCounter = 0;

        for (int i = 0 ; i < amount; i++ ){
            
            if (i == temp.size()-1){
                break;
            }

            temp.set(i, temp.get(i)+1);
            moveCounter++;
            position++;
        }

        remainingMoves = amount - moveCounter;
       

        if (remainingMoves >= 1){
            remainingBottom2(remainingMoves);
        }

        else if(remainingMoves == 0){

            if(position - 1 == bottomRow.size()-1){
                updateAfterP2();
                updatedBoard();

                int check = 0;
                
                for(int i = 0; i < bottomRow.size()-1 ; i++){
                    if (bottomRow.get(i) == 0) {
                    check++;
                
                    }

                    if (check == bottomRow.size()-1){
                        return;
                    }       

                }

                System.out.println("");
                Best.findBestBotMove(topRow, bottomRow);
                System.out.println("Best move: " +  Best.getBestMove()); 

                System.out.println("You get another turn Player 2! Pick another hole:");
                
                int turn = input.nextInt();
                
                while(turn-1 >= bottomRow.size() || bottomRow.get(turn-1) == 0 || turn == 0){
                    System.out.println("Invalid! Enter again!");
                    turn = input.nextInt();
                } 

                clearTempBoards();
                moveBottomBoard2(turn - 1);
                
            }


            else if (temp.get(position-1) > 1){
               moveBottomBoard2(position-1);
            }

            else{
                updateAfterP2();

                if (position - 1 < bottomRow.size()-1 && bottomRow.get(position-1) == 1 && topRow.get(position+1-1) >=1){
                    clearTempBoards();
                    updatedBoard();
                    bottomRow.set(bottomRow.size()-1, bottomRow.get(bottomRow.size()-1) + 1 + topRow.get(position+1-1) );
                    System.out.println("");
                    System.out.println("Player 2 has eaten a hole at " + (position) + " for a total of " + (topRow.get(position+1-1)+1) + "!");

                    bottomRow.set(position-1, 0);
                    topRow.set(position+1-1, 0);

                }

                clearTempBoards();
                updatedBoard();
            }

        }
    }
  
    

    //updating both boards based on the temporary arraylist
    public void updateAfterP1(){
       
        int count2 = temp.size() - topRow.size();

        for (int i = 0 ; i < bottomRow.size()-1  ; i++ ){
            bottomRow.set(i, temp.get(count2+1));
            count2++;
        }
    
        Collections.reverse(temp);

        int count = temp.size() - topRow.size();

        for (int i = 0 ; i < topRow.size(); i++){
            topRow.set(i, temp.get(count));
            count++;
        }



   }


   public void updateAfterP2(){
        
        for (int i = 0 ; i < bottomRow.size(); i++){
            bottomRow.set(i, temp.get(i));
        }

        Collections.reverse(temp);

        for (int i = 1 ; i < topRow.size() ; i++){
            topRow.set(i, temp.get(i-1));
        }

   }


    //displays the latest content for the boards
    public void updatedBoard(){
        
        System.out.print(topRow.get(0) + " ");

        for (int i = 1; i < topRow.size(); i++){
            System.out.print("|" + topRow.get(i) + "|");
        }

        System.out.println("");

        for (int i = 0; i < bottomRow.size()-1; i++) {
			System.out.print("|" + bottomRow.get(i) + "|");
		}

        System.out.print(" " + bottomRow.get(bottomRow.size()-1));
    }

    //clears the temporary arraylists
    public void clearTempBoards(){
        temp.clear();
        fakeBot.clear();
        fakeTop.clear();
    }

    public void clearGameBoards(){
        topRow.clear();
        bottomRow.clear();
        temp.clear();
        fakeBot.clear();
        fakeTop.clear();
    }

    public int getTopScoreHole(){
        return topRow.get(0);
    }

    public int getBotScoreHole(){
        return bottomRow.get(bottomRow.size()-1);
    }

    


}