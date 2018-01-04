import org.jspace.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Server {
    private final static String URI = "tcp://127.0.0.1:9001/";
    private final static String GATE_URI = URI + "?keep";

    public static void main(String[] args) throws InterruptedException {
        SpaceRepository repository = new SpaceRepository();
        repository.addGate(GATE_URI);
        repository.add("aspace", new SequentialSpace());
        Space space = repository.get("aspace");
        space.put("existingrooms", new ArrayList<>());
        System.out.println("Server started!");

        new Thread(new Create(repository, space)).start();
        new Thread(new Exists(space)).start();
        new Thread(new Rooms(space)).start();
    }
}

class Create implements Runnable {
    private final static String ALPHABET = "ABCDEFZHIKLMNOPQRSTVX";

    private SpaceRepository repository;
    private Space space;

    public Create(SpaceRepository repository, Space space) {
        this.repository = repository;
        this.space = space;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object[] create = space.get(new ActualField("create"), new FormalField(String.class));
                StringBuilder UID = new StringBuilder();
                do {
                    Random r = new Random();
                    for (int i = 0; i < 8; i++) {
                        UID.append(ALPHABET.charAt(r.nextInt(ALPHABET.length())));
                    }
                } while (repository.get("UID") != null);
                repository.add(UID.toString(), new SequentialSpace());
                space.put("createResult", UID.toString(), create[1]);
                Object[] rooms = space.get(new ActualField("existingrooms"), new FormalField(Object.class));
                List<String>  rooms1 = (ArrayList<String>) rooms[1];
                rooms1.add(UID.toString());
                space.put("existingrooms", rooms1);
                System.out.println("New room with UID " + UID + " created!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Exists implements Runnable {
    private Space space;

    public Exists(Space space) {
        this.space = space;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object[] exists = space.get(new ActualField("exists"), new FormalField(String.class), new FormalField(String.class));
                System.out.println("Checking if room " + exists[1] + " exists!");
                Object[] checkRooms = space.query(new ActualField("existingrooms"), new FormalField(Object.class));
                List<String> rooms = (List<String>) checkRooms[1];
                Boolean doesExist = rooms.stream().anyMatch(r -> r.equals(exists[1]));
                space.put("existsResult", doesExist, exists[2]);
                System.out.println("Room " + exists[1] + " exists: " + doesExist);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Rooms implements Runnable {
    private Space space;

    public Rooms(Space space) {
        this.space = space;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object[] rooms = space.get(new ActualField("rooms"), new FormalField(String.class));
                space.put("roomsResult", space.get(new ActualField("existingrooms"), new FormalField(Object.class))[1], rooms[1]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}