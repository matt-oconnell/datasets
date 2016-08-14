package corgis.classics.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * 
 */
public class Difficulty {
	
    // The 'Flesch Reading Ease' uses the sentence length (number of words per sentence) and the number of syllables per word in an equation to calculate the reading ease. Texts with a very high Flesch reading Ease score (about 100) are very easy to read, have short sentences and no words of more than two syllables.
    private Double fleschReadingEase;
    // The Automated Readability Index is a number indicating the understandability of the text. This number is an approximate US Grade Level needed to comprehend the text, calculated using the characters per word and words per sentences.
    private Double automatedReadabilityIndex;
    // The Coleman Liau Index is a number indicating the understandability of the text. This number is an approximate US Grade Level needed to comprehend the text, calculated using characters instead of syllables, similar to the Automated Readability Index.
    private Double colemanLiauIndex;
    // The Gunning Fog Index measures the readability of English writing. The index estimates the years of formal education needed to understand the text on a first reading. The formula is calculated using the ratio of words to sentences and the percentage of words that are complex (i.e. have three or more syllables).
    private Double gunningFog;
    // Linsear Write is a readability metric for English text, purportedly developed for the United States Air Force to help them calculate the readability of their technical manuals. It was designed to calculate the United States grade level of a text sample based on sentence length and the number words used that have three or more syllables.
    private Double linsearWriteFormula;
    // The Dale Chall Readability Score provides a numeric gauge of the comprehension difficulty that readers come upon when reading a text. It uses a list of 3000 words that groups of fourth-grade American students could reliably understand, considering any word not on that list to be difficult. This number is an approximate US Grade Level needed to comprehend the text.
    private Double daleChallReadabilityScore;
    // The "Flesch-Kincaid Grade Level Formula" presents a score as a U.S. grade level, making it easier to understand. It uses a similar formula to the Flesch Reading Ease measure.
    private Double fleschKincaidGrade;
    // The SMOG grade is a measure of readability that estimates the years of education needed to understand a piece of writing. SMOG is the acronym derived from "Simple Measure of Gobbledygook". Its formula is based on the number of polysyllables (words with three or more syllables) and the number of sentences.
    private Double smogIndex;
    // The number of words in the text that are considered "difficult"; that is, they are not on a list of 3000 words that are considered understandable by fourth-grade American students.
    private Integer difficultWords;
    
    
    /*
     * @return 
     */
    public Double getFleschReadingEase() {
        return this.fleschReadingEase;
    }
    
    
    
    /*
     * @return 
     */
    public Double getAutomatedReadabilityIndex() {
        return this.automatedReadabilityIndex;
    }
    
    
    
    /*
     * @return 
     */
    public Double getColemanLiauIndex() {
        return this.colemanLiauIndex;
    }
    
    
    
    /*
     * @return 
     */
    public Double getGunningFog() {
        return this.gunningFog;
    }
    
    
    
    /*
     * @return 
     */
    public Double getLinsearWriteFormula() {
        return this.linsearWriteFormula;
    }
    
    
    
    /*
     * @return 
     */
    public Double getDaleChallReadabilityScore() {
        return this.daleChallReadabilityScore;
    }
    
    
    
    /*
     * @return 
     */
    public Double getFleschKincaidGrade() {
        return this.fleschKincaidGrade;
    }
    
    
    
    /*
     * @return 
     */
    public Double getSmogIndex() {
        return this.smogIndex;
    }
    
    
    
    /*
     * @return 
     */
    public Integer getDifficultWords() {
        return this.difficultWords;
    }
    
    
    
	
	/**
	 * Creates a string based representation of this Difficulty.
	
	 * @return String
	 */
	public String toString() {
		return "Difficulty[" +fleschReadingEase+", "+automatedReadabilityIndex+", "+colemanLiauIndex+", "+gunningFog+", "+linsearWriteFormula+", "+daleChallReadabilityScore+", "+fleschKincaidGrade+", "+smogIndex+", "+difficultWords+"]";
	}
	
	/**
	 * Internal constructor to create a Difficulty from a  representation.
	 * @param json_data The raw json data that will be parsed.
	 */
    public Difficulty(JSONObject json_data) {
        try {// flesch reading ease
            this.fleschReadingEase = (Double)json_data.get("flesch reading ease");// automated readability index
            this.automatedReadabilityIndex = (Double)json_data.get("automated readability index");// coleman liau index
            this.colemanLiauIndex = (Double)json_data.get("coleman liau index");// gunning fog
            this.gunningFog = (Double)json_data.get("gunning fog");// linsear write formula
            this.linsearWriteFormula = (Double)json_data.get("linsear write formula");// dale chall readability score
            this.daleChallReadabilityScore = (Double)json_data.get("dale chall readability score");// flesch kincaid grade
            this.fleschKincaidGrade = (Double)json_data.get("flesch kincaid grade");// smog index
            this.smogIndex = (Double)json_data.get("smog index");// difficult words
            this.difficultWords = new Integer(((Long)json_data.get("difficult words")).intValue());
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Difficulty; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Difficulty; a field had the wrong structure.");
    		e.printStackTrace();
        }
	}	
}