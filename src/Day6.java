import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./input/Day6.txt"));

        List<List<Long>> numbers =
                lines.stream()
                        .limit(lines.size() - 1L)
                        .map(line -> Arrays.stream(line.trim().split("\\s+"))
                                .map(Long::parseLong)
                                .toList())
                        .toList();

        List<String> operators =
                Arrays.stream(lines.getLast()
                                .trim()
                                .split("\\s+"))
                        .toList();

        long result = 0;
        for(int i=0; i<operators.size(); i++) {
            if(operators.get(i).equals("*")){
                long subResult = 1;
                for(List<Long> list : numbers){
                    subResult *= list.get(i);
                }
                result += subResult;
            }else{
                long subResult = 0;
                for(List<Long> list : numbers){
                    subResult += list.get(i);
                }
                result += subResult;
            }

        }
        System.out.println(result);

        
        //Part 2
        char[][] chars = lines.subList(0, lines.size()-1).stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        List<List<Long>> numbers2 = getLists(chars);

        result=0;
        List<String> operators2 = operators.reversed();
        for(int i=0; i<operators2.size(); i++) {
            if(operators2.get(i).equals("*")){
                long subResult = 1;
                for(Long value : numbers2.get(i)){
                    subResult *= value;
                }
                result += subResult;
            }else{
                long subResult = 0;
                for(Long value : numbers2.get(i)){
                    subResult += value;
                }
                result += subResult;
            }

        }
        System.out.println(result);
    }

    private static List<List<Long>> getLists(char[][] chars) {
        List<List<Long>> numbers2 = new ArrayList<>();
        List<Long> currentList = new ArrayList<>();
        for(int i = chars[0].length-1; i>=0; i--){
            StringBuilder sb = new StringBuilder();
            for (char[] aChar : chars) {
                sb.append(aChar[i]);
            }
            if(sb.toString().isBlank()){
                numbers2.add(currentList);
                currentList = new ArrayList<>();
            } else {
                currentList.add(Long.parseLong(sb.toString().trim()));
            }
        }
        numbers2.add(currentList);
        return numbers2;
    }
}

