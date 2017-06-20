package com.core;

import java.util.Arrays;

/**
 * Created by agaspari on 6/14/2017.
 */
public class Encrypter {
    private final Integer[] primes = new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251};

    public Encrypter(){}

    public String encryptText(String plaintext){
        StringBuilder ciphertext = new StringBuilder();
        String[] words = plaintext.split(" ");
        for(int i = 0; i < words.length; i++){
            if(i + 1 == words.length){
                ciphertext.append(encryptWord(words[i]));
            }else{
                ciphertext.append(encryptWord(words[i] + " "));
            }
        }
        return ciphertext.toString();
    }

    private String encryptWord(String plaintext){
        StringBuilder ciphertext = new StringBuilder();
        String[] letters = plaintext.split("");

        for (String letter : letters) {
            if (letters.length == 0 || letter.getBytes().length == 0) {
                return "";
            }
            int tempByte = letter.getBytes()[0];
            String tempBinary = Integer.toBinaryString(tempByte);
            for (int j = 0; j < tempBinary.length(); j++) {
                if (tempBinary.substring(j, j + 1).equals("1")) {
                    ciphertext.append(primes[(int) (Math.random() * primes.length)]).append(" ");
                } else {
                    int tempNum;
                    do {
                        tempNum = (int)(Math.random() * 100);
                    } while (Arrays.asList(primes).contains(tempNum));
                    ciphertext.append(tempNum).append(" ");
                }
            }
            ciphertext.append("\n");
        }
        return ciphertext.toString();
    }

    public String decryptText(String ciphertext){
        StringBuilder plaintext = new StringBuilder();
        String[] letters = ciphertext.split("\n");
        for (String letter : letters) {
            plaintext.append(decryptLetter(letter));
        }
        return plaintext.toString();
    }

    private String decryptLetter(String ciphertext){
        String letter;
        String[] bytes = ciphertext.split(" ");

        StringBuilder binary = new StringBuilder();
        for (String aByte : bytes) {
            int myByte = -1;
            try{
                myByte = Integer.parseInt(aByte);
            }catch(NumberFormatException e){}

            if (Arrays.asList(primes).contains(myByte)) {
                binary.append("1");
            } else if (!Arrays.asList(primes).contains(myByte)) {
                binary.append("0");
            }
        }
        int decimalValue = Integer.parseInt(binary.toString(), 2);
        byte[] myByte = new byte[]{(byte)decimalValue};
        letter = new String(myByte);
        return letter;
    }
}