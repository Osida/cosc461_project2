import java.io.*;

public class MiniMaxOpening {
    private final Integer INFINITY = Integer.MIN_VALUE;
    int evaluatedPositions = 0;
    int minimaxEstimate = 0;
    File inputBoardFile, outputBoardFile;
    int searchDepth;

    public MiniMaxOpening(File input, File output, int depth) {
        this.inputBoardFile = input;
        this.outputBoardFile = output;
        this.searchDepth = depth;
    }

    public int setSearchDepthByOne(boolean isIncrement) {
        if (isIncrement) this.searchDepth++;
        return this.searchDepth--;
    }

    public int setEvaluatedPositionsByOne(boolean isIncrement) {
        if (isIncrement) this.evaluatedPositions++;
        return this.evaluatedPositions--;
    }

    public void play() {
        try (FileReader inputFile = new FileReader(this.inputBoardFile);
             PrintWriter outputFile = new PrintWriter(new FileWriter(this.outputBoardFile));
             BufferedReader reader = new BufferedReader(inputFile)
        ) {
            reader.lines().forEach(strCurrentLine -> {
                char[] inputBoard = strCurrentLine.toCharArray();
                System.out.printf("search depth: %d \ninput board: %s", searchDepth, String.valueOf(inputBoard));

//                minimax(inputBoard, true);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private void minimax(char[] inputBoard, boolean isMaximizingPlayer) {
//        if (this.searchDepth == 0) setEvaluatedPositionsByOne(true);
//        else if (this.searchDepth > 0 && isMaximizingPlayer) {
//            ArrayList<char[]> childBoards = new ArrayList<>();
//            char[] maximizingBoard = null;
//            char[] miniimizingBoard = null;
//
//            System.out.printf("Current search depth: %d", this.searchDepth);
//            this.searchDepth -=;
//
//            childBoards = generateAdd(inputBoard);
//        }
//    }
//
//    private ArrayList<char[]> generateAdd(char[] inputBoard) {
//        char[] copyOfBoard;
//        ArrayList<char[]> listLOfBoardPositions;
//
//        int index = 0;
//        for (char piece : inputBoard) {
//            if (piece == 'x') {
//                copyOfBoard = inputBoard.clone();
//                copyOfBoard[index] = 'W';
//
//                if (closeMill(index, copyOfBoard))
//                    listLOfBoardPositions = generateRemove(copyOfBoard, listLOfBoardPositions);
//                else listLOfBoardPositions.add(copyOfBoard);
//            }
//            index++;
//        }
//        return listLOfBoardPositions;
//    }
//
//    private boolean closeMill(int index, char[] copyOfBoard) {
//        char c = copyOfBoard[index];
//
//        switch (index) {
//            case index == 0: // a0
//                if (copyOfBoard[1] == c && copyOfBoard[2] == c)
//        }
//
//        return false;
//    }
//
//    private ArrayList<char[]> generateRemove(char[] copyOfBoard, ArrayList<char[]> listLOfBoardPositions) {
//        return null;
//    }
//
//    private char[] swapPlayers(char[] board) {
//        char[] tempBoard = board.clone();
//
//        int index = 0;
//        for (char piece : tempBoard) {
//            if (piece == 'W') tempBoard[index] = 'B';
//            else if (piece == 'B') tempBoard[index] = 'W';
//            index++;
//        }
//
//        return tempBoard;
//    }
}
