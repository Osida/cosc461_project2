import java.io.File;

public class Main {
    static String INPUTFILE = "src/board3.txt";
    static String OUTPUTFILE = "src/board4.txt";

    public static void main(String[] args) {
//        File inputBPFile = new File(args[0]);
//        File outputBPFile = new File(args[1]);
//        Integer depth = Integer.valueOf((args[2]));
        MiniMaxGameDriver(new File(INPUTFILE), new File(OUTPUTFILE), 2);
    }

    public static void MiniMaxGameDriver(File inputBoardFile, File outputBoardFile, Integer searchDepth) {
        MiniMaxGame playMidgameEndgame = new MiniMaxGame(inputBoardFile, outputBoardFile, searchDepth);
        playMidgameEndgame.play();
    }
}