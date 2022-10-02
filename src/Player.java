import java.util.ArrayList;
import java.util.List;

public class Player implements ISaveable {
    private String name, weapon;
    private int hitPoints, strength;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return String.format("Player{name='%s', hitPoints=%d, strength=%d, weapon='%s'}", this.name, this.hitPoints, this.strength, this.weapon);
    }

    public Player(String name, int hitPoints, int strength) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.strength = strength;
        this.weapon = "Sword";
    }

    @Override
    public List<String> write() {
        String[] strings = new String[]{this.name, Integer.toString(this.hitPoints), Integer.toString(this.strength), this.weapon};
        return new ArrayList<String>(List.of(strings));
    }

    @Override
    public void read(List<String> list) {
        if(list == null) return;
        if(list.size() == 0) return;
        this.name = list.get(0);
        this.hitPoints = Integer.parseInt(list.get(1));
        this.strength = Integer.parseInt(list.get(2));
        this.weapon = list.get(3);
    }


}
