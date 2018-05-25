/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardbj;

import java.util.Random;

/**
 *
 * @author Student
 */
public class Game {
//max value will be treated as maxcards - 3 and ace will be treated as either 1 or maxcards - 2

    public static final int MAXCARDS = 13;
    public static final int HOUSES = Card.House.values().length;
    public static final int BLACKJACK = MAXCARDS * 2 - 5; //blackjack is ace(MAXCARDS - 2) plus max value,k,q,j(MAXCARDS - 3)
    private final int MAXPLAYERS = 2; //must be 2 or more(one is the dealer)
    public enum Choice{
        HIT, STAND, FINISH, CONTINUE
    }
    
    private Random deal = new Random();
    private Card[] deck = new Card[MAXCARDS * HOUSES];
    private Player[] player = new Player[MAXPLAYERS];
    private View view = new View();
    
    public Game() {
        int current = 0;
        for (Card.House house : Card.House.values()) {
            for (int i = 1; i < MAXCARDS + 1; i++) {
                deck[current] = new Card(i, house);
                current++;
            }
        }
    }

    public boolean Start() {
        if (deck.length / 2 < player.length) {
            return false;
        }
        for (int i = 0; i < MAXPLAYERS; i++) {
            player[i] = new Player();
        }

        return true;
    }

    private void firstDraw() {
        int drawA, drawB;

        for (int i = 0; i < MAXPLAYERS; i++) {
            do {
                drawA = deal.nextInt(deck.length);
            } while (!deck[drawA].isInDeck());

            deck[drawA].setInDeck(false);

            do {
                drawB = deal.nextInt(deck.length);
            } while (!deck[drawB].isInDeck());

            deck[drawB].setInDeck(false);
            int bet = 10;
            player[i].firstTurn(deck[drawA], deck[drawB], bet);
        }
    }

    public void Playing() {
        boolean playing;
        Choice choice;
        int gaming = 10;
        int draw;
        int deckIterate;
        int highest = 0;
        
        //View view = new View();
        Controller input = new Controller();

        while (gaming>0) {
            gaming--;
            view.print("turn " + (10-gaming));
            firstDraw();

            for (int i = 0; i < player.length; i++) {
                if (player[i].getState() == Player.State.BLACKJACK) {
                    playing = false;
                } else {
                    playing = true;
                }
                while (playing) {
//                    view.
                    choice = Choice.HIT;
                    if(i == player.length - 1){
                        if(player[i].getTotal() > 16 || player[i].getTotal() > highest){ //dealer
                            choice = Choice.STAND;
                        }
                    }
                    else{
                        if(player[i].getTotal() < 21){
                            view.print(player[i].handToString() + " " + player[i].getState());
                            choice = input.getChoice();
                        }
                        else
                            choice = Choice.STAND;
                    }
                    if (choice.equals(Choice.HIT)) {//input here
                        drawCard(i);
                    }
                    else {
                        playing = false;
                    }
                }
                view.print(player[i].handToString() + " " + player[i].getState());
                if(player[i].getTotal() > highest && player[i].getTotal() < 22)
                    highest = player[i].getTotal();
            }
            moneyChange();
            CardReset();
//            break;
        }
    }
    
    private void moneyChange(){
        int playerT;
        Player.State playerS;
        int dealerT = player[player.length-1].getTotal();
        Player.State dealerS = player[player.length-1].getState();
        
        for(int i = 0; i< player.length-1; i++){
            playerT = player[i].getTotal();
            playerS = player[i].getState();
            if(dealerS.equals(Player.State.BLACKJACK)){
                if(playerS.equals(Player.State.BLACKJACK))
                    continue;
                else
                    player[i].addMoney(-10);
            }
            else if(playerT == dealerT && dealerT < 22){
                continue;
            }
            else if((playerT > dealerT || dealerT > 21) && playerT < 22)
                player[i].addMoney(10);
            else{
                player[i].addMoney(-10);
            }
        }
    }
    
    private boolean drawCard(int i){
        int deckIterate = 0;
        int draw = deal.nextInt(deck.length);
        while (true) {
            if (deckIterate == deck.length) {
                return false;
            } else if (!deck[draw].isInDeck()) {
                if (draw < deck.length - 1) {
                    draw++;
                } else {
                    draw = 0;
                }
                deckIterate++;
            } else {
                deck[draw].setInDeck(false);
                if (player[i].draw(deck[draw]) != Player.State.PLAYING) {
                    //playing = false;
                }
                break;
            }
        }
        return true;
    }

    private void CardReset() {
        for (int i = 0; i < deck.length; i++) {
            deck[i].setInDeck(true);
        }
        int dealer = player[player.length-1].getTotal();
        if(dealer > BLACKJACK)
            dealer = 0;//if dealer busts all win excpet those that also bust
        for (int i = 0; i < player.length-1; i++) {
            player[i].clearHand(dealer);
        }
        player[player.length-1].clearHand();
    }

    public void End() {
        for (int i = 0; i < player.length; i++) {
            view.print(player[i].myMoney() + "");
            player[i] = null;
        }
        for (int i = 0; i < deck.length; i++) {
            deck[i].setInDeck(true);
        }
    }
}
