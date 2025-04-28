package org.example;

import java.util.Map;

public class Huffman2 {

    public static void main(String[] args) {
        String text = "Capital J,lowercase j";
        Map<String, Object> result = HuffmanCoding.compress(text);
        String compressed = (String) result.get("compressed");
        Map<Character, String> codeMap = (Map<Character, String>) result.get("codeMap");

        System.out.println("Compressed: " + compressed);
        System.out.println("Code Map: " + codeMap);

        String decompressed = HuffmanCoding.decompress(compressed, codeMap);
        System.out.println("Decompressed: " + decompressed);
    }
}
