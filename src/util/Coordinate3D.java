package util;

public class Coordinate3D {
    public int x;
    public int y;
    public int z;
    
    public Coordinate3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Coordinate3D(String[] coordinates) {
        this.x = Integer.parseInt(coordinates[0]);
        this.y = Integer.parseInt(coordinates[1]);
        this.z = Integer.parseInt(coordinates[2]);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate3D other = (Coordinate3D) obj;
        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(x) + Integer.hashCode(y)  + Integer.hashCode(z);
    }

    @Override
    public String toString() {
        return "Coordinate(" + x + ", " + y + ", " + z + ")";
    }
}
