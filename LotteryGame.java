/* An instaniable class that plays  a lottery game
@author Andrew Kealy
18/11/2018
*/
import java.util.Random;
public class LotteryGame{
  //declare data members
  private static final int MATCH3 = 100;
  private static final int MATCH4 = 250;
  private static final int MATCH5 = 1000;
  private static final int NUMBERS_PER_LINE=6;
  private static final int TOTAL_NUMBERS=40;


  //An array in which to hold  randomly generated winning  numbers
  private int[] winningNumbers;

  //A 2d array to hold the player's numbers
  private int[][] playerLines ;

  //An array to keep track of how many lines were winning lines and what they won
  private int[] linesPlayedAndWon;

  private int totalWinnings;
  private int jackpotsWon;

  public LotteryGame(){
    winningNumbers=new int[NUMBERS_PER_LINE];
    totalWinnings=0;
    jackpotsWon=0;
  }
  //An instance of the Random class used to generate random numbers
  Random myRandom=new Random();

  //setters
  public void setPlayerLines(int[][] playerLines){
    this.playerLines=playerLines;
  }


  //calculate which lines have won and what the winnings are
  public void compute(){
      //generate random numbers;
      winningNumbers=generateRandomWinningNumbers(NUMBERS_PER_LINE, TOTAL_NUMBERS);
      //The array 'lines played and won' is created to the length of the number of lines played. It will keep track of winnings
      linesPlayedAndWon=new int[playerLines.length];

      //This nested for loop iterates over the array of player numbers and compares them with the random numbers
      for (int i=0;i<playerLines.length;i++){
        int numberOfMatches=0;
        for (int j=0;j<NUMBERS_PER_LINE;j++){
          for (int k=0;k<NUMBERS_PER_LINE;k++){
            if (playerLines[i][j]==winningNumbers[k]){
              numberOfMatches++;
            }
          }
        }
          switch (numberOfMatches){
            case 0: linesPlayedAndWon[i]=0;
            break;
            case 1: linesPlayedAndWon[i]=1;
            break;
            case 2: linesPlayedAndWon[i]=2;
            break;
            case 3: linesPlayedAndWon[i]=3;
                    totalWinnings+=MATCH3;
            break;
            case 4: linesPlayedAndWon[i]=4;
                    totalWinnings+=MATCH4;
            break;
            case 5: linesPlayedAndWon[i]=5;
                    totalWinnings+=MATCH5;
            break;
            case 6: linesPlayedAndWon[i]=6;
                    jackpotsWon++;
            break;
            default: linesPlayedAndWon[i]=-1;
            break;
          }
      }
    }

    //A helper method that takes the number of random numbers needed, and the upper limit of the selection, and returns that amount of random numbers from 1 to the upper limit
    public int[] generateRandomWinningNumbers(int howManyRandomNumbers, int fromHowBigASelection){
      int[] numbersToChooseFrom=new int[fromHowBigASelection];
      for (int i=0;i<fromHowBigASelection;i++) {
        numbersToChooseFrom[i]=i+1;
      }

      int winningNumbers[]=new int[howManyRandomNumbers];
      int randomIndex;
      int arrayLengthToSearch=numbersToChooseFrom.length;

      for (int i=0;i<howManyRandomNumbers;i++){
        randomIndex=myRandom.nextInt(arrayLengthToSearch);
        winningNumbers[i]=numbersToChooseFrom[randomIndex];
        //A method that removes the selected number from the array of available numbers.
        System.arraycopy(numbersToChooseFrom,randomIndex+1, numbersToChooseFrom, randomIndex, numbersToChooseFrom.length - 1 - randomIndex);
        //The array is still the same length, even though the chosen number has been removed. So the area to search in the array is reduced to account for this.
        arrayLengthToSearch--;
      }
      int[] fixedNumbers={1,2,3,4,5,6};
      return fixedNumbers;

//      return winningNumbers;
    }

    //helper method that takes a number of matches in a line and returns a String to present to the user.
    public String whatYouWon(int matchedNumbers){
      String whatYouWon;
      switch (matchedNumbers){
        case 0:
        case 1:
        case 2: whatYouWon="This is not a winning line.";
        break;
        case 3: whatYouWon="You win "+MATCH3+" euros.";
        break;
        case 4: whatYouWon="You win "+MATCH4+" euros.";
        break;
        case 5: whatYouWon="You win "+MATCH5+" euros.";
        break;
        case 6: whatYouWon="Congratulations, you won the jackpot!!!";
        break;
        default: whatYouWon="";
        break;
      }
      return whatYouWon;
    }

    //getters
    public int[] getLinesPlayedAndWon(){
      return linesPlayedAndWon;
    }
    public int getTotalWinnings(){
      return totalWinnings;
    }
    public int getJackpotsWon(){
      return jackpotsWon;
    }
  }
