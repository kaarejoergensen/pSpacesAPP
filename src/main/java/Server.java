import org.jspace.*;

import java.util.Random;

public class Server {
    private final static String ALPHABET = "ABCDEFZHIKLMNOPQRSTVX";
    private final static String URI = "tcp://ec2-18-218-0-120.us-east-2.compute.amazonaws.com:9001/";
    private final static String GATE_URI = URI + "?keep";

    public static void main(String[] args) throws InterruptedException {
        SpaceRepository repository = new SpaceRepository();
        repository.addGate(GATE_URI);
        repository.add("aspace", new SequentialSpace());
        Space space = repository.get("aspace");
        System.out.println("Server started!");
        while (true) {
            Object[] create = space.get(new ActualField("create"), new FormalField(String.class));
            String UID = "";
            do {
                Random r = new Random();
                for (int i = 0; i < 8; i++) {
                    UID += ALPHABET.charAt(r.nextInt(ALPHABET.length()));
                }
            } while (repository.get("UID") != null);
            repository.add(UID, new SequentialSpace());
            space.put("roomUID", create[1], URI + UID + "?keep");
            System.out.println("New room with UID " + UID + " created!");
        }
    }
}