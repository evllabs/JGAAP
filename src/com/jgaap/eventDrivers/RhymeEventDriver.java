package com.jgaap.eventDrivers;
import com.jgaap.generics.EventDriver;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.*;
import java.util.Arrays;
import java.text.Normalizer;
import java.text.Normalizer.Form;


class HashMapPair {
    private HashMap<String, String> map1;
    private HashMap<String, String> map2;

    public HashMapPair(HashMap<String, String> map1, HashMap<String, String> map2) {
        this.map1 = map1;
        this.map2 = map2;
    }

    public HashMap<String, String> getMap1() {
        return map1;
    }

    public HashMap<String, String> getMap2() {
        return map2;
    }
}


public class RhymeEventDriver extends EventDriver {
    @Override
	public String displayName() {
		return "Rhymes";
	}

	@Override
	public String tooltipText() {
		return "Words/Phrases that Rhyme";
	}

	@Override
	public String longDescription() {
		return "Words/Phrases that Rhyme";
	}


	@Override
	public boolean showInGUI() {
		return true;
	}


    public static String calculateFileHash(String filePath, String algorithm) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        try (InputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        StringBuilder hashString = new StringBuilder();
        for (byte b : digest.digest()) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }

    public static ArrayList<ArrayList<String>> find_events_rhyming_events(char[] text){

        String saved_phonetic_hash = "";
        String saved_language_hash = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("com/jgaap/resources/Phonetic_data/Phonetics_dict_hashmap.hash"))) {
            saved_phonetic_hash = reader.readLine();
        }
        catch(Exception e){
            System.out.println("Couldn't load in saved Phonetics Hash file");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("com/jgaap/resources/Phonetic_data/Language_dict_hashmap.hash"))) {
            saved_language_hash = reader.readLine();
        }
        catch(Exception e){
            System.out.println("Couldn't load in saved Phonetics Hash file");
        }

        String hash3 = "";
        String hash4 = "";
        try{
            hash3 = calculateFileHash("com/jgaap/resources/Phonetic_data/Phonetics_dict.ser", "SHA-256");
            hash4 = calculateFileHash("com/jgaap/resources/Phonetic_data/Language_dict.ser", "SHA-256");
            
        }
        catch(Exception e){
            System.out.println("Hashes weren't calculated correctly");
            e.printStackTrace();
        }
        if(!saved_phonetic_hash.equals(hash3) || !saved_language_hash.equals(hash4)){
            System.err.println("Data files have been tampered with");
            return null;
        }
        HashMap<String, String> phonetics_HashMap = new HashMap<>();
        HashMap<String ,String> language_HashMap = new HashMap<>();

        try (FileInputStream fileIn = new FileInputStream("com/jgaap/resources/Phonetic_data/Phonetics_dict.ser");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)){
                phonetics_HashMap =  (HashMap<String, String>) objectIn.readObject();
             }
        catch(Exception e){
            System.out.println("Cannot convert phonetics data to hashmap");
        }
        try (FileInputStream fileIn = new FileInputStream("com/jgaap/resources/Phonetic_data/Language_dict.ser");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)){
                language_HashMap =  (HashMap<String, String>) objectIn.readObject();
             }
        catch(Exception e){
            System.out.println("Cannot convert phonetics data to hashmap");
        }
        
    



        String txt = new String(text);
        //System.out.println(txt);
        //split the text by stripping punctuation of any kind or spaces
        String[] words_or_chinese_phrases = txt.split("[ ,\\.\\;\\:\\(\\)\\[\\]\\{\\}\\<\\>\\uFF0C\\u3002\\?\\!\\uFF01\\uFF1F\\u060C\\u061F]+");
        //System.out.println(Arrays.toString(words_or_chinese_phrases));
        //check if the text is Chinese
        boolean isChinese = false;
        boolean isArabic = false;
        boolean isGreek = false;
        boolean isRussian = false;
        boolean isEnglish = false;
        for(String word_or_phrase : words_or_chinese_phrases){
            String final_element = word_or_phrase.substring(word_or_phrase.length()-1);
            if(phonetics_HashMap.containsKey(final_element) && language_HashMap.get(final_element).equals("Chinese")){
                isChinese = true;
                break;
            }
            else if(phonetics_HashMap.containsKey(word_or_phrase) && language_HashMap.get(word_or_phrase).equals("English")){
                isEnglish = true;
                break;
            }
            else if(phonetics_HashMap.containsKey(word_or_phrase) && language_HashMap.get(word_or_phrase).equals("Greek")){
                isGreek = true;
                break;
            }
            else if(phonetics_HashMap.containsKey(word_or_phrase) && language_HashMap.get(word_or_phrase).equals("Russian")){
                isRussian = true;
                break;
            }
            else if(phonetics_HashMap.containsKey(word_or_phrase) && language_HashMap.get(word_or_phrase).equals("Arabic")){
                isArabic = true;
                break;
            }
        }
        HashMap<String, Integer>Syllables_to_Event_Array_Position = new HashMap<>();
        ArrayList<ArrayList<String>>Event_Array_Position = new ArrayList<>();
        Integer next_array_position = 0;
        if(isChinese){
            for(String chinese_phrase : words_or_chinese_phrases){
                String final_character = chinese_phrase.substring(chinese_phrase.length()-1);
                if(!phonetics_HashMap.containsKey(final_character)){
                    ArrayList<String>added_in = new ArrayList<>();
                    added_in.add(final_character);
                    Event_Array_Position.add(added_in);
                    next_array_position += 1;
                }
                else{
                    String pinyin_translation = phonetics_HashMap.get(final_character);
                    String[] pinyin_syllables = pinyin_translation.split("\\s+");
                    String final_syllable = pinyin_syllables[pinyin_syllables.length -1];
                    String final_syllable_with_removed_toner = final_syllable.replaceAll("[0-9]", "");
                    int first_vowel = 0;
                    HashSet<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'o' , 'u' , 'i'));
                    for(char c : final_syllable_with_removed_toner.toCharArray()){
                        if(vowels.contains(c)){
                            break;
                        }
                        else{
                            first_vowel += 1;
                        }
                    }
                    String final_syllable_rhyming_section = final_syllable_with_removed_toner.substring(first_vowel);
                    if(Syllables_to_Event_Array_Position.containsKey(final_syllable_rhyming_section)){
                        int position_in_Event_List = Syllables_to_Event_Array_Position.get(final_syllable_rhyming_section);
                        Event_Array_Position.get(position_in_Event_List).add(chinese_phrase);
                    }
                    else{
                        Syllables_to_Event_Array_Position.put(final_syllable_rhyming_section, next_array_position);
                        Event_Array_Position.get(next_array_position).add(chinese_phrase);
                        next_array_position += 1;
                    }
                }
            }
        }
        else if(isArabic){
            for(String arabic_phrase : words_or_chinese_phrases){
                if(!phonetics_HashMap.containsKey(arabic_phrase)){
                    ArrayList<String>added_in = new ArrayList<>();
                    added_in.add(arabic_phrase);
                    Event_Array_Position.add(added_in);
                    next_array_position += 1;
                }
                else{
                    String phonetic_translation = phonetics_HashMap.get(arabic_phrase);
                    HashSet<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'o' , 'u' , 'i'));
                    char[] normalized_translation = Normalizer.normalize(phonetic_translation, Form.NFD).toCharArray();
                    int start_position =0;
                    for(char c: normalized_translation){
                        if(!vowels.contains(c)){
                            start_position += 1;
                        }
                        else{
                            break;
                        }
                    }
                    String last_syllable = normalized_translation.toString().substring(start_position);
                    if(Syllables_to_Event_Array_Position.containsKey(last_syllable)){
                        int position_in_Event_List = Syllables_to_Event_Array_Position.get(last_syllable);
                        Event_Array_Position.get(position_in_Event_List).add(arabic_phrase);
                    }
                    else{
                        Syllables_to_Event_Array_Position.put(last_syllable, next_array_position);
                        Event_Array_Position.add(new ArrayList<>());
                        Event_Array_Position.get(next_array_position).add(arabic_phrase);
                        next_array_position += 1;
                    }
                    
                }
            }
        }
        else if(isGreek){
            for(String greek_word : words_or_chinese_phrases){
                if(!phonetics_HashMap.containsKey(greek_word)){
                    ArrayList<String>added_in = new ArrayList<>();
                    added_in.add(greek_word);
                    Event_Array_Position.add(added_in);
                    next_array_position += 1;
                }
                else{
                    String phonetic_translation = phonetics_HashMap.get(greek_word);
                    String [] syllables = phonetic_translation.split("\\s+");
                    String last_syllable = syllables[syllables.length-1];
                    if(Syllables_to_Event_Array_Position.containsKey(last_syllable)){
                        int position_in_Event_List = Syllables_to_Event_Array_Position.get(last_syllable);
                        Event_Array_Position.get(position_in_Event_List).add(greek_word);
                    }
                    else{
                        Syllables_to_Event_Array_Position.put(last_syllable, next_array_position);
                        Event_Array_Position.add(new ArrayList<>());
                        Event_Array_Position.get(next_array_position).add(greek_word);
                        next_array_position += 1;
                    }
                    
                }
            }
        }
        else if(isRussian){
            for(String russian_word : words_or_chinese_phrases){
                if(!phonetics_HashMap.containsKey(russian_word)){
                    ArrayList<String>added_in = new ArrayList<>();
                    added_in.add(russian_word);
                    Event_Array_Position.add(added_in);
                    next_array_position += 1;
                }
                else{
                    String phonetic_translation = phonetics_HashMap.get(russian_word);
                    String [] syllables = phonetic_translation.split("\\s+");
                    int last_syllable_vowel = syllables.length -1;
                    for(int j = syllables.length-1; j>= 0; j--){
                        String syllable = syllables[j];
                        if(syllable.contains("a") || syllable.contains("e") || syllable.contains("o") || syllable.contains("i") || syllable.contains("u")){
                            break;
                        }
                        else{
                            last_syllable_vowel -= 1;
                        }
                    }
                    String last_syllable = "";
                    for(int k = last_syllable_vowel; k < syllables.length; k++){
                        last_syllable += syllables[k];
                    }
                    if(Syllables_to_Event_Array_Position.containsKey(last_syllable)){
                        int position_in_Event_List = Syllables_to_Event_Array_Position.get(last_syllable);
                        Event_Array_Position.get(position_in_Event_List).add(russian_word);
                    }
                    else{
                        Syllables_to_Event_Array_Position.put(last_syllable, next_array_position);
                        Event_Array_Position.add(new ArrayList<>());
                        Event_Array_Position.get(next_array_position).add(russian_word);
                        next_array_position += 1;
                    }

                }
                
            }
        }
        else if(isEnglish){
            for(String english_word : words_or_chinese_phrases){
                String original_english_word = english_word;
                english_word = english_word.toLowerCase();
                if(!phonetics_HashMap.containsKey(english_word)){
                    ArrayList<String>added_in = new ArrayList<>();
                    added_in.add(english_word);
                    Event_Array_Position.add(added_in);
                    next_array_position += 1;
                }
                else{
                    String phonetic_translation = phonetics_HashMap.get(english_word);
                    String [] syllables = phonetic_translation.split("\\s+");
                    //System.out.println(Arrays.toString(syllables));
                    int last_syllable_vowel = syllables.length -1;
                    for(int j = syllables.length-1; j> 0; j--){
                        String syllable = syllables[j];
                        if(syllable.contains("A") || syllable.contains("E") || syllable.contains("O") || syllable.contains("I") || syllable.contains("U") ||syllable.contains("Y")){
                            break;
                        }
                        else{
                            last_syllable_vowel -= 1;
                        }
                    }
                    String last_syllable = "";
                    for(int k = last_syllable_vowel; k < syllables.length; k++){
                        last_syllable += syllables[k];
                    }
                    if(Syllables_to_Event_Array_Position.containsKey(last_syllable)){
                        int position_in_Event_List = Syllables_to_Event_Array_Position.get(last_syllable);
                        Event_Array_Position.get(position_in_Event_List).add(original_english_word);
                    }
                    else{
                        Syllables_to_Event_Array_Position.put(last_syllable, next_array_position);
                        Event_Array_Position.add(new ArrayList<>());
                        Event_Array_Position.get(next_array_position).add(original_english_word);
                        next_array_position += 1;
                    }
                    
                }
            }
            //System.out.println(Event_Array_Position.toString());
            //System.out.println("\n\n");

        }
        else{
            System.out.println("We are here??");
            for(String ambigous_phrase : words_or_chinese_phrases){
                ArrayList<String>added_in = new ArrayList<>();
                added_in.add(ambigous_phrase);
                Event_Array_Position.add(added_in);
            }
        }
        //System.out.println(Event_Array_Position.toString());
        //System.out.println("\n\n");
        return Event_Array_Position;


       
        
    }




    /**
	 * Create sequence of rhyming words from document set.
	 * 
	 * @param document
	 *            document of interest
	 */
	@Override
	public EventSet createEventSet(char[] text) {
		EventSet es = new EventSet();
		
        ArrayList<ArrayList<String>>rhyme_splits = find_events_rhyming_events(text);
        for(ArrayList<String> words_that_rhyme_together:  rhyme_splits){
            String all_rhymes_combined = String.join(" " , words_that_rhyme_together);
            es.addEvent(new Event(all_rhymes_combined , this));
        }
        

        
        return es;

	}

    
}
