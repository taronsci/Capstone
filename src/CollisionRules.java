
public interface CollisionRules {
    Hexagon.eddieDir[] getOutput(int rule);
    Hexagon.eddieDir[] getInput(int rule);
    void enforceRule(Hexagon original, Hexagon copy);
}
