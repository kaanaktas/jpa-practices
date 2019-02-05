package com.sps.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kaktas on 01-Feb-19
 */
public class DataParser {

    public static void main(String[] args) throws Exception {
        CsvParser();
    }

     public static List<DataWrapper> CsvParser() throws Exception{
         Path path = Paths.get("src/main/resources/MOCK_DATA.csv");
         List<String> lines = Files.readAllLines(path);

         List<DataWrapper> result = lines.
                 stream()
                 .filter(s -> !s.startsWith("#"))
                 .map(s -> {
                     String[] arr = s.split(",");
                     try{
                         return new DataWrapper(arr[0], arr[1], arr[2]
                         ,arr[3], arr[4], arr[5], arr[6], arr[7], arr[8]
                                 ,arr[9], arr[10], arr[11], arr[12]);
                     } catch (Exception e){
                         e.printStackTrace();
                         return null;
                     }
                 })
                 .filter(t -> t != null)
                 .collect(Collectors.toList());

         return result;
     }
}
