import com.google.cloud.texttospeech.v1.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class VoiceSynthesizer {
    private String language;
    private SsmlVoiceGender gender;

    public VoiceSynthesizer(String language, SsmlVoiceGender gender) {
        this.language = language;
        this.gender = gender;
    }

    public File synthesize(String text) throws IOException {
        System.out.println("Creating voice synthesize request");
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode(language)
                    .setSsmlGender(gender)
                    .build();

            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .setSampleRateHertz(44100)
                    .build();

            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice,
                    audioConfig);

            byte[] data = response.getAudioContent().toByteArray();

            String fileName = "speech/" + text.replace(' ', '_') + ".mp3";
            File file = new File(fileName);

            try (OutputStream out = new FileOutputStream(file)) {
                out.write(data);
            }

            return file;
        }
    }
}
