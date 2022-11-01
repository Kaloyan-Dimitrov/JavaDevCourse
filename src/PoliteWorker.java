public class PoliteWorker {
    private String name;
    private boolean active;

    public static void main(String[] args) {
        final PoliteWorker worker1 = new PoliteWorker("Worker1", true);
        final PoliteWorker worker2 = new PoliteWorker("Worker2", true);

        final SharedResource sharedResource = new SharedResource(worker1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                worker1.work(sharedResource, worker2);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                worker2.work(sharedResource, worker1);
            }
        }).start();
    }

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
            if (sharedResource.getOwner() != this) {
                try {
                    wait(10);
                } catch (InterruptedException e) {
                }
                continue;
            }

            if (otherWorker.isActive()) {
                System.out.println(getName() + ": give the resource to the worker " + otherWorker.getName());
                sharedResource.setOwner(otherWorker);
                continue;
            }

            System.out.println(getName() + ": working on the common resource");
            active = false;
            sharedResource.setOwner(otherWorker);
        }
    }
}

class SharedResource {
    private PoliteWorker owner;

    public SharedResource(PoliteWorker d) {
        owner = d;
    }

    public synchronized void setOwner(PoliteWorker d) {
        owner = d;
    }

    public PoliteWorker getOwner() {
        return owner;
    }
}