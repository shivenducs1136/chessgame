package io;

import exceptions.PositionSizeTooShortException;

public class PositionToIndexConverter {
//    private static PositionToIndexConverter converter;
//    public static PositionToIndexConverter getPositionCoordinateConverter(){
//        if(converter == null){
//            converter = new PositionToIndexConverter();
//        }
//        return converter;
//    }

    public int getIindex(String position){
        if(position.length()<2) throw new PositionSizeTooShortException();
        return position.charAt(0) - '0';
    }
    public int getJindex(String position){
        if(position.length()<2) throw new PositionSizeTooShortException();
        return position.charAt(1) - '0';
    }

    public String getPositionFromIndexes(int i,int j){
        if(i<0 || i>7) throw new IndexOutOfBoundsException();
        if(j<0 || j>7) throw new IndexOutOfBoundsException();
        return i+""+j;
    }

    public boolean isIndexSafe(int i, int j){
        return i >= 0 && i < 8 && j >= 0 && j < 8;
    }
}
