import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;
import org.jspace.Space;

import java.io.IOException;
import java.util.List;

class ConsumerAgent implements Runnable {
    private final static String REMOTE_URI = "tcp://ec2-18-218-0-120.us-east-2.compute.amazonaws.com:9001/aspace2?keep";

    private Space space;
    private int me;

    public static void main(String[] args) throws IOException {
        int n = 1;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
        for (int i = 0; i < n; i++) {
            new Thread(new ConsumerAgent(new RemoteSpace(REMOTE_URI), i)).start();
        }
    }

    ConsumerAgent(Space space, int me) {
        this.space = space;
        this.me = me;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(2000);
                List<Object[]> data = space.getAll(new FormalField(Person.class),new FormalField(String.class),new FormalField(Integer.class));
                System.out.print(this.me + " CONSUMED ");
                data.forEach(o -> System.out.print(o[0] + " " + o[1] + " " + o[2] + " | "));
                System.out.println("\n" + data.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
