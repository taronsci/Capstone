import javax.print.attribute.standard.MediaSize;

public class Hexagon {
    public enum eddieDir{FC, TC, NA}
    //Hexagon only sees TC eddies

    private VertexGrid vGrid;
    private final Vertex center;

    //Edge n, Edge nw, Edge sw, Edge s, Edge se, Edge ne

    private Edge edge_n;
    private Edge edge_nw;
    private Edge edge_sw;
    private Edge edge_s;
    private Edge edge_se;
    private Edge edge_ne;


    public Hexagon(Vertex center, VertexGrid vgrid){
        this.center = center;
//        VertexGrid vgrid = VertexGrid.getInstance();
        int[] c = center.getCoordinate();

        edge_n = vgrid.getEdge(center, vgrid.getVertex(c[0],c[1]-1));
        edge_s = vgrid.getEdge(center,vgrid.getVertex(c[0],c[1]+1));

        if(center.getCoordinate()[0] % 2 != 0){ //kent
            edge_nw = vgrid.getEdge(center,vgrid.getVertex(c[0]-1,c[1])); //dzax
            edge_ne = vgrid.getEdge(center,vgrid.getVertex(c[0]+1,c[1])); //aj
            edge_sw = vgrid.getEdge(center,vgrid.getVertex(c[0]-1,c[1]+1)); //nerqev dzax
            edge_se = vgrid.getEdge(center,vgrid.getVertex(c[0]+1,c[1]+1)); //nerqev aj
        }else{ //zuyg
            edge_nw = vgrid.getEdge(center,vgrid.getVertex(c[0]-1,c[1]-1)); //verev dzax
            edge_ne = vgrid.getEdge(center,vgrid.getVertex(c[0]+1,c[1]-1)); //verev aj
            edge_sw = vgrid.getEdge(center,vgrid.getVertex(c[0]-1,c[1])); // dzax
            edge_se = vgrid.getEdge(center,vgrid.getVertex(c[0]+1,c[1])); // aj
        }
//        setInitialEdgeDirections(edge_n, edge_nw, edge_sw, edge_s, edge_se, edge_ne);
    }

    public Hexagon(Hexagon original){
        if(original == null){
            System.out.println("ERROR");
            System.exit(0);
        }
        center = new Vertex(original.center);
        edge_n = new Edge(original.edge_n);
        edge_nw = new Edge(original.edge_nw);
        edge_sw = new Edge(original.edge_sw);
        edge_s = new Edge(original.edge_s);
        edge_se = new Edge(original.edge_se);
        edge_ne = new Edge(original.edge_ne);
//        this.edgeDirections = new eddieDir[6];
//        System.arraycopy(original.edgeDirections, 0, this.edgeDirections, 0, edgeDirections.length);
    }

    /**
     * Called in setter methods to set the Edge direction
     * @param e
     * @param dir
     */
    private void setEdgeDirection(Edge e, eddieDir dir){
        switch(dir){
            case FC:
                if (center.equals(e.getStart()))  //if edge start Vertex corresponds to center of hexagon
                    e.setDirection(Edge.eddie.SE); //then set direction to Start-End
                else
                    e.setDirection(Edge.eddie.ES);
                break;
            case TC:
                if (center.equals(e.getStart()))  //if edge start Vertex corresponds to center of hexagon
                    e.setDirection(Edge.eddie.ES); //then set direction to Start-End
                else
                    e.setDirection(Edge.eddie.SE);
                break;
            case NA:
                e.setDirection(Edge.eddie.NA);
                break;
            default:
                System.out.println("Provided edge is not in this hexagon?");
        }
    }
    public boolean hasDirection(Edge e){
        return e.getDirection() != Edge.eddie.NA;
    }
    public boolean hasDirection(int edge){
        switch(edge) {
            case 0: return hasDirection(edge_n);
            case 1: return hasDirection(edge_nw);
            case 2: return hasDirection(edge_sw);
            case 3: return hasDirection(edge_s);
            case 4: return hasDirection(edge_se);
            case 5: return hasDirection(edge_ne);
            default: return false;
        }
    }
    public void setEdgeDirections(eddieDir[] output, boolean all) {
        if(output.length != 6)
            System.out.println("must have 6 directions to set");

        if(all) {
            for(int i = 0; i < output.length; i++){
                if(getEdge(i) != null)
                    setDirection(i, output[i]);
            }
        }else{
            for(int i = 0; i < output.length; i++){
                if(getEdge(i) != null && !hasDirection(i))
                    setDirection(i, output[i]);
            }

        }
    }
    public void setDirection(int edge, eddieDir dir){
        switch(edge) {
            case 0: setDirection_n(dir);
                break;
            case 1: setDirection_nw(dir);
                break;
            case 2: setDirection_sw(dir);
                break;
            case 3: setDirection_s(dir);
                break;
            case 4: setDirection_se(dir);
                break;
            case 5: setDirection_ne(dir);
                break;
        }
    }
    public void setDirection_n(eddieDir dir){
        setEdgeDirection(edge_n, dir);
//        edgeDirections[0] = dir;
    }
    public void setDirection_nw(eddieDir dir){
        setEdgeDirection(edge_nw, dir);
//        edgeDirections[1] = dir;
    }
    public void setDirection_sw(eddieDir dir){
        setEdgeDirection(edge_sw, dir);
//        edgeDirections[2] = dir;
    }
    public void setDirection_s(eddieDir dir){
        setEdgeDirection(edge_s, dir);
//        edgeDirections[3] = dir;
    }
    public void setDirection_se(eddieDir dir){
        setEdgeDirection(edge_se, dir);
//        edgeDirections[4] = dir;
    }
    public void setDirection_ne(eddieDir dir){
        setEdgeDirection(edge_ne, dir);
//        edgeDirections[5] = dir;
    }

    public String toString(){
        eddieDir[] edgeDirections = getHexagonEddies();
        return edgeDirections[0]+" "+ edgeDirections[1]+" "+ edgeDirections[2]+" "+ edgeDirections[3]+" "+ edgeDirections[4]+" "+ edgeDirections[5];
//        return center.toString();
    }

    public Vertex getCenter() {
        return center;
    }
    public Edge getEdge_n() {
        return edge_n;
    }
    public Edge getEdge_ne() {
        return edge_ne;
    }
    public Edge getEdge_nw() {
        return edge_nw;
    }
    public Edge getEdge_s() {
        return edge_s;
    }
    public Edge getEdge_se() {
        return edge_se;
    }
    public Edge getEdge_sw() {
        return edge_sw;
    }
    public Edge[] getEdges(){
        return  new Edge[]{edge_n, edge_nw, edge_sw, edge_s, edge_se, edge_ne};
    }
    public Edge getEdge(int edge){
        switch(edge) {
            case 0: return edge_n;
            case 1: return edge_nw;
            case 2: return edge_sw;
            case 3: return edge_s;
            case 4: return edge_se;
            case 5: return edge_ne;
            default: return null;
        }
    }

    /**
     * Gives the eddies representation of a hexagon
     * @return the eddies of every edge of the hexagon (in order)
     */
    public eddieDir[] getHexagonEddies(){
        eddieDir[] ans = new eddieDir[6];

        Edge[] edges = getEdges();
        Vertex other;
        for(int i = 0;i < 6;i++) {
            if(edges[i] == null)
                return null;

            if(edges[i].getDirection() != Edge.eddie.NA) { //if eddie has dir
                other = edges[i].getStart();

                if (center.equals(other)) { //if edge start Vertex corresponds to center of hexagon
                    if (edges[i].getDirection() == Edge.eddie.SE) { //if eddie is Start-End
                        ans[i] = eddieDir.FC;//then eddie direction is From-Center
                    } else {
                        ans[i] = eddieDir.TC;//otherwise direction is To-Center
                    }
                }else{
                    if (edges[i].getDirection() == Edge.eddie.SE) {
                        ans[i] = eddieDir.TC;
                    } else {
                        ans[i] = eddieDir.FC;
                    }
                }
            }else
                ans[i] = eddieDir.NA;
        }
        return ans;
    }
    public eddieDir getEddie(int i){
        Edge[] edges = getEdges();
        Vertex other;

            if(edges[i].getDirection() != Edge.eddie.NA) { //if eddie has dir
                other = edges[i].getStart();

                if (center.equals(other)) { //if edge start Vertex corresponds to center of hexagon
                    if (edges[i].getDirection() == Edge.eddie.SE) { //if eddie is Start-End
                        return eddieDir.FC;//then eddie direction is From-Center
                    } else {
                        return eddieDir.TC;//otherwise direction is To-Center
                    }
                }else{
                    if (edges[i].getDirection() == Edge.eddie.SE) {
                        return eddieDir.TC;
                    } else {
                        return eddieDir.FC;
                    }
                }
            }else
                return eddieDir.NA;
    }
    /**
     * Updates the hexagon according to the output of the rule, and the edges that should be changed. If a rule was not found, then push all eddies forward (only inside the hexagon)
     * @param rule the rule to be enforced
     * @param rules is true if a specific rule was found, false if not.
     */
    public void updateHexagon(int rule, boolean rules, boolean[] shouldBeChanged, Hexagon original){
        eddieDir[] originalEdgeDirections = original.getHexagonEddies();

        if(rules) { //if there is a rule then rules is true.
            setEdgeDirections(WolframRules.output[rule],true); //set eddies to clone
            original.setEdgeDirections(new eddieDir[]{eddieDir.NA,eddieDir.NA,eddieDir.NA,eddieDir.NA,eddieDir.NA,eddieDir.NA},true);//delete all from original
        }
        else if(shouldBeChanged != null){ //the rule is applied and the eddies in shouldBeChanged that are false should be pushed forward or should stay the same; else if(shouldBeChanged != null)
            for (int i = 0; i < 6; i++) {
                if(shouldBeChanged[i]) {
//                    System.out.println(10);

                    if(getEddie(i) == eddieDir.NA) //if eddie is FC, do not update it. just changed
                        setDirection(i, WolframRules.output[rule][i]);

                    original.setDirection(i,eddieDir.NA); //delete from original
                }
                else { //for the eddies that should be pushed forward or should not change
                    if (originalEdgeDirections[i] == eddieDir.TC) { //check if the eddie in question is TC. If it is, then it should be pushed forward
//                        System.out.println(11);

                        if(getEddie(i) == eddieDir.NA) // only update if direction is NA. aka eddie has not been pushed here
                            setDirection(i, WolframRules.output[rule][i]); //the update is performed

                        setDirection((i + 3) % 6, eddieDir.FC); //eddie is pushed forward
                        original.setDirection(i, eddieDir.NA);         //delete from original
//                        shouldBeChanged[(i + 3) % 6] = false; //the direction where the eddie has been pushed should not change any more
                    }
                    else { //do nothing
//                        System.out.println(12);
                        //idk if i should make the original NA here..no
                    }
                }
            }
        }else{ //if there are no rules that can be applied, push all forward (inside hexagon)
            for (int i = 0; i < 6; i++) {
                if(originalEdgeDirections[i] == eddieDir.TC) { //check if there is an eddie TC that should be pushed forward. We ignore FC eddies here. NA eddies do not change
//                    System.out.println(13);
                    setDirection((i + 3) % 6, eddieDir.FC); //eddie is pushed forward
                    original.setDirection(i, eddieDir.NA); //NA appears in its place             problem here?

//                    System.out.println(this);
//                    System.out.println(getEdge_nw().getDirection());
                }
            }

        }
    }

    public void reset(){
        setEdgeDirections(new Hexagon.eddieDir[]{Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA},true);
    }
    public void makeWall(int edge){
        switch(edge) {
            case 0: edge_n = null;
                break;
            case 1: edge_nw = null;
                break;
            case 2: edge_sw = null;
                break;
            case 3: edge_s = null;
                break;
            case 4: edge_se = null;
                break;
            case 5: edge_ne = null;
                break;
        }
    }

//    public void makeWall(){
//        setNull(3);
//    }

//    public void makeWall(boolean[] edges){
//        for(int i = 0; i < edges.length; i++){
//            if(edges[i]){
//                setNull(i);
//            }
//        }
//    }


}
