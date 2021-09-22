package cz.uhk.fim.pro2.playlist.utils;

import cz.uhk.fim.pro2.playlist.model.Song;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;

public class CSVParser {
    public static void generateCsv(String filename, List<Song> objects) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        StringBuilder builder = new StringBuilder();
        for (Song song : objects) {
            builder.append(String.format("%d;%s;%s;%s;%n",
                    song.getOrder(), song.getName(), song.getInterpret(), song
                            .getTime()
                    )
            );
        }

        writeToFile(builder.toString().getBytes(), filename, "csv");
    }

    public static void writeToFile(byte[] content, String filename, String extension) {
        try {
            Files.write(Paths.get(String.format("%s.%s", filename, extension)), content);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}