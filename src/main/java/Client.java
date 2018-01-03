import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;
import org.jspace.Space;

import java.io.IOException;

class Client   {
    private final static String REMOTE_URI = "tcp://ec2-18-218-0-120.us-east-2.compute.amazonaws.com:9001/aspace?keep";

    public static void main(String[] args) throws IOException, InterruptedException {
        Space space = new RemoteSpace(REMOTE_URI);
        space.put("create", "test");
        Object[] room = space.get(new ActualField("roomUID"), new FormalField(String.class), new FormalField(String.class));
        System.out.println(room[1] + " " + room[2]);
        Space roomSpace = new RemoteSpace((String) room[2]);
        roomSpace.put("test");
    }

}
