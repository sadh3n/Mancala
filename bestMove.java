import java.util.ArrayList;
import java.util.Collections;

import javax.swing.plaf.synth.SynthSplitPaneUI;

public class bestMove extends testGame {

    private int bestMove;
    private int position;
    private int moveCounter;
    private int remainingMoves;
    private int amount;
   
    ArrayList<Integer> topRow = new ArrayList<Integer>();
    ArrayList<Integer> bottomRow = new ArrayList<Integer>();
    ArrayList<Integer> temp = new ArrayList<Integer>();
    ArrayList<Integer> fakeBot = new ArrayList<Integer>();
    ArrayList<Integer> fakeTop = new ArrayList<Integer>();


    ArrayList<Integer> bestTop = new ArrayList<Integer>();
    ArrayList<Integer> bestBot = new ArrayList<Integer>();

    ArrayList<Integer> constTop = new ArrayList<Integer>();
    ArrayList<Integer> constBot = new ArrayList<Integer>();

    public void findBestTopMove(ArrayList<Integer> top, ArrayList<Integer> bottom){

        constTop = top;
        constBot = bottom;
        
        for (int i = 1; i < top.size(); i++){
            
            if (topRow.isEmpty() && bottomRow.isEmpty()){


                for (int t = 0; t < constTop.size() ; t++){
                    topRow.add(0);
                    bottomRow.add(0);
                }
    
            }
            
            Collections.copy(topRow, constTop);
            Collections.copy(bottomRow, constBot);

            
            moveTopBoard(topRow.size() - i - 1);

            bestTop.add(topRow.get(0));
        }   
    
        checkBestMove(bestTop);
        
    }
    
    public void findBestBotMove(ArrayList<Integer> top, ArrayList<Integer> bottom){
        
        constTop = top;
        constBot = bottom;
        

        for (int i = 1; i < bottom.size(); i++){

            
            if (topRow.isEmpty() && bottomRow.isEmpty()){


                for (int t = 0; t < constTop.size() ; t++){
                    topRow.add(0);
                    bottomRow.add(0);
                }
    
            }
            
            Collections.copy(topRow, constTop);
            Collections.copy(bottomRow, constBot);
            
            moveBottomBoard(i - 1);

            bestBot.add(bottomRow.get(bottomRow.size()-1));
        }

        
        checkBestMove(bestBot);
    

    }
    
    
    public void moveTopBoard(int x){
        
        position = x;
        moveCounter = 0 ;
        
        
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

        
        if (remainingMoves >= 1){
            remainingTop(remainingMoves);
        } 

        else if(remainingMoves == 0){

            
            if(position == topRow.size()-1){ 
                
                updateAfterP1();
                clearTempBoards();
                return;

            }  
            
            else if (temp.get(position) > 1){
               moveTopBoard(position);
            }


            else{
                updateAfterP1();

                if (position < topRow.size()-1 && topRow.get(topRow.size()-1-position) == 1 && bottomRow.get(topRow.size()-1-position-1) >=1){
                    topRow.set(0, topRow.get(0) + 1 + bottomRow.get(topRow.size()-1-position-1));
                
                    topRow.set(topRow.size()-1-position, 0);
                    bottomRow.set(topRow.size()-1-position-1 , 0);

                }
                
                clearTempBoards();
                return;
                
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
                clearTempBoards();
                return;

            }
                

            else if (temp.get(position-1) > 1){
               moveTopBoard(position-1);
            }


            else{
                updateAfterP1();
                    
                if (position - 1 < topRow.size()-1 && topRow.get((topRow.size()-1) - (position-1)) == 1 && bottomRow.get((topRow.size()-1)- (position-1) - 1) >=1){
        
                    topRow.set(0, topRow.get(0) + 1 + bottomRow.get((topRow.size()-1)- (position-1) - 1));

                    topRow.set((topRow.size()-1) - (position-1), 0);
                    bottomRow.set((topRow.size()-1)- (position-1) - 1, 0);

                }

                clearTempBoards();
                return;
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
                clearTempBoards();
                return;

                
            }


            else if (temp.get(position) > 1){
               moveBottomBoard(position);
            }

            else{
                updateAfterP2();

                if (position < bottomRow.size()-1 && bottomRow.get(position) == 1 && topRow.get(position+1) >=1){
                    clearTempBoards();
                    bottomRow.set(bottomRow.size()-1, bottomRow.get(bottomRow.size()-1) + 1 + topRow.get(position+1) );
                    
                    bottomRow.set(position, 0);
                    topRow.set(position+1, 0);

                }

                clearTempBoards();
                return;
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
                clearTempBoards();
                return;
                
                
            }


            else if (temp.get(position-1) > 1){
               moveBottomBoard(position-1);
            }

            else{
                updateAfterP2();

                if (position - 1 < bottomRow.size()-1 && bottomRow.get(position-1) == 1 && topRow.get(position+1-1) >=1){
                    clearTempBoards();
                    
                    bottomRow.set(bottomRow.size()-1, bottomRow.get(bottomRow.size()-1) + 1 + topRow.get(position+1-1) );
                    bottomRow.set(position-1, 0);
                    topRow.set(position+1-1, 0);

                }

                clearTempBoards();
                return;
                
            }

        }
    }

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


   public void clearTempBoards(){
    temp.clear();
    fakeBot.clear();
    fakeTop.clear();

}

    
    public void checkBestMove(ArrayList<Integer> check){

        int max = check.get(0);

            for(int i = 0; i<check.size(); i++){
                if(check.get(i) > max){
                    max = check.get(i);
            }
        }
            for(int i = 0; i<check.size(); i++){
                
                if(check.get(i) == max){    
                    setBestMove(i+1);
                    break;
            }
        }

        bestTop.clear();    
        bestBot.clear();

    }

    
    public void setBestMove(int x) {
        bestMove = x;
    }


    public int getBestMove() {
        return bestMove;
    }
    
}
