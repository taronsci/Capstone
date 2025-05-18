public abstract class CollisionRules {

    protected Hexagon.eddieDir[][] input;
    protected Hexagon.eddieDir[][] output;

     CollisionRules(Hexagon.eddieDir[][] input, Hexagon.eddieDir[][] output){
        this.input = input;
        this.output = output;
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

        Hexagon.eddieDir[] hex = original.getHexagonEddies();
        int count;

        for(int r = 0; r < input.length; r++) { //for every rule input
            count = 0;
            for (int i = 0; i < 6; i++) { //for every edge
                if(hex[i] == input[r][i]) {  //compare edge with rule
                    count++;
                }else if(hex[i] == null) {
                    copy.reflectSlip(hex, original);
//                    copy.reflectNoSlip(hex, original);
                    return;
                }
            }
            if(count == 6) {
                copy.updateHexagon(output[r], original); //update according to rule
                return;
            }
        }

        copy.updateHexagon(null, original); //push forward
    }
}

