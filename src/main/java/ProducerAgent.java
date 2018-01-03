import org.jspace.RemoteSpace;
import org.jspace.SequentialSpace;
import org.jspace.Space;
import org.jspace.SpaceRepository;

import java.io.IOException;

class ProducerAgent implements Runnable {
    private final static String REMOTE_URI = "tcp://ec2-18-218-0-120.us-east-2.compute.amazonaws.com:9001/aspace2?keep";

    private Space space;
    private String name;
    private int delay;

    public static void main(String[] args) throws IOException {
        int delay = 1000;
        if (args.length > 0) {
            delay = Integer.parseInt(args[0]);
        }
        int N = 2;
        if (args.length > 1) {
            N = Integer.parseInt(args[1]);
        }
        for (int i = 0; i < N; i++) {
            new Thread(new ProducerAgent(String.valueOf(i), new RemoteSpace(REMOTE_URI), delay)).start();
        }

    }

    private ProducerAgent(String name, Space space, int delay) {
        System.out.println(name + " producing with delay of " + delay);
        this.space = space;
        this.name = name;
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while (true) {
                Thread.sleep(delay);
                space.put(new Person(String.valueOf(i), "TestName", "12345678-1234", 20),name,i);
                i++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
