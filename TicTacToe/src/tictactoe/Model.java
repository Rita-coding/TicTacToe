package tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class Model {

    private final int size;
    private Player actualPlayer;
    private Player[][] table;
    private final ArrayList<Integer> Xi;
    private final ArrayList<Integer> Xj;
    private final ArrayList<Integer> Oi;
    private final ArrayList<Integer> Oj;


    public Model(int size) {
        this.size = size;
        actualPlayer = Player.X;
        Xi = new ArrayList<>();
        Xj = new ArrayList<>();
        Oi = new ArrayList<>();
        Oj = new ArrayList<>();
        table = new Player[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                table[i][j] = Player.NOBODY;
            }
        }
    }


    public Player step(int row, int column) {
        if (table[row][column] != Player.NOBODY) {
            return table[row][column];
        }

        table[row][column] = actualPlayer;
        if (actualPlayer == Player.X) {
            this.Xi.add(row);
            this.Xj.add(column);
        } else {
            this.Oi.add(row);
            this.Oj.add(column);
        }

        //System.out.println(Xi);

        if (actualPlayer == Player.X) {
            actualPlayer = Player.O;
        } else {
            actualPlayer = Player.X;
        }


        return table[row][column];
    }

    public boolean isDraw(){
        int count =0;
        for(int i=0; i<size; i++){
            for(int j =0; j<size; j++){
                if(table[i][j] == Player.NOBODY){
                    count++;
                }
            }
        }
        return count==0 && findWinner() == Player.NOBODY;
    }

    public Player findWinner() {

        //rows
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] != Player.NOBODY) {
                    boolean ok = true;
                    Player potential = table[i][j];
                    for (int z = 1; ok && z < 5; ++z) {
                        if (j + z > size - 1) {
                            ok = false;
                        }
                        ok = ok && table[i][j + z] == potential;
                    }
                    if (ok) {
                        return potential;
                    }
                }
            }
        }
        //column
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] != Player.NOBODY) {
                    boolean ok = true;
                    Player potential = table[i][j];
                    for (int z = 1; ok && z < 5; ++z) {
                        if (i + z > size - 1) {
                            ok = false;
                        }
                        ok = ok && table[i + z][j] == potential;
                    }
                    if (ok) {
                        return potential;
                    }
                }
            }
        }

        //right diagonal
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] != Player.NOBODY) {
                    boolean ok = true;
                    Player potential = table[i][j];
                    for (int z = 1; ok && z < 5; ++z) {
                        if (i + z > size - 1 || j + z > size - 1) {
                            ok = false;
                        }
                        ok = ok && table[i + z][j + z] == potential;
                    }
                    if (ok) {
                        return potential;
                    }
                }
            }
        }

        //left diagonal
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] != Player.NOBODY) {
                    boolean ok = true;
                    Player potential = table[i][j];
                    for (int z = 1; ok && z < 5; ++z) {
                        if (i + z > size - 1 || j + z < size - 1) {
                            ok = false;
                        }
                        ok = ok && table[i + z][j - z] == potential;
                    }
                    if (ok) {
                        return potential;
                    }
                }
            }
        }

        return Player.NOBODY;
    }

    public Player[][] minusPieces(int row, int col) {

        Player player = step(row,col);
        Player winner = Player.NOBODY;
        winner = findWinner();
        if(player != Player.NOBODY && winner == Player.NOBODY){
            Player potential = player;

            //row
            if(col == 0 ){

                if(table[row][col+1] == player && table[row][col+2] == player && table[row][col+3] == player){
                    return changeFour(potential);
                }else if(table[row][col+1] == player && table[row][col+2] == player){
                   return changeThree(potential);
                }
            }

            if(col == size-1){
                if(table[row][col-1] ==player && table[row][col-2] == player && table[row][col-3] == player){
                    return changeFour(potential);
                }else if(table[row][col-1] ==player && table[row][col-2] == player){
                    return changeThree(potential);
                }
            }

            if(col == 1){
                if(table[row][col+1] == player && table[row][col+2] == player && table[row][col+3] == player ||
                table[row][col-1] == player && table[row][col+1] == player && table[row][col+2] == player){
                    return changeFour(potential);
                }else if(table[row][col+1] == player && table[row][col+2] == player ||
                table[row][col-1] == player && table[row][col+1] == player){
                    return changeThree(potential);
                }
            }

            if(col == size-2){
                if(table[row][col-1] == player && table[row][col+1] == player && table[row][col-2] == player ||
                table[row][col-1] ==player && table[row][col-2] == player && table[row][col-3] == player){
                    return changeFour(potential);
                }else if(table[row][col-1] == player && table[row][col+1] == player ||
                table[row][col-1] ==player && table[row][col-2] == player){
                    return changeThree(potential);
                }
            }

            if(col >1 && col<size-3){
                if(table[row][col+1] == player && table[row][col+2] == player && table[row][col+3] == player ||
                table[row][col+1] == player && table[row][col+2] == player && table[row][col-1] == player ||
                table[row][col+1] == player && table[row][col-2] == player && table[row][col-1] == player ||
                table[row][col-1] == player && table[row][col+1] == player && table[row][col+2] == player  ||
                table[row][col-1] ==player && table[row][col-2] == player && table[row][col+1] == player){

                    return changeFour(potential);
                }
            }

            if(col > 2 && col < size-2){
                if(table[row][col+1] == player && table[row][col+2] == player && table[row][col-1] == player ||
                table[row][col+1] == player && table[row][col-1] == player && table[row][col-2] == player ||
                table[row][col-1] == player && table[row][col-2] == player && table[row][col-3] == player){

                  return changeFour(potential);
                }
            }

            if(col > 1 && col<size-2){
                if(table[row][col+1] == player && table[row][col+2] == player ||
                   table[row][col-1] == player && table[row][col+1] == player ||
                   table[row][col-1] ==player && table[row][col-2] == player){

                   return changeThree(potential);
                }
            }


            //col
            if(row == 0 ){

                if(table[row+1][col] == player && table[row+2][col] == player && table[row+3][col] == player){
                    return changeFour(potential);
                }else if(table[row+1][col] == player && table[row+2][col] == player){
                    return changeThree(potential);
                }
            }

            if(row == size-1){
                if(table[row-1][col] == player && table[row-2][col] == player && table[row-3][col] == player){
                    return changeFour(potential);
                }else if(table[row-1][col] ==player && table[row-2][col] == player){
                    return changeThree(potential);
                }
            }

            if(row == 1){
                if(table[row+1][col] == player && table[row+2][col] == player && table[row+3][col] == player ||
                   table[row-1][col] == player && table[row+1][col] == player && table[row+2][col] == player){
                   return changeFour(potential);
                }else if(table[row+1][col] == player && table[row+2][col] == player ||
                        table[row-1][col] == player && table[row+1][col] == player){
                    return changeThree(potential);
                }
            }

            if(row == size-2){
                if(table[row-1][col] == player && table[row-2][col] == player && table[row-3][col] == player ||
                   table[row+1][col] == player && table[row-1][col] == player && table[row-2][col] == player){
                    return changeFour(potential);

                }else if(table[row+1][col] == player && table[row-1][col] == player ||
                        table[row-1][col] ==player && table[row-2][col] == player){

                   return changeThree(potential);
                }
            }

            if(row >1 && row<size-3){
                if(table[row-2][col] == player && table[row-1][col] == player && table[row+1][col] == player ||
                   table[row-1][col] == player && table[row+1][col] == player && table[row+2][col] == player ||
                   table[row+1][col] == player && table[row+2][col] == player && table[row+3][col] == player){

                    return changeFour(potential);
                }
            }

            if(row > 2 && row < size-2){
                if(table[row-3][col] == player && table[row-2][col] == player && table[row-1][col] == player ||
                   table[row-2][col] == player && table[row-1][col] == player && table[row+1][col] == player ||
                   table[row-1][col] == player && table[row+1][col] == player && table[row+2][col] == player ||
                   table[row+1][col] == player && table[row-1][col] == player && table[row-2][col] == player ||
                   table[row+2][col] == player && table[row+1][col] == player && table[row-1][col] == player){

                    return changeFour(potential);
                }
            }

            if(row > 1 && row < size-2){
                if(table[row-2][col] == player && table[row-1][col] == player ||
                   table[row-1][col] == player && table[row+1][col] == player ||
                   table[row+1][col] ==player && table[row+2][col] == player){

                    return changeThree(potential);
                }
            }



            //diagonals
            try{
                if(table[row+1][col+1] == player && table[row+2][col+2] == player && table[row+3][col+3] == player){
                   return changeFour(potential);
                }

                if(row+3 > size-1 || col + 3> size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);

            }

            try{
                if(table[row-1][col-1] == player && table[row+1][col+1] == player && table[row+2][col+2] == player){
                    return changeFour(potential);
                }

                if(row+2 > size-1 || col + 2> size-1 || row -1 <size-1 || col-1 < size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);

            }

            try{
                if(table[row-1][col-1] == player && table[row-2][col-2] == player && table[row+1][col+1] == player){
                    return changeFour(potential);
                }

                if(row+1 > size-1 || col + 1> size-1 || row -2 <size-1 || col-2 < size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);
            }

            try{
                if(table[row-1][col-1] == player && table[row-2][col-2] == player && table[row-3][col-3] == player){
                    return changeFour(potential);
                }

                if(row-3 < size-1 || col - 3< size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);
            }


            try{
                if(table[row+1][col+1] == player && table[row+2][col+2] == player){
                    return changeThree(potential);
                }
                if(row+2 > size-1 || col + 2> size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);
            }

            try{
                if(table[row+1][col+1] == player && table[row-1][col-1] == player){
                    return changeThree(potential);
                }
                if(row+1 > size-1 || col + 1> size-1 || row-1<size-1 || col-1<size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);
            }



            try{
                if(table[row-1][col-1] == player && table[row-2][col-2] == player ){
                    return changeThree(potential);
                }

                if(row-2 < size-1 || col - 2< size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);
            }

            //left diagonal
            try{
                if(table[row+1][col-1] == player && table[row+2][col-2] == player && table[row+3][col-3] == player){
                    return changeFour(potential);
                }

                if(row+3 > size-1 || col + 3> size-1 || row-3<size-1 || col-3<size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);

            }

            try{
                if(table[row-1][col+1] == player && table[row+1][col-1] == player && table[row+2][col-2] == player){
                    return changeFour(potential);
                }

                if(row+2 > size-1 || col + 1> size-1 || row -1 <size-1 || col-2 < size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);

            }

            try{
                if(table[row-1][col+1] == player && table[row-2][col+2] == player && table[row+1][col-1] == player){
                    return changeFour(potential);
                }

                if(row+1 > size-1 || col + 2> size-1 || row -2 <size-1 || col-1 < size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);
            }

            try{
                if(table[row-1][col+1] == player && table[row-2][col+2] == player && table[row-3][col+3] == player){
                    return changeFour(potential);
                }

                if(row-3 < size-1 || col - 3< size-1 || row+3>size-1 || col+3>size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);
            }


            try{
                if(table[row+1][col-1] == player && table[row+2][col-2] == player){
                    return changeThree(potential);
                }
                if(row+2 > size-1 || col + 2> size-1 || row-2<size-1 || col-2<size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);
            }

            try{
                if(table[row-1][col+1] == player && table[row+1][col-1] == player){
                    return changeThree(potential);
                }
                if(row+1 > size-1 || col + 1> size-1 || row-1<size-1 || col-1<size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);
            }



            try{
                if(table[row-1][col+1] == player && table[row-2][col+2] == player ){
                    return changeThree(potential);
                }

                if(row-2 < size-1 || col - 2< size-1 || row+2 > size-1 || col+2 > size-1){
                    throw new Exception();
                }
            }catch (Exception  e){
                //System.out.print(e);
            }
        }
        return table;

    }



        public Player[][] changeThree (Player potential){
            Random random = new Random();
            int rand;
            if (potential == Player.X) {
                rand = random.nextInt(Xi.size());
                table[Xi.get(rand)][Xj.get(rand)] = Player.NOBODY;
                Xi.remove(Xi.get(rand));
                Xj.remove(Xj.get(rand));
                return table;
            } else {
                rand = random.nextInt(Oi.size());
                table[Oi.get(rand)][Oj.get(rand)] = Player.NOBODY;
                Oi.remove(Oi.get(rand));
                Oj.remove(Oj.get(rand));
                return table;
            }
        }

        public Player[][] changeFour(Player potential){
            Random random = new Random();
            int rand1;
            int rand2;
            if (potential == Player.X) {
                rand1 = random.nextInt(Xi.size());
                table[Xi.get(rand1)][Xj.get(rand1)] = Player.NOBODY;
                Xi.remove(Xi.get(rand1));
                Xj.remove(Xj.get(rand1));

                rand2 = random.nextInt(Xi.size());
                table[Xi.get(rand2)][Xj.get(rand2)] = Player.NOBODY;
                Xi.remove(Xi.get(rand2));
                Xj.remove(Xj.get(rand2));
                return table;
            } else {
                rand1 = random.nextInt(Oi.size());
                table[Oi.get(rand1)][Oj.get(rand1)] = Player.NOBODY;
                Oi.remove(Oi.get(rand1));
                Oj.remove(Oj.get(rand1));

                rand2 = random.nextInt(Oi.size());
                table[Oi.get(rand2)][Oj.get(rand2)] = Player.NOBODY;
                Oi.remove(Oi.get(rand2));
                Oj.remove(Oj.get(rand2));
                return table;
            }
        }

        public Player getActualPlayer () {
            return actualPlayer;
        }
    }

