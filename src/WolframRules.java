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
     * Checks if there is a corresponding rule for the given hexagon. Rotations not considered
     * @param h hexagon
     * @return the rule to be enforced
     */
    private int checkFixedRules(Hexagon h){
        Hexagon.eddieDir[] hex = h.getHexagonEddies();

//        if(hex == null)
//            return -10;

        for(int i = 0; i < 5; i++) { //for every rule input
            if (Arrays.equals(hex, input[i])) {
                return i;
            }
        }
        return -1; //move eddies forward
    }

    /**
     * Tries to find a rule by ignoring mismatched eddies. Checks rule conditions on h, applies it on copy.
     * @param h original Hexagon
     * @param copy copy Hexagon
     */
    //have boolean flags while checking if hexagon is same as input. if flag is false - ignore it next time
    private void enforceNonFixedRule(Hexagon h, Hexagon copy){
        Hexagon.eddieDir[] hex = h.getHexagonEddies();

        boolean[][] flags = new boolean[5][6]; //gather flags for all rules
        int[] mismatches= new int[5];
        int collisionEddieCount; //number of eddies in a hexagon

        for (int r = 0;r < input.length;r++) { //for every rule
//            System.out.println("rule " + r);
            collisionEddieCount = 0;

            for (int i = 0; i < 6; i++) { //compare each edge
                if(hex[i] == null) {
//                    copy.reflect1(hex, h);
                    copy.reflect2(hex, h);
                    return;
                    //call a method that does wall reflection
                }

                if(hex[i] == input[r][i]) {  // equal || (hex[i] == Hexagon.eddieDir.FC && input[r][i] == Hexagon.eddieDir.NA)
                    flags[r][i] = true;
                } else{
                    if((hex[i] == Hexagon.eddieDir.FC && input[r][i] == Hexagon.eddieDir.NA))
                        mismatches[r]++; //mismatch means that there is an eddie in the hexagon that isn't the same as its corresponding input eddie
                    else
                        mismatches[r]+=2; //if error check this
                }

                if(hex[i] == Hexagon.eddieDir.TC)
                    collisionEddieCount++;
            }
//            System.out.println("mismatches are " + mismatches[r]);
//            System.out.println("collision count is " + collisionEddieCount);

            if (collisionEddieCount == 0){
//                System.out.println("no updates needed");
                return;
            }
            else if(collisionEddieCount == 1){ //there is 1 TC eddie, so it should be pushed forward
//                System.out.println(3);
                copy.updateHexagon(-2, false, null, h); //push forward the TC eddie
                return;
            }
            //if the rule being checked has 1 mismatch, then this is the best rule.
            else if(collisionEddieCount >= 2 && mismatches[r] == 1){ //1. do the update with the least amount of mismatches, or the first one found 2. maybe it would be beneficial to start checking rules from the end, because they have more active eddies 3. ignore that one and do update
//                System.out.println(2);
                System.out.println(Arrays.toString(flags[r]));
                copy.updateHexagon(r, false, flags[r], h); //give update an array of edges that should be changed according to the table, the others should move forward
                return;
            }
        }

        //could be random
        int smallest = 0; // find rule with smallest mismatches
        for(int i = 1;i < mismatches.length;i++){
            if(mismatches[i] <= mismatches[smallest]) //could be < or random
                smallest = i;
        }

//        System.out.println();
//        System.out.println("smallest mismatches are for rule " + smallest);
//        System.out.println(Arrays.toString(flags[smallest]));
        copy.updateHexagon(smallest,false, flags[smallest], h);
    }

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

        int rule = checkFixedRules(original);

//        if(rule == -10)
//            return;

        if(rule != -1){ //if a rule is found
            copy.updateHexagon(rule, true,null, original);
        }else{
            enforceNonFixedRule(original, copy);
        }
    }
}
