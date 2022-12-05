import java.io.*;
import java.util.ArrayList;

public class MiniMaxGame {
    private final Integer INFINITY = Integer.MIN_VALUE;
    int evaluatedPositions = 0;
    int minimaxEstimate = 0;
    File inputBoardFile, outputBoardFile;
    int searchDepth;

    public MiniMaxGame(File input, File output, int depth) {
        this.inputBoardFile = input;
        this.outputBoardFile = output;
        this.searchDepth = depth;
    }

    public void play() {
        try (FileReader inputFile = new FileReader(this.inputBoardFile);
             PrintWriter outputFile = new PrintWriter(new FileWriter(this.outputBoardFile));
             BufferedReader reader = new BufferedReader(inputFile)
        ) {
            reader.lines().forEach(strCurrentLine -> {
                char[] inputBoard = strCurrentLine.toCharArray();
                System.out.printf("search depth: %d \ninput board: %s", searchDepth, String.valueOf(inputBoard));

                char[] outBoard = minimax(inputBoard, true, outputFile);
                outputFile.printf("\nInput position: %s", new String(inputBoard));
                outputFile.printf("\nOutput position: %s", new String(outBoard));
                outputFile.printf("\nPositions evaluated by static estimation: %d", evaluatedPositions);
                outputFile.printf("\nMiniMax estimate: %d", minimaxEstimate);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private char[] minimax(char[] inputBoard, boolean isMaximizingPlayer, PrintWriter outputFile) {
        ArrayList<char[]> boardChildren;
        char[] maximizingBoardSelection, miniimizingBoardSelection;
        char[] miniimizingBoard, maximizingBoard;

        if (this.searchDepth == 0) evaluatedPositions += 1;
        else if (this.searchDepth > 0 && isMaximizingPlayer) {
            System.out.printf("At search depth: %d\n", this.searchDepth);
            this.searchDepth -= 1;

            boardChildren = generateMovesMidgameEndgame(inputBoard);
            for (char[] child : boardChildren) {
                System.out.println("Possible moves for White: " + new String(child));
            }

            int x = -INFINITY;

            int index = 0;
            for (char[] board : boardChildren) {
                miniimizingBoard = minimax(board, false, outputFile);
                if (x < staticEstimation(miniimizingBoard)) {
                    x = staticEstimation(miniimizingBoard);
                    minimaxEstimate = x;
                    maximizingBoardSelection = boardChildren.get(index);
                    outputFile.println("maximizingBoardSelection: " + new String(maximizingBoardSelection));
                }
                index++;
            }

        } else if (this.searchDepth > 0 && !isMaximizingPlayer) {
            System.out.printf("At search depth: %d\n", this.searchDepth);
            this.searchDepth -= 1;

            boardChildren = generateBlackMoves(inputBoard);
            for (char[] child : boardChildren) {
                System.out.println("Possible moves for Black: " + new String(child));
            }

            int x = INFINITY;

            int index = 0;
            for (char[] board : boardChildren) {
                maximizingBoard = minimax(board, true, outputFile);
                if (x > staticEstimation(maximizingBoard)) {
                    x = staticEstimation(maximizingBoard);
//                    minimaxEstimate = x;
                    miniimizingBoardSelection = boardChildren.get(index);
                }
                index++;
            }
        }
        return inputBoard;
    }

    private int staticEstimation(char[] board) {
        int wcount = 0;
        int bcount = 0;
        ArrayList<char[]> nbmList = new ArrayList<>();
        nbmList = generateBlackMoves(board);

        int bmovecount = nbmList.size();
        int[] listOfPiecesNums = countPieces(board);

        int whitePieces = listOfPiecesNums[0];
        int blackPieces = listOfPiecesNums[1];

        if (blackPieces <= 2) return 10000;
        else if (whitePieces <= 2) return -10000;
        else if (blackPieces == 0) return 10000;
        else return (1000 * (whitePieces - blackPieces) - bmovecount);
    }

    private ArrayList<char[]> generateBlackMoves(char[] board) {
        char[] copyOfBoard = board.clone();
        int index = 0;
        for (char piece : board) {
            if (piece == 'W') board[index] = 'B';
            else if (piece == 'B') board[index] = 'W';
            index++;
        }

        ArrayList<char[]> gbm = new ArrayList<>();
        ArrayList<char[]> gbmswap = new ArrayList<>();

        gbm = generateMovesMidgameEndgame(copyOfBoard);

        int indexJ = 0;
        for (char[] boardI : gbm) {
            char[] tempBoard = boardI;
            int innerIndex = 0;
            for (char piece : tempBoard) {
                if (piece == 'W') tempBoard[innerIndex] = 'B';
                else if (piece == 'B') tempBoard[innerIndex] = 'W';
                innerIndex++;
            }
            indexJ++;
            gbmswap.add(boardI);
        }
        return gbmswap;
    }

    private int[] countPieces(char[] board) {
        int index = 0;
        int whitePieces = 0;
        int blackPieces = 0;
        int emptySpaces = 0;

        for (char piece : board) {
            if (piece == 'W') whitePieces++;
            else if (piece == 'B') blackPieces++;
            else if (piece == 'x') emptySpaces++;
            index++;
        }

        return new int[]{whitePieces, blackPieces, emptySpaces};
    }

    private ArrayList<char[]> generateMovesMidgameEndgame(char[] inputBoard) {
        ArrayList<char[]> listOfBoardPositions = new ArrayList<>();
        int[] numberOfPieces = countPieces(inputBoard);

        if (numberOfPieces[0] == 3) {
            listOfBoardPositions = generateHopping(inputBoard);
            return listOfBoardPositions;
        } else {
            listOfBoardPositions = generateMove(inputBoard);
            return listOfBoardPositions;
        }
    }

    private ArrayList<char[]> generateHopping(char[] board) {
        char[] copyOfBoard;
        ArrayList<char[]> list = new ArrayList<>();

        int index = 0;
        for (char piece : board) {
            if (piece == 'w') {
                int innerIndex = 0;

                for (char otherPiece : board) {
                    if (board[innerIndex] == 'x') {
                        copyOfBoard = board.clone();
                        copyOfBoard[index] = 'x';
                        copyOfBoard[innerIndex] = 'W';
                        if (closeMill(innerIndex, copyOfBoard)) generateRemove(copyOfBoard, list);
                        else list.add(copyOfBoard);
                    }
                    innerIndex++;
                }
            }
            index++;
        }
        return list;
    }

    private ArrayList<char[]> generateMove(char[] board) {
        char[] copyOfBoard;
        int[] listOfNeighbors;
        ArrayList<char[]> listOfBoardPositions = new ArrayList<>();

        int index = 0;
        for (char piece : board) {
            if (piece == 'W') {
                listOfNeighbors = neighbours(index);
                int neighborCounter = 0;
                for (int neighbor : listOfNeighbors) {
                    if (board[neighbor] == 'x') {
                        copyOfBoard = board.clone();
                        copyOfBoard[index] = 'x';
                        copyOfBoard[neighbor] = 'W';
                        if (closeMill(neighbor, copyOfBoard)) {
                            generateRemove(copyOfBoard, listOfBoardPositions);
                        } else {
                            listOfBoardPositions.add(copyOfBoard);
                        }
                    }
                    neighborCounter++;
                }
            }

            index++;
        }
        return listOfBoardPositions;
    }

    private void generateRemove(char[] board, ArrayList<char[]> listOfBoardPositions) {
        ArrayList<char[]> list = new ArrayList<>();

        int index = 0;
        for (int piece : board) {
            if (piece == 'B') {
                if (!closeMill(index, board)) {
                    char[] copyOfBoard = board.clone();
                    copyOfBoard[index] = 'x';
                    list.add(copyOfBoard);
                } else {
                    char[] copyOfBoard = board.clone();
                    list.add(copyOfBoard);
                }
            }
            index++;
        }
    }

    public int[] neighbours(int boardLocation) {
        return switch (boardLocation) {
            case 0 -> new int[]{1, 3, 8};
            case 1 -> new int[]{0, 2, 4};
            case 2 -> new int[]{1, 5, 13};
            case 3 -> new int[]{0, 4, 6, 9};
            case 4 -> new int[]{1, 3, 5};
            case 5 -> new int[]{2, 4, 7, 12};
            case 6 -> new int[]{3, 7, 9};
            case 7 -> new int[]{5, 6, 11};
            case 8 -> new int[]{0, 9, 20};
            case 9 -> new int[]{3, 8, 10, 17};
            case 10 -> new int[]{6, 9, 14};
            case 11 -> new int[]{7, 12, 16};
            case 12 -> new int[]{5, 11, 13, 19};
            case 13 -> new int[]{2, 12, 22};
            case 14 -> new int[]{10, 15, 17};
            case 15 -> new int[]{4, 16, 18};
            case 16 -> new int[]{11, 15, 19};
            case 17 -> new int[]{9, 14, 18, 20};
            case 18 -> new int[]{15, 17, 19, 21};
            case 19 -> new int[]{12, 16, 18, 22};
            case 20 -> new int[]{8, 17, 21};
            case 21 -> new int[]{18, 20, 22};
            case 22 -> new int[]{13, 19, 21};
            default -> new int[]{};
        };
    }

    private boolean closeMill(int boardLocation, char[] listOfBoardPositions) {
        char[] b = listOfBoardPositions.clone();
        char c = b[boardLocation];

        if (c == 'W' || c == 'B') {
            return switch (boardLocation) {
                case 0 -> (b[1] == c && b[2] == c) || (b[3] == c && b[6] == c) || (b[8] == c && b[20] == c); // a0
                case 1 -> (b[1] == c && b[2] == c) || (b[3] == c && b[6] == c) || (b[8] == c && b[20] == c); // d0
                case 2 -> (b[0] == c && b[1] == c) || (b[5] == c && b[7] == c) || (b[13] == c && b[22] == c); // go
                case 3 -> (b[0] == c && b[6] == c) || (b[4] == c && b[5] == c) || (b[9] == c && b[17] == c); // b1
                case 4 -> (b[3] == c && b[5] == c); // d1
                case 5 -> (b[2] == c && b[7] == c) || (b[3] == c && b[4] == c) || (b[12] == c && b[19] == c); // f1
                case 6 -> (b[0] == c && b[3] == c) || (b[10] == c && b[14] == c); //c2
                case 7 -> (b[2] == c && b[5] == c) || (b[11] == c && b[16] == c); // e2
                case 8 -> (b[0] == c && b[20] == c) || (b[9] == c && b[10] == c); // a3
                case 9 -> (b[3] == c && b[17] == c) || (b[8] == c && b[10] == c); // b3
                case 10 -> (b[6] == c && b[14] == c) || (b[8] == c && b[9] == c); // c3
                case 11 -> (b[7] == c && b[16] == c) || (b[12] == c && b[13] == c); // e3
                case 12 -> (b[5] == c && b[19] == c) || (b[11] == c && b[13] == c); // f3
                case 13 -> (b[2] == c && b[22] == c) || (b[11] == c && b[12] == c); // g3
                case 14 -> (b[6] == c && b[10] == c) || (b[15] == c && b[16] == c) || (b[17] == c && b[20] == c); // c4
                case 15 -> (b[14] == c && b[16] == c) || (b[18] == c && b[21] == c); // d4
                case 16 -> (b[7] == c && b[11] == c) || (b[14] == c && b[15] == c) || (b[19] == c && b[22] == c); // e4
                case 17 -> (b[3] == c && b[9] == c) || (b[14] == c && b[20] == c) || (b[18] == c && b[19] == c); // b5
                case 18 -> (b[15] == c && b[21] == c) || (b[17] == c && b[19] == c); // d5
                case 19 -> (b[5] == c && b[12] == c) || (b[16] == c && b[22] == c) || (b[17] == c && b[18] == c); // f5
                case 20 -> (b[0] == c && b[8] == c) || (b[14] == c && b[17] == c) || (b[21] == c && b[22] == c); // a6
                case 21 -> (b[15] == c && b[18] == c) || (b[20] == c && b[22] == c); // d6
                case 22 -> (b[2] == c && b[13] == c) || (b[16] == c && b[19] == c) || (b[20] == c && b[21] == c); // g6
                default -> false;
            };
        }
        return false;
    }

}
