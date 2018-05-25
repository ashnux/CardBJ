/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardbj;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Controller {
    Scanner sc;
    public Controller(){
        sc = new Scanner(System.in);
    }
    public Game.Choice getChoice(){
        boolean valid = false;
        String read;
        while(!valid){
            System.out.println("hit or stand?");
            valid = true;
            read = sc.next().toLowerCase();
            if("stand".equals(read))
                return Game.Choice.STAND;
            else if("hit".equals(read))
                return Game.Choice.HIT;
            else
                valid = false;
        }
        return Game.Choice.STAND;
    }
    
    public Game.Choice nextGame(){
    boolean valid = false;
        String read;
        while(!valid){
            System.out.println("finish or continue?");
            valid = true;
            read = sc.next().toLowerCase();
            if("finish".equals(read))
                return Game.Choice.FINISH;
            else if("continue".equals(read))
                return Game.Choice.CONTINUE;
            else
                valid = false;
        }
        return Game.Choice.FINISH;
    }
}
