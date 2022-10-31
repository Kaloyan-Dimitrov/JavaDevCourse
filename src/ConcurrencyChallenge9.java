public class ConcurrencyChallenge9 {
    public static void main(String[] args) {
        final NewTutor tutor = new NewTutor();
        final NewStudent student = new NewStudent(tutor);
        tutor.setStudent(student);

        Thread tutorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                tutor.studyTime();
            }
        });

        Thread studentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                student.handInAssignment();
            }
        });

        tutorThread.start();
        studentThread.start();
    }
}

class NewTutor {
    private NewStudent student;

    public void setStudent(NewStudent student) {
        this.student = student;
    }

    public void studyTime() {

        synchronized (this) {
            System.out.println("Tutor has arrived: (" + Thread.currentThread().getName()
                                + " gets tutor lock)");
            synchronized (student) {
                System.out.println("Waiting for student: (" + Thread.currentThread().getName()
                        + " gets student lock)");
                try {
                    // wait for student to arrive
                    System.out.println(Thread.currentThread().getName()
                            + " releases student and tutor lock and waits");
                    wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Tutor: Student starting to study");
                student.startStudy();
                System.out.println("Tutor is studying with student");
            }
        }
    }

    public void getProgressReport() {
        // get progress report
        System.out.println("Tutor gave progress report (" + Thread.currentThread().getName() + ")");
    }
}

class NewStudent {

    private NewTutor tutor;

    NewStudent(NewTutor tutor) {
        this.tutor = tutor;
    }

    public void startStudy() {
        // study
        System.out.println("Student is studying");
    }

    public void handInAssignment() {
        synchronized (tutor) {
            System.out.println("Requesting tutor progress report: (" + Thread.currentThread().getName()
                    + " gets tutor lock)");
            synchronized (this) {
                System.out.println("Student handed in assignment: (" + Thread.currentThread().getName()
                        + " gets student lock)");
                tutor.notifyAll();
            }
        }
    }
}

