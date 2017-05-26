import com.sun.jna.NativeLibrary;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sergiy on 5/24/2017.
 */
public class Main {
    public static AnimationTimer at;
    public static Timer t;
    public static void main(String[] args) {
        NativeLibrary.addSearchPath("libvlc", "D:\\Program Files\\VideoLAN\\VLC");
        NativeLibrary.addSearchPath("libvlccore", "D:\\Program Files\\VideoLAN\\VLC");
        args = new String[]{"rtsp://admin:skymallcamera5@46.219.14.65:30001/h264/ch01/sub/av_stream"};

        final String media = args[0];
        final String options = formatRtspStream();
        System.out.println("Streaming '" + media + "' to '" + options + "'");



        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(args);
        final HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        t= new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.print("a");
                mediaPlayer.pause();
                if (mediaPlayer != null && mediaPlayer.getSnapshot() != null) {
                    File outputfile = new File("1" + "." + "jpg");
                    try {
                        ImageIO.write(mediaPlayer.getSnapshot(), "jpg", outputfile);
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                }
                mediaPlayer.start();
            }
        });

        t.start();

        mediaPlayer.playMedia(media,
                options,
                ":no-sout-rtp-sap",
                ":no-sout-standard-sap",
                ":sout-all",
                ":sout-keep"
        );



    }

    private static String formatRtspStream() {
        StringBuilder sb = new StringBuilder(60);
        sb.append("rtsp://admin:skymallcamera5@46.219.14.65:30001/h264/ch01/sub/av_stream");

        return sb.toString();
    }
}
