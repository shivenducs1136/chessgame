package com.bitiwsor.chessgame.exceptions;

public class InvalidMoveException extends Exception{
    public InvalidMoveException(){
        super("Move you are trying to perform is invalid!");
    }
    public InvalidMoveException(String s){
        super(s);
    }
}
