package vala.com;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        HashMap<Character, Character> hashmap = new HashMap<>();
        HashMap<Character, Character> decryptionMap = new HashMap<>();

        int decryptOrEncrypt = scanner.nextInt();
        scanner.nextLine();

        if (decryptOrEncrypt == 1) {
//              Takes a file and returns encrypted version of it with two key files for decrypting

            System.out.println("Introduce la ruta de archivo:");
            String path = scanner.nextLine();

            getTextFileData(path, hashmap);     // Gets the data from the inputted file

            generateKeySet(hashmap);    // Generates keys and values for encryption/decryption

            outputKeys(hashmap);    // Generates two files with keys and values for encryption/decryption

            generateEncryptedFile(path, hashmap);   // Generates encrypted file

        } else {
//              Decrypts the file

            System.out.println("Introduce la ruta de las llaves:");
            String pathOfKeys = scanner.nextLine();

            System.out.println("Introduce la ruta de los valores:");
            String pathOfValues = scanner.nextLine();

            getDecryptKeys(pathOfKeys, pathOfValues, decryptionMap);


            System.out.println("Introduce la ruta de archivo encriptado:");
            String pathOfEncriptedFile = scanner.nextLine();

            decryptFile(pathOfEncriptedFile, decryptionMap);


        }




    }

    private static void generateKeySet(HashMap<Character, Character> hashmap) {

        ArrayList<Character> allChars = new ArrayList<>(hashmap.keySet());

        Collections.shuffle(allChars);

        int count = 0;
        for (HashMap.Entry<Character, Character> entry : hashmap.entrySet()) {
            hashmap.put(entry.getKey(), allChars.get(count));
            count++;
        }
    }

    private static void getTextFileData(String path, HashMap<Character, Character> hashmap) {
        try {
            InputStream input = new FileInputStream(path);
            byte[] data = input.readAllBytes();
            for (byte el : data) {
                char element = (char)el;

                hashmap.put(element, element);

            }
        } catch (IOException e) {
            System.out.println("IO error file input");
        }
    }

    private static void decryptFile(String pathOfDecriptedFile, HashMap<Character, Character> hashmap) {
        try {
            InputStream input = new FileInputStream(pathOfDecriptedFile);
            byte[] data = input.readAllBytes();

            PrintStream output = new PrintStream(pathOfDecriptedFile);

            for (byte el : data) {
                output.print(hashmap.get((char)el));
            }
        } catch (IOException e) {
            System.out.println("IO error file input");
        }
    }

    private static void getDecryptKeys(String pathOfKeys, String pathOfValues, HashMap<Character, Character> decryptionMap) {
        try {
            InputStream inputKey = new FileInputStream(pathOfKeys);
            byte[] data = inputKey.readAllBytes();

            InputStream inputValue = new FileInputStream(pathOfValues);
            byte[] data2 = inputValue.readAllBytes();

            int count2 = 0;
            for (byte el : data) {
                char element = (char) el;
                decryptionMap.put((char) data2[count2], element);
                count2++;
            }


        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    private static void generateEncryptedFile(String path, HashMap<Character, Character> hashmap) {
        try {
            File encryptedFile = new File("encrypted_file.txt");
            encryptedFile.createNewFile();
            PrintStream file = new PrintStream(encryptedFile);

            InputStream input = new FileInputStream(path);
            byte[] data = input.readAllBytes();
            for (byte el : data) {
                char element = (char)el;

                char value = hashmap.get(element);
                file.print(value);
            }

//
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    private static void outputKeys(HashMap<Character, Character> hashmap) {
        try {
            File keyFile = new File("key.txt");
            keyFile.createNewFile();
            PrintStream file1 = new PrintStream(keyFile);

            File valueFile = new File("value.txt");
            valueFile.createNewFile();
            PrintStream file2 = new PrintStream(valueFile);


            for (char key: hashmap.keySet()) {
                file1.print(key);
                file2.print(hashmap.get(key));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
}
