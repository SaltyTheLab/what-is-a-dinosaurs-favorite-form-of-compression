package org.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class HuffmanNode {
    char ch;
    int freq;
    HuffmanNode left, right;

    HuffmanNode(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
    }
}

class HuffmanCoding {

    // Function to build frequency map
    public static Map<Character, Integer> buildFrequencyTable(String text) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }
        return freqMap;
    }

    // Function to build the Huffman Tree
    public static HuffmanNode buildTree(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffmanNode> heap = new PriorityQueue<>(Comparator.comparingInt(n -> n.freq));

        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            heap.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (heap.size() > 1) {
            HuffmanNode left = heap.poll();
            HuffmanNode right = heap.poll();
            HuffmanNode merged = new HuffmanNode('\0', left.freq + right.freq);
            merged.left = left;
            merged.right = right;
            heap.add(merged);
        }

        return heap.poll();
    }

    // Generate codes recursively
    public static void generateCodes(HuffmanNode node, String code, Map<Character, String> codeMap) {
        if (node == null)
            return;

        if (node.left == null && node.right == null) {
            codeMap.put(node.ch, code);
        }

        generateCodes(node.left, code + "0", codeMap);
        generateCodes(node.right, code + "1", codeMap);
    }

    // Compress function
    public static Map<String, Object> compress(String text) {
        Map<Character, Integer> freqMap = buildFrequencyTable(text);
        HuffmanNode root = buildTree(freqMap);

        Map<Character, String> codeMap = new HashMap<>();
        generateCodes(root, "", codeMap);

        StringBuilder encoded = new StringBuilder();
        for (char ch : text.toCharArray()) {
            encoded.append(codeMap.get(ch));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("compressed", encoded.toString());
        result.put("codeMap", codeMap);

        return result;
    }

    // Decompress function
    public static String decompress(String compressed, Map<Character, String> codeMap) {
        Map<String, Character> reverseMap = new HashMap<>();
        for (Map.Entry<Character, String> entry : codeMap.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }

        StringBuilder decoded = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (char bit : compressed.toCharArray()) {
            currentCode.append(bit);
            if (reverseMap.containsKey(currentCode.toString())) {
                decoded.append(reverseMap.get(currentCode.toString()));
                currentCode.setLength(0);
            }
        }

        return decoded.toString();
    }
}
