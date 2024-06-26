package com.ldt.BeatHive.util;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.images.Artwork;

import java.io.*;

public class AudioFileCoverUtils {

  public static final String[] FALLBACKS = {
    "cover.jpg", "album.jpg", "folder.jpg", "cover.png", "album.png", "folder.png"
  };

  public static InputStream fallback(String path) throws FileNotFoundException {
    // Method 1: use embedded high resolution album art if there is any
    try {
      MP3File mp3File = new MP3File(path);
      if (mp3File.hasID3v2Tag()) {
        Artwork art = mp3File.getTag().getFirstArtwork();
        if (art != null) {
          byte[] imageData = art.getBinaryData();
          return new ByteArrayInputStream(imageData);
        }
      }
      // If there are any exceptions, we ignore them and continue to the other fallback method
    } catch (ReadOnlyFileException | InvalidAudioFrameException | TagException | IOException ignored) {
    }

    // Method 2: look for album art in external files
    final File parent = new File(path).getParentFile();
    for (String fallback : FALLBACKS) {
      File cover = new File(parent, fallback);
      if (cover.exists()) {
        return new FileInputStream(cover);
      }
    }
    return null;
  }
}