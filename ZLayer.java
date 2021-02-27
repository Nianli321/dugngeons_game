package unsw.dungeon;

public enum ZLayer {
    FLOOR(4),
    ITEMS(3),
    OBSTACLES(2),
    MOVEABLES(1),
    PLAYER(0);

    private final int z;

    private ZLayer(int z) {
        this.z = z;
    }

    public int getZIndex() {
        return z;
    }
}