public class ConcurrencyChallenge8 {
    public static void main(String[] args) {
        Tutor tutor = new Tutor();
        Student1 student = new Student1(tutor);
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

class Tutor {
    private Student1 student;

    public synchronized void setStudent(Student1 student) {
        this.student = student;
    }

    public synchronized void studyTime() {
        System.out.println("Tutor has arrived");
        try {
            // wait for student to arrive and hand in assignment
            System.out.println("Tutor sleeps");
            Thread.sleep(300);
        }
        catch (InterruptedException e) {

        }
        student.startStudy();
        System.out.println("Tutor is studying with student");
    }

    public synchronized void getProgressReport() {
        // get progress report
        System.out.println("Tutor gave progress report");
    }
}

class Student1 {

    private Tutor tutor;

    Student1(Tutor tutor) {
        this.tutor = tutor;
    }

    public synchronized void startStudy() {
        // study
        System.out.println("Student is studying");
    }

    public synchronized void handInAssignment() {
        System.out.println("Student tries to hand in assignment.");
        tutor.getProgressReport();
        System.out.println("Student handed in assignment");
    }
}

