public class WolframRules extends CollisionRules {
    //Edge n, Edge nw, Edge sw, Edge s, Edge se, Edge ne

     public static final Hexagon.eddieDir[][] input = new Hexagon.eddieDir[][]{ //5x6
        {Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA},//2
        {Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.TC},//3
        {Hexagon.eddieDir.TC, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA, Hexagon.eddieDir.TC},//3
        {Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA, Hexagon.eddieDir.TC},//3
        {Hexagon.eddieDir.TC, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA, Hexagon.eddieDir.TC, Hexagon.eddieDir.TC, Hexagon.eddieDir.NA}//4
    };

    public static final Hexagon.eddieDir[][] output = new Hexagon.eddieDir[][]{ //5x6
            {Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.FC, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA, Hexagon.eddieDir.FC},
            {Hexagon.eddieDir.FC, Hexagon.eddieDir.NA, Hexagon.eddieDir.FC, Hexagon.eddieDir.FC, Hexagon.eddieDir.NA, Hexagon.eddieDir.NA},
            {Hexagon.eddieDir.NA, Hexagon.eddieDir.FC, Hexagon.eddieDir.FC, Hexagon.eddieDir.NA, Hexagon.eddieDir.FC, Hexagon.eddieDir.NA},
            {Hexagon.eddieDir.NA, Hexagon.eddieDir.FC, Hexagon.eddieDir.NA, Hexagon.eddieDir.FC, Hexagon.eddieDir.NA, Hexagon.eddieDir.FC},
            {Hexagon.eddieDir.NA, Hexagon.eddieDir.FC, Hexagon.eddieDir.FC, Hexagon.eddieDir.NA, Hexagon.eddieDir.FC, Hexagon.eddieDir.FC}
    };

    public WolframRules(){
        super(input, output);
    }

}


