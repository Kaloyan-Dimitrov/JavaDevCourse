import java.util.ArrayList;
import java.util.List;

public class Monster implements ISaveable {
    private String name;
    private int hitPoints, strength;

    public Monster(String name, int hitPoints, int strength) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public String toString() {
        return String.format("Monster{name='%s', hitPoints=%d, strength=%d}", this.name, this.hitPoints, this.strength);
    }

    @Override
    public List<String> write() {
        String[] strings = new String[]{this.name, Integer.toString(this.hitPoints), Integer.toString(this.strength)};
        return new ArrayList<String>(List.of(strings));
    }

    @Override
    public void read(List<String> list) {
        if(list == null) return;
        if(list.size() == 0) return;
        this.name = list.get(0);
        this.hitPoints = Integer.parseInt(list.get(1));
        this.strength = Integer.parseInt(list.get(2));
    }
}
