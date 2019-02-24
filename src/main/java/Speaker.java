import com.google.cloud.texttospeech.v1.SsmlVoiceGender;

import java.io.File;
import java.io.IOException;

public class Speaker {
    private VoiceSynthesizer vs;
    private Mp3Player player;

    public Speaker(String language, SsmlVoiceGender gender, Mp3Player player) {
        this.vs = new VoiceSynthesizer(language, gender);
        this.player = player;

    }

    public void speak(String text) {
        System.out.println("Request to say: " + format(text));
        String path = getFileName(text);
        File file = new File(path);
        if (file.exists()) {
            System.out.println("File existed");
            player.play(file.toString());
        } else {
            try {
                System.out.println("File did not exist");
                file = vs.synthesize(format(text));
                player.play(file.toString());
            } catch (IOException e) {
                System.out.println("Something wrong happened while synthesizing speech");
                e.printStackTrace();
            }
        }
    }

    private String format(String text) {
        return text.replace('_', ' ');
    }

    private String getFileName(String text) {
        return "speech/" + text + ".mp3";
    }
}
