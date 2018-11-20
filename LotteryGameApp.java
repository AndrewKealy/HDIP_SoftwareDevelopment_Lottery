/* A class that lets a user enter numbers in  a lottery game
@author Andrew Kealy
18/11/2018
*/
import javax.swing.JOptionPane;


public class LotteryGameApp{
  public static void main(String args[]){
    //declare variables
    final int MATCH3 = 100;
    final int MATCH4 = 250;
    final int MATCH5 = 1000;
    final int NUMBERS_PER_LINE=6;
    final int MAX_LINES=3;
    final int TOTAL_NUMBERS=40;

    // An array to hold a history of the players games. It's set at a fixed size of 100 games.
    int[][] history=new int[100][];

    //user input is taken first as a String to allow validation
    String numberOfLinesString=new String();
    int numberOfLines=0;

    String playersChoice=new String();
    //A 2d array to hold the players numbers for an individual game
    int[][] playerLines ;

    //An array to hold the amount of numbers matched for each line in a single game
    int[] linesPlayedAndWon;

    //keeps track of cash won
    int totalWinnings=0;

    //keeps track of number of jackpots (of unspecified cash value) won
    int jackpotsWon=0;

    //A String to tell the user how many jackpots they won
    String jackpotsString=new String();


    int numberOfGamesPlayed=0;
    boolean isPlayingAgain=true;
    String playAgain=new String();

    //A StringBuffer to help concatanate the results of a game to a single String
    StringBuffer gameResultsStringBuffer=new StringBuffer();
    String gameResultsString=new String();

    //A StringBuffer to help concatanate the results of all the games to a single String
    StringBuffer historyResultsStringBuffer=new StringBuffer();
    String historyResultsString=new String();

    //create objects;
    LotteryGame myLotteryGame=new LotteryGame();
    do{
    /*input - ask the user how many lines they would like to play and then take their lottery numbers
    There is validation to check that they enter an int and that it is within a permitted range.
    */
    numberOfLines=-1;
    while(numberOfLines==-1){
      numberOfLinesString=JOptionPane.showInputDialog(null, "How many lines would you like to play? 1, 2 or 3:");
      numberOfLines=stringToInt(numberOfLinesString);
      if(numberOfLines!=-1){
        if (numberOfLines<1 || numberOfLines>MAX_LINES){
          JOptionPane.showMessageDialog(null, "Please enter a valid number of lines. 1, 2 or 3");
          numberOfLines=-1;
        }
      }
    }

    //A 2d array is initialised with the number of rows set to the number of lines the players wants to play. The number of columns is set to how many numbers are in each line.
    playerLines=new int[numberOfLines][NUMBERS_PER_LINE];

    //Take the input, checking that it is   within the valid range
    playersChoice="";
    for (int i=0;i<numberOfLines;i++){
      for (int j=0;j<NUMBERS_PER_LINE;j++){
        playerLines[i][j]=-1;
        while(playerLines[i][j]==-1){
          playersChoice=JOptionPane.showInputDialog(null, "Line: "+(i+1)+", please enter your "+ordinalStringFromInt(j+1)+" number.");
          playerLines[i][j]=stringToInt(playersChoice);

            //Checks if selected numbers is already in the array
            for (int k=0;k<j;k++){
                if (playerLines[i][j]==playerLines[i][k]){
                  JOptionPane.showMessageDialog(null, "You have already selected that number for this line.");
                  playerLines[i][j]=-1;
                }
            }
            //Checks that selected numbers is within the valid range
            if(playerLines[i][j]!=-1){
              if(playerLines[i][j]<1 || playerLines[i][j]>TOTAL_NUMBERS){
                JOptionPane.showMessageDialog(null, "Please only select numbers between 1 and 40");
                playerLines[i][j]=-1;
              }
            }
        }
      }
    }
      //Send the array of the playerse selection to the instance of the LotteryGame class and compute (generate winning numbers and compare the user's selection.)
      myLotteryGame.setPlayerLines(playerLines);
      myLotteryGame.compute();

      //Get the result of the game
      linesPlayedAndWon=myLotteryGame.getLinesPlayedAndWon();

      //Output the results to the user
      for (int i=0;i<linesPlayedAndWon.length;i++){
        gameResultsStringBuffer.append("Line "+(i+1)+", you matched "+linesPlayedAndWon[i] + ". "+myLotteryGame.whatYouWon(linesPlayedAndWon[i])+"<br>");
      }
      gameResultsString=gameResultsStringBuffer.toString();
      JOptionPane.showMessageDialog(null,"<html>"+gameResultsString+"</html>");
      //Clear the StringBuffer in case the user plays again
      gameResultsStringBuffer.delete(0, gameResultsStringBuffer.length());

      playAgain=JOptionPane.showInputDialog(null, "Would you like to play again?");

    //Keep asking if they would like to play again until getting a valid answer
    while (!"no".equalsIgnoreCase(playAgain) && !"yes".equalsIgnoreCase(playAgain)){
      playAgain=JOptionPane.showInputDialog(null, "Please enter Yes or No. Would you like to play again?");
    }
    if (playAgain.equalsIgnoreCase("no")){
        isPlayingAgain=false;
    }
    //Add this games results to the history array
    history[numberOfGamesPlayed]=linesPlayedAndWon;
    numberOfGamesPlayed++;

  }while(isPlayingAgain==true);
  //After all games are played, give the user their entire history
  totalWinnings=myLotteryGame.getTotalWinnings();
  jackpotsWon=myLotteryGame.getJackpotsWon();
  jackpotsString="";
  if (jackpotsWon==1){
    jackpotsString="you won<span class=highlight> "+jackpotsWon+"</span> jackpot.";
  } else {
    jackpotsString="you won<span class=highlight> "+jackpotsWon+"</span> jackpots.";
  }
  for (int i=0; i<numberOfGamesPlayed;i++){
    for (int j=0;j<history[i].length;j++)
    historyResultsStringBuffer.append("Game "+(i+1)+ ", line "+(j+1)+": you matched "+history[i][j] + ". "+myLotteryGame.whatYouWon(history[i][j])+"<br>");
  }
  historyResultsString=historyResultsStringBuffer.toString();
  JOptionPane.showMessageDialog(null, "<html><style>.highlight{font-weight: bold;color: green;}</style>"+historyResultsString+"<br>"+"Your total euro winnings are<span class=highlight> "+totalWinnings+"</span> and "+jackpotsString+"</html>");
  }
  //A helper method that converts a string to an int of possible, or else returns an error
  private static int stringToInt(String string){
    try{
      return Integer.parseInt(string);
    }
    catch (NumberFormatException e){
      JOptionPane.showMessageDialog(null, "Not a valid number! Please enter a number.");
      return -1;
    }
  }
  //A helper method to take an int and return a String of its ordinal equivalent
  private static String ordinalStringFromInt(int number){
    String ordinal = new String();
    switch(number){
      case 1: ordinal="first";
      break;
      case 2: ordinal="second";
      break;
      case 3: ordinal="third";
      break;
      case 4: ordinal="fourth";
      break;
      case 5: ordinal="fifth";
      break;
      case 6: ordinal="sixth";
      break;
      default: ordinal="";
    }
    return ordinal;
  }
}
