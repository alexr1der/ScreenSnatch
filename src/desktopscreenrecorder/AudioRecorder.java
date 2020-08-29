/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopscreenrecorder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import java.nio.file.*;

public class AudioRecorder extends Thread {

    private static TargetDataLine mic;

    public void AudioRecorder() {

//        initRecording();
    }

    private void initRecording() {

        System.out.println("begin sound test...");

        try {

            //define audio format
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

            mic = (TargetDataLine) AudioSystem.getLine(info);
            mic.open();

            System.out.println("recording initialized...");
          

        } catch (LineUnavailableException ex) {

            ex.printStackTrace();

        }

    }

    @Override
    public  void run() {
        
        initRecording();
        startRecording();

    }

    /**
     * starts recording audio when 'Start Recording' is pressed on MainScreenRecorderFrame
     */
    private void startRecording() {

        // have to try/catch in the case that AudioSystem isn't able to write into file
        try {
            mic.start();

            AudioInputStream audioInputStream = new AudioInputStream(mic);
            
            // creates file to save mic recording
            File f = new File("audio_output.wav");
            
            // writes audio from mic into the file
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, f);
            
            // print out once done for confirmation
            System.out.println("done writing to file");
            
        } catch (IOException io) {
            io.getStackTrace();
        }

    }
    
    
    /**
     * stops the audio recording when 'Stop Recording" is pressed on MainScreenRecordeerFrame
     */
    public void stopRecording() {
        // stop mic from recording
        mic.stop();
        mic.close();
        
        // print out once done for confirmation
        System.out.println("Recording ended");

    }
    
}

 




