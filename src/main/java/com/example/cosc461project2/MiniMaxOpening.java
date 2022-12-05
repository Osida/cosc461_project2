package com.example.cosc461project2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MiniMaxOpening {


    private static int positions_evaluated=0;
    private static int minimax_estimate = 0;

    public static void main(String[] args){

        File board1F = new File(args[0]);
        File board2F = new File(args[1]);
        int depth = Integer.parseInt(args[2]);

        try {
            FileInputStream fis = new FileInputStream(board1F);
            PrintWriter out = new PrintWriter(new FileWriter(board2F));
            //FileOutputStream fos = new FileOutputStream(fOutputFile);
            Scanner s= new Scanner(fis);

            while(s.hasNextLine()){
                String str= s.next();
                char[] b = str.toCharArray();
                //Entry<String,Integer> bm= null;
                MiniMaxOpening m = new MiniMaxOpening();
                System.out.println("__BOARD__: "+new String(b));

                char[] d = m.MaxMin(b, depth);
                System.out.println("POSITIONS_EVALUATED :"+m.positions_evaluated);
                System.out.println("MINIMAX_ESTIMATE :"+m.minimax_estimate);
                out.println("Board Position : "+new String(d));
                out.println("Positions evaluated by static estimation : "+m.positions_evaluated);
                out.println("MiniMax estimate : " +m.minimax_estimate);
            }
            fis.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList gAdd(char [] b){
        ArrayList<char[]> gAList = new ArrayList<char[]>();
        char bCopy[];
        for(int i = 0; i < b.length; i++){
            if(b[i] == 'x'){
                bCopy = b.clone();
                bCopy[i] = 'W';
                if(closeMill(i,bCopy)){
                    gAList = gRem(bCopy, gAList);
                }else{
                    gAList.add(bCopy);
                }
            }
        }
        return gAList;
    }
    public void gMov(){

    }
    public void gHop(){

    }
    public ArrayList gRem(char [] b, ArrayList l){
        ArrayList<char[]> gRList = (ArrayList) l.clone();

        for (int i = 0; i < b.length; i++){
            if(b[i]=='B') {
                if(!(closeMill(i,b))){
                    char bCopy[]= b.clone();
                    bCopy[i] = 'x';
                    gRList.add(bCopy);
                }else{
                    char bCopy[]=b.clone();
                    gRList.add(bCopy);
                }
            }
        }
        return gRList;

    }
    public ArrayList gBlk(char[] b) {

        char[] tempb = b.clone();
        for(int i=0;i<tempb.length;i++) {
            if(tempb[i]=='W') {
                tempb[i] = 'B';
                continue;
            }
            if(tempb[i]=='B') {
                tempb[i] = 'W';
            }
        }

        ArrayList<char[]> gbm = new ArrayList<char[]>();
        ArrayList<char[]> gbmswap = new ArrayList<char[]>();

        gbm = gAdd(tempb);
        for(char[] y : gbm) {
            char[] tempbc = y;
            for(int i=0;i<tempbc.length;i++) {
                if(tempbc[i]=='W') {
                    tempbc[i] = 'B';
                    continue;
                }
                if(tempbc[i]=='B') {
                    tempbc[i] = 'W';
                }
            }
            gbmswap.add(y);
        }
        return gbmswap;
    }

    public char[] MaxMin(char[] b, int depth){
        if(depth>0){
            System.out.println("MAXMIN_DEPTH: "+ depth);
            depth--;
            ArrayList<char[]> white = new ArrayList<char[]>();
            char[] minBoard;
            char[] maxBoardchoice = new char[50];
            white = gAdd(b);
            int v=-999999;

            for(int i=0;i<white.size();i++){
                minBoard = MinMax(white.get(i), depth);
                if(v<sEstOpen(minBoard)){
                    v = sEstOpen(minBoard);
                    minimax_estimate = v;
                    maxBoardchoice = white.get(i);
                }
            }
            return maxBoardchoice;
        }else if(depth == 0){
            positions_evaluated++;
        }
        return b;
    }
    public char[] MinMax(char[]b, int depth) {

        if(depth>0) {
            depth--;
            ArrayList<char[]> black = new ArrayList<char[]>();
            char[] maxBoard;
            char[] minBoardchoice = new char[50];
            black = gBlk(b);
            int v=999999;

            for(int i=0;i<black.size();i++) {
                maxBoard = MaxMin(black.get(i), depth);
                if(v>sEstOpen(maxBoard)) {
                    v = sEstOpen(maxBoard);
                    minBoardchoice = black.get(i);
                }
            }
            return minBoardchoice;
        }
        else if(depth==0){
            positions_evaluated++;
        }
        return b;
    }

    public int[] neighbors(int location){ //format return better?
         switch (location) {
            case 0:
                return new int[]{1, 3, 8};
            case 1 :
                return new int[] {0,2,4};
            case 2 :
                return new int[]{1,5,13};
            case 3 :
                return new int[]{0,4,6,9};
            case 4 :
                return new int[]{1,3,5};
            case 5 :
                return new int[]{2,4,7,12};
            case 6 :
                return new int[]{3,7,9};
            case 7 :
                return new int[]{5,6,11};
            case 8 :
                return new int[]{0,9,20};
            case 9 :
                return new int[]{3,8,10,17};
            case 10 :
                return new int[]{6,9,14};
            case 11 :
                return new int[]{7,12,16};
            case 12 :
                return new int[]{5,11,13,19};
            case 13 :
                return new int[]{2,12,22};
            case 14 :
                return new int[]{10,15,17};
            case 15 :
                return new int[]{4,16,18};
            case 16 :
                return new int[]{11,15,19};
            case 17 :
                return new int[]{9,14,18,20};
            case 18 :
                return new int[]{15,17,19,21};
            case 19 :
                return new int[]{12,16,18,22};
            case 20 :
                return new int[]{8,17,21};
            case 21 :
                return new int[]{18,20,22};
            case 22 :
                return new int[]{13,19,21};
            default :
                return new int[]{};
        }
    }
    public boolean closeMill(int location, char [] b){
        char color = b[location];
        if(color != 'x'){
            return switch (location) {
                case 0 -> (b[1] == color && b[2] == color) || (b[3] == color && b[6] == color) || (b[8] == color && b[20] == color);
                case 1 -> b[0] == color && b[2] == color;
                case 2 -> (b[0] == color && b[1] == color) || (b[5] == color && b[7] == color) || (b[13] == color && b[22] == color);
                case 3 -> (b[0] == color && b[6] == color) || (b[4] == color && b[5] == color) || (b[9] == color && b[17] == color);
                case 4 -> b[3] == color && b[5] == color;
                case 5 -> (b[2] == color && b[7] == color) || (b[3] == color && b[4] == color) || (b[12] == color && b[19] == color);
                case 6 -> (b[0] == color && b[3] == color) || (b[10] == color && b[14] == color);
                case 7 -> (b[2] == color && b[5] == color) || (b[11] == color && b[16] == color);
                case 8 -> (b[0] == color && b[20] == color) || (b[9] == color && b[10] == color);
                case 9 -> (b[3] == color && b[17] == color) || (b[8] == color && b[10] == color);
                case 10 -> (b[6] == color && b[14] == color) || (b[8] == color && b[9] == color);
                case 11 -> (b[7] == color && b[16] == color) || (b[12] == color && b[13] == color);
                case 12 -> (b[5] == color && b[19] == color) || (b[11] == color && b[13] == color);
                case 13 -> (b[2] == color && b[22] == color) || (b[11] == color && b[12] == color);
                case 14 -> (b[6] == color && b[10] == color) || (b[15] == color && b[16] == color) || (b[17] == color && b[20] == color);
                case 15 -> (b[14] == color && b[16] == color) || (b[18] == color && b[21] == color);
                case 16 -> (b[7] == color && b[11] == color) || (b[14] == color && b[15] == color) || (b[19] == color && b[22] == color);
                case 17 -> (b[3] == color && b[9] == color) || (b[14] == color && b[20] == color) || (b[18] == color && b[19] == color);
                case 18 -> (b[15] == color && b[21] == color) || (b[17] == color && b[19] == color);
                case 19 -> (b[5] == color && b[12] == color) || (b[16] == color && b[22] == color) || (b[17] == color && b[18] == color);
                case 20 -> (b[0] == color && b[8] == color) || (b[14] == color && b[17] == color) || (b[21] == color && b[22] == color);
                case 21 -> (b[15] == color && b[18] == color) || (b[20] == color && b[22] == color);
                case 22 -> (b[2] == color && b[13] == color) || (b[16] == color && b[19] == color) || (b[20] == color && b[21] == color);
                default -> false;
            };
        }
        return false;
    }
    public int sEstOpen(char [] b){
        int cW = 0;
        int cB = 0;
        //count number of white/black pieces
        for (char c : b) {
            if (c == 'W')
                cW++;
            if (c == 'B')
                cB++;
        }
        return cW-cB;
    }

//    public int sEstMidEnd(char [] b, int p){//Unfinished implementation of numBlackMoves
//        int cW = 0;
//        int cB = 0;
//        //count number of white/black pieces
//        for (char c : b) {
//            if (c == 'W')
//                cW++;
//            if (c == 'B')
//                cB++;
//        }
//        if(cB <= 2)
//            return 10000;//white wins
//        else if(cW <= 2)
//            return -10000;//black wins
//        else if (p == 0)//Unfinished Unfinished Unfinished
//            return 10000;//white wins
//        else
//            return 1000*(cW-cB)-p;
//    }

}