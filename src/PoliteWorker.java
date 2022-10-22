public class PoliteWorker {
    private String name;
    private boolean active;

    public PoliteWorker(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public synchronized void work(SharedResource sharedResource, PoliteWorker otherWorker) {
        while (active) {
        }
    }
}

class SharedResource {

}