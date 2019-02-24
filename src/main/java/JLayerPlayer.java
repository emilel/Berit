import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class JLayerPlayer implements Mp3Player {
    public void play(String fileName) {
        try {
            System.out.println("Playing mp3 file");
            Player playMP3 = new Player(new FileInputStream(fileName));
            playMP3.play();
        } catch (JavaLayerException e) {
            System.out.println("Error playing file");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Error finding file");
            e.printStackTrace();
        }
    }

    public String toString() {
        return "JLayer";
    }
}
