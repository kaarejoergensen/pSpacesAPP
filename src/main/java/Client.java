import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;
import org.jspace.Space;

import java.io.IOException;

class Client   {
    private final static String REMOTE_URI = "tcp://127.0.0.1:9001/";

    public static void main(String[] args) throws IOException, InterruptedException {
        Space space = new RemoteSpace(REMOTE_URI + "aspace?keep");
        space.put("create", "test");
        Object[] room = space.get(new ActualField("createResult"), new FormalField(String.class), new ActualField("test"));
        System.out.println(room[1] + " " + room[2]);
        Space roomSpace = new RemoteSpace(REMOTE_URI + room[2] + "?keep");
        roomSpace.put("test");
        space.put("exists", room[1], "test");
        Object[] exists = space.get(new ActualField("existsResult"), new FormalField(Boolean.class), new ActualField("test"));
        System.out.println(exists[1]);
    }

}
