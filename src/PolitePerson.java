public class PolitePerson {
    public static void main(String[] args) {
        PolitePersonClass jane = new PolitePersonClass("Jane");
        PolitePersonClass john = new PolitePersonClass("John");
        new Thread(new Runnable () {
            public void run() {
                 jane.sayHello(john);
                }
        }).start();
        new Thread(new Runnable () {
            public void run() {
                john.sayHello(jane);
                }
        }).start();
    }

    static class PolitePersonClass {
        private final String name;

        public PolitePersonClass(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public synchronized void sayHello(PolitePersonClass person) {
            System.out.format("%s: %s" + " has said hello to me!%n", this.name, person.getName());
            person.sayHelloBack(this);
        }

        public synchronized void sayHelloBack(PolitePersonClass person) {
            System.out.format("%s: %s" + " has said hello back to me!%n", this.name, person.getName());
        }
    }
}
