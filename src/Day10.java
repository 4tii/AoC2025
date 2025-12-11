import com.microsoft.z3.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day10 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./input/Day10.txt"));
        List<Machine> machines = new ArrayList<>();
        for(String line : lines){
            machines.add(new Machine(
                    line.substring(line.indexOf('[')+1, line.lastIndexOf(']')),
                    line.substring(line.indexOf('('), line.lastIndexOf(')')+1),
                    line.substring(line.indexOf('{')+1, line.lastIndexOf('}'))
            ));
        }
        
        int result=0;
        for(Machine m : machines){
            for(int size=1; size<=m.buttons.size(); size++){
                List<Integer> resultList = new ArrayList<>();
                if(search(m.buttons, m.lights, size, 0, 0, resultList)) {
                    result += resultList.size();
                    break;
                }
            }
        }
        System.out.println(result);
        
        //Part 2
        result=0;
        for(Machine m : machines){
            result+=solve(m.buttons, m.joltage);
        }
        System.out.println(result);
    }

    private static boolean search(List<Integer> buttons, int lights, int remaining, int start, int currentXor, List<Integer> chosen){
        if(remaining == 0)
            return currentXor == lights;
        
        if(start >= buttons.size())
            return false;
        
        for(int i=start; i<buttons.size(); i++){
            int button = buttons.get(i);
            
            chosen.add(button);
            
            if(search(buttons, lights, remaining-1, i+1, currentXor ^ button, chosen))
                return true;
            
            chosen.removeLast();
        }
        return false;
    }
    
    private static int solve(List<Integer> buttons, int[] joltage){
        Context ctx = new Context();
        Optimize opt = ctx.mkOptimize();
        
        IntExpr[] x = new IntExpr[buttons.size()];
        for(int i=0; i<buttons.size(); i++){
             x[i] = ctx.mkIntConst("x"+i);
             opt.Add(ctx.mkGe(x[i], ctx.mkInt(0)));
        }
        
        
        for(int i=0; i<joltage.length; i++){
            ArithExpr<IntSort> sum = ctx.mkInt(0);
            for(int btn=0; btn<buttons.size(); btn++){
                if(((buttons.get(btn) >> i) &1) == 1){
                    sum = ctx.mkAdd(sum, x[btn]);
                }
            }
            opt.Add(ctx.mkEq(sum, ctx.mkInt(joltage[i])));
        }
        
        ArithExpr<IntSort> totalPresses = ctx.mkInt(0);
        for(IntExpr btn : x)
            totalPresses = ctx.mkAdd(totalPresses, btn);
        opt.MkMinimize(totalPresses);
        
        int result=0;
        if(opt.Check() == Status.SATISFIABLE){
            Model model = opt.getModel();
            result = Integer.parseInt(model.evaluate(totalPresses, false).toString());
        }
        ctx.close();
        return result;
    }
    
    public static class Machine{
        int lights;
        List<Integer> buttons;
        int[] joltage;
        
        public Machine(String lights, String buttons, String joltage) {
            this.lights = Integer.parseInt(new StringBuilder(lights.replace('#', '1').replace('.', '0')).reverse().toString(), 2);   
            this.buttons = Arrays.stream(buttons.split(" "))
                    .map(s -> s.replaceAll("[()]", ""))
                    .map(s ->{
                        int value=0;
                        for(String num: s.split(","))
                            value |= 1<<Integer.parseInt(num);
                        return value;
                    }).toList();
            this.joltage = Arrays.stream(joltage.split(",")).mapToInt(Integer::parseInt).toArray();
        }
    }
}
