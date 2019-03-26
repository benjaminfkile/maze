package mazeGenerator;

public enum Direction {
    //     Flag      X  Y, Opposite flag
    UP    ((byte) 1, 0, 1, (byte) 4),
    RIGHT ((byte) 2, 1, 0, (byte) 8),
    DOWN  ((byte) 4, 0,-1, (byte) 1),
    LEFT  ((byte) 8,-1, 0, (byte) 2);

    public byte value;
    public byte opposite;

    public int x;
    public int y;

    Direction(byte value, int x, int y, byte opposite){
        this.value = value;
        this.x = x;
        this.y = y;
        this.opposite = opposite;
    }
}
