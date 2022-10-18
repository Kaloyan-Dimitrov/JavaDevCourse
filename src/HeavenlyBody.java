/*
    Only add/edit code where it is stated in the description.
*/

import java.util.HashSet;
import java.util.Set;

public abstract class HeavenlyBody {
    private final double orbitalPeriod;
    private final Key key;
    private final Set<HeavenlyBody> satellites;

    public HeavenlyBody(String name, double orbitalPeriod, BodyTypes bodyType) {
        this.orbitalPeriod = orbitalPeriod;
        this.satellites = new HashSet<>();
        this.key = new Key(name, bodyType);
    }

    public Key getKey() {
        return key;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public boolean addSatellite(HeavenlyBody satellite) {
        return this.satellites.add(satellite);
    }

    public Set<HeavenlyBody> getSatellites() {
        return new HashSet<>(this.satellites);
    }

    public static Key makeKey(String name, BodyTypes bodyType) {
        return new Key(name, bodyType);
    }

    @Override
    public String toString() {
        return String.format("%s: %s, %s", this.key.getName(), this.key.getBodyType(), this.orbitalPeriod);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeavenlyBody that = (HeavenlyBody) o;
        return this.key.equals(that.key);
    }

    @Override
    public final int hashCode() {
        return this.key.hashCode();
    }

    public enum BodyTypes {
        PLANET("planet"),
        DWARF_PLANET("dwarf"),
        MOON("moon");
        private String label;
        private BodyTypes(String label) {
            this.label = label;
        }
    }

    public static final class Key {
        private String name;
        private BodyTypes bodyType;

        private Key(String name, BodyTypes bodyType) {
            this.name = name;
            this.bodyType = bodyType;
        }

        public String getName() {
            return name;
        }

        public BodyTypes getBodyType() {
            return bodyType;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return name.equals(key.name) && bodyType.equals(key.bodyType);
        }

        @Override
        public final int hashCode() {
            return this.name.hashCode() + this.bodyType.hashCode() + 33;
        }

        @Override
        public String toString() {
            return String.format("%s: %s", this.name, this.bodyType);
        }
    }
}