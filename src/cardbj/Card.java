/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardbj;

/**
 *
 * @author Student
 */
public class Card {

    public enum House{
        HEART, DIAMOND, SPADE, CLUB
    }
    private int number;
    private House house;
    private boolean inDeck;
    
    public Card(int number, House house){
        setNumber(number);
        setHouse(house);
        inDeck = true;
    }
    public int getNumber() {
        return number;
    }
    
    private void setNumber(int number) {
        this.number = number;
    }

    public House getHouse() {
        return house;
    }

    private void setHouse(House house) {
        this.house = house;
    }
    
    public boolean isInDeck() {
        return inDeck;
    }

    public void setInDeck(boolean inDeck) {
        this.inDeck = inDeck;
    }
    
    public String toString(){
        String temp = house + ",";
        if(number == Game.MAXCARDS-2)
            temp += "J";
        else if(number == Game.MAXCARDS-1)
            temp += "Q";
        else if(number == Game.MAXCARDS)
            temp += "K";
        else
            temp += number;
        return temp;
    }
}
