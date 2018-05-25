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
public class Player {
    
    private final int MAXHAND = FindMax();
    private Card[] hand = new Card[MAXHAND];
    public enum State{
        PLAYING, BLACKJACK, BUST
    }
    private State state;
    private int total;
    private int handSize;
    private int money;
    private int bet;
    
    private int FindMax(){
        int counter = 0;
        int total = 0;
        int card = 1;
        int house = 1;
        while(counter < Game.HOUSES * Game.MAXCARDS && total <= Game.BLACKJACK){
            counter++;
            total += card;
            house++;
            if(house == Game.HOUSES){
                house = 1;
                card++;
            }
        }
        return counter;
    }
    
    public Player(){
        money = 100;
    }
    public Player(int money){
        this.money = money;
    }
    public void addMoney(int bet){
        money += bet;
    }
    public boolean isPoor(){
        if(money > 0)
            return false;
        return true;
    }
    public int myMoney(){
        return money;
    }
    
    private void addTotal(int position){
        if(hand[position].getNumber() > Game.MAXCARDS-3)
            total += Game.MAXCARDS-3;
        else
            total += hand[position].getNumber();
        
    }
    
    public void firstTurn(Card first, Card second, int bet){
        handSize = 0;
        total = 0;
        
        draw(first);
        draw(second);
        
        if(total == Game.BLACKJACK)
            state = State.BLACKJACK;
        state = State.PLAYING;
        this.bet = bet;
    }
    
    public State draw(Card draw){
        hand[handSize] = draw;
        addTotal(handSize);
        handSize++;
        if(total > Game.BLACKJACK)
            state = State.BUST;
        return state;
    }
    
    public void clearHand(int dealer){
        for(int i = 0; i < handSize; i++){
            hand[i] = null;
        }
        handSize = 0;
        if(state != State.BUST && total < dealer || state == State.BLACKJACK)
            money += bet;
        else
            money -= bet;
    }
    
    public void clearHand(){
        for(int i = 0; i < handSize; i++){
            hand[i] = null;
        }
        handSize = 0;
        if(state != State.BUST)
            money += bet;
        else
            money -= bet;
    }
    
    public int getTotal(){
        return total;
    }
    
    public State getState(){
        return state;
    }
    
    public String handToString(){
        String handString = "";
        for(int i = 0; i < handSize; i++){
            handString += hand[i].toString() + "|";
        }
        handString += total;
        
        return handString;
    }
}
