import java.util.Arrays;

public class WolframRules {
    //Edge n, Edge nw, Edge sw, Edge s, Edge se, Edge ne

    public static final Hexagon.eddieDir[][] input = new Hexagon.eddieDir[][]{ //5x6
            {Hexagon.eddieDir.NA,Hexagon.eddieDir.TC,Hexagon.eddieDir.NA,Hexagon.eddieDir.NA,Hexagon.eddieDir.TC,Hexagon.eddieDir.NA},//2
            {Hexagon.eddieDir.NA,Hexagon.eddieDir.TC,Hexagon.eddieDir.NA,Hexagon.eddieDir.NA,Hexagon.eddieDir.TC,Hexagon.eddieDir.TC},//3
            {Hexagon.eddieDir.TC,Hexagon.eddieDir.NA,Hexagon.eddieDir.NA,Hexagon.eddieDir.TC,Hexagon.eddieDir.NA,Hexagon.eddieDir.TC},//3
            {Hexagon.eddieDir.NA,Hexagon.eddieDir.TC,Hexagon.eddieDir.NA,Hexagon.eddieDir.TC,Hexagon.eddieDir.NA,Hexagon.eddieDir.TC},//3
            {Hexagon.eddieDir.TC,Hexagon.eddieDir.TC,Hexagon.eddieDir.NA,Hexagon.eddieDir.TC,Hexagon.eddieDir.TC,Hexagon.eddieDir.NA}//4
    };

    public static final Hexagon.eddieDir[][] output = new Hexagon.eddieDir[][]{ //5x6
            {Hexagon.eddieDir.NA,Hexagon.eddieDir.NA,Hexagon.eddieDir.FC,Hexagon.eddieDir.NA,Hexagon.eddieDir.NA,Hexagon.eddieDir.FC},
            {Hexagon.eddieDir.FC,Hexagon.eddieDir.NA,Hexagon.eddieDir.FC,Hexagon.eddieDir.FC,Hexagon.eddieDir.NA,Hexagon.eddieDir.NA},
            {Hexagon.eddieDir.NA,Hexagon.eddieDir.FC,Hexagon.eddieDir.FC,Hexagon.eddieDir.NA,Hexagon.eddieDir.FC,Hexagon.eddieDir.NA},
            {Hexagon.eddieDir.NA,Hexagon.eddieDir.FC,Hexagon.eddieDir.NA,Hexagon.eddieDir.FC,Hexagon.eddieDir.NA,Hexagon.eddieDir.FC},
            {Hexagon.eddieDir.NA,Hexagon.eddieDir.FC,Hexagon.eddieDir.FC,Hexagon.eddieDir.NA,Hexagon.eddieDir.FC,Hexagon.eddieDir.FC}
    };


    /**
     * Enforces rule on the given Hexagon if there is one, otherwise pushes eddies forward inside the Hexagon
     * @param original
     * @param copy
     */
    public void enforceRule(Hexagon original, Hexagon copy){
        if(copy == null) {
            System.out.println("Clone is null");
            System.exit(0);
        }

        Hexagon.eddieDir[] hex = original.getHexagonEddies();
        int count;

        for(int r = 0; r < input.length; r++) { //for every rule input
            count = 0;
            for (int i = 0; i < 6; i++) { //for every edge
                if(hex[i] == input[r][i]) {  //compare edge with rule
                    count++;
                }else if(hex[i] == null) {
                    copy.reflect1(hex, original);
//                    copy.reflect2(hex, original);
                    return;
                }
            }

            if(count == 6) {
                copy.updateHexagon(r, true, null, original); //update according to rule
                return;
            }
        }

        copy.updateHexagon(-2, false, null, original); //push forward
    }
}
