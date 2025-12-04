import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Day4 {
    public static void main(String[] args) throws IOException {
        char[][] input;
        try (Stream<String> lines = Files.lines(Paths.get("./input/Day4.txt"))) {
            input = lines.map(String::toCharArray).toArray(char[][]::new);
        }

        int result = 0;
        for(int i=0;i<input.length;i++) {
            for(int j=0;j<input[i].length;j++) {
                if(input[i][j] == '@' && getNumberOfAdjacentRolls(input, i, j)<4)
                    result++;
            }
        }
        System.out.println(result);

        //Part 2
        result = 0;
        int subResult;
        char[][] newInput = new char[input.length][input[0].length];
        do{
            subResult = 0;
            for(int i=0;i<input.length;i++) {
                for(int j=0;j<input[i].length;j++) {
                    if(input[i][j] == '@' && getNumberOfAdjacentRolls(input, i, j)<4) {
                        subResult++;
                        newInput[i][j] = '.';
                    } else {
                        newInput[i][j] = input[i][j];
                    }
                }
            }
            result += subResult;
            input = newInput;
        }while(subResult != 0);

        System.out.println(result);
    }

    private static int getNumberOfAdjacentRolls(char[][] input, int row, int col) {
        int count = 0;
        int size = input.length-1;

        if(row>0 && col>0 && input[row-1][col-1] == '@')
            count++;
        if(col>0 && input[row][col-1] == '@')
            count++;
        if(row< size && col>0 && input[row+1][col-1] == '@')
            count++;
        if(row>0 && input[row-1][col] == '@')
            count++;
        if(row<size && input[row+1][col] == '@')
            count++;
        if(row>0 && col<size && input[row-1][col+1] == '@')
            count++;
        if(col<size && input[row][col+1] == '@')
            count++;
        if(row<size && col<size && input[row+1][col+1] == '@')
            count++;

        return count;
    }
}
