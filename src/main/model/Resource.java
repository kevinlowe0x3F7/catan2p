package src.main.model;

/**
 * Enum indicating the different type of resources
 */
public enum Resource {
    BRICK,
    SHEEP,
    ORE,
    WHEAT,
    WOOD;

    @Override
    public String toString() {
        switch(this) {
            case BRICK: return "brick";
            case SHEEP: return "sheep";
            case ORE:   return "ore";
            case WHEAT: return "wheat";
            case WOOD:  return "wood";
            default:    return "";
        }
    }
}

