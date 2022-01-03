import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.lang.Math;

public class main {

    private static int C=0;
    private static int R=0;
    private static int C2=0;
    private static int R2=0;

    private static void quickSort(int arr[][], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    private static int partition(int arr[][], int begin, int end) {
        int pivot = arr[end][0];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr[j][0] >= pivot) {
                i++;

                int[] swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        int[] swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;

        return i+1;
    }

    private static int[][] rotate(int[][] mat) {
        int[][] temp = new int[C2][R2];
        for (int y = 0; y < R2; y++){
            for (int x = 0; x < C2; x++) {
                    temp[C2-1-x][y]=mat[y][x];
            }
        }
        int tempo = C2;
        C2 = R2;
        R2 = tempo;
        return temp;
    }

    private static int[][] flipHorizontal(int[][] mat){
        int[][] temp = new int[R2][C2];
        for (int i = 0; i < R2; i++){
            for (int j = 0; j < C2; j++) {
                temp[i][C2 - j - 1] = mat[i][j];
            }
        }
        return temp;
    }

public static void main(String[] args){
    Kattio io = new Kattio(System.in,System.out);


    int T = io.getInt();

    for(int i = 0; i < T; i++) {

        int output = 0;
        R = io.getInt();
        C = io.getInt();

        int[][] image1 = null;
        if(R != 0 && C != 0){
            image1 = new int[R][C];
            for (int y = 0; y < R; y++) {
                String row = io.getWord();
                for (int x = 0; x < C; x++) {
                    char c = row.charAt(x);
                    if (c == '#') {
                        image1[y][x] = 1;
                    } else {
                        image1[y][x] = 0;
                    }
                }
            }
        }


        R2 = io.getInt();
        C2 = io.getInt();
        int[][] image2 = null;
        if(R2 != 0 && C2 != 0){
            image2 = new int[R2][C2];
            for (int y = 0; y < R2; y++) {
                String row = io.getWord();
                for (int x = 0; x < C2; x++) {
                    char c = row.charAt(x);
                    if (c == '#') {
                        image2[y][x] = 1;
                    } else {
                        image2[y][x] = 0;
                    }
                }

            }
        }

        if(R2 != 0 && C2 != 0 && R != 0 && C != 0) {
            int[][] count = new int[2 * (C+(C2-1)) * (R+(R2-1))][3];
            int v = 0;
            for (int w = 0; w < 2; w++) {
                for (int y = 0; y < (R + R2 - 1); y++) {
                    for (int x = 0; x < (C + C2 - 1); x++) {
                        int sum = 0;
                        for (int j = 0; j < R2; j++) {
                            for (int k = 0; k < C2; k++) {
                                int temp1 = x + k - (C2 - 1);
                                int temp2 = y + j - (R2 - 1);
                                if (temp1 >= 0 && temp1 <= (C - 1) && temp2 >= 0 && temp2 <= (R - 1)) {
                                    sum = sum + image1[temp2][temp1];
                                }
                            }
                        }
                        count[v][0] = sum;
                        count[v][1] = y;
                        count[v][2] = x;
                        v++;
                    }
                }
                int bla = C2;
                C2 = R2;
                R2 = bla;
            }

            quickSort(count, 0, count.length - 1);

            int index = 0;
            int br = 0;

            outerloop:
            for (int[] arr : count) {
                for (int perm = 0; perm < 8; perm++) {
                    int sum = 0;

                    if (perm != 0) {
                        image2 = rotate(image2);
                    }

                    if (perm == 4) {
                        image2 = flipHorizontal(image2);
                    }
                    for (int j = 0; j < R2; j++) {
                        for (int k = 0; k < C2; k++) {
                            int temp1 = arr[2] + k - (C2 - 1);
                            int temp2 = arr[1] + j - (R2 - 1);
                            if (temp1 >= 0 && temp1 <= (C - 1) && temp2 >= 0 && temp2 <= (R - 1)) {
                                sum = sum + image2[j][k] * image1[temp2][temp1];
                            }
                        }
                    }
                    if (sum == arr[0] && output <= sum) {
                        output = sum;
                        break outerloop;
                    }
                    if (sum > output) {
                        output = sum;
                    }
                }
                image2 = rotate(image2);
            }
            io.println(output);
        }else {
            io.println(output);
        }

    }
    io.close();

}
}

class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
    }

    public String getWord() {
        return nextToken();
    }



    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}

