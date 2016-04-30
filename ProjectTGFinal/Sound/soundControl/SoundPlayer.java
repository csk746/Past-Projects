package soundControl;
import java.io.*;
import sun.audio.*;
 
public class SoundPlayer{
  
	public SoundPlayer(String id) throws Exception
  {
    // open the sound file as a Java input stream
	 System.out.println("너");
    String gongFile = ("/Users/igeonhui/Documents/workspace/ProjectTGIntergrate/"+id+".wav");
    InputStream in = new FileInputStream(gongFile);
    System.out.println("왜");
 
    // create an audiostream from the inputstream
    AudioStream audioStream = new AudioStream(in);
    System.out.println("음악안틈");
 
    // play the audio clip with the audioplayer class
    AudioPlayer.player.start(audioStream);
  }
}