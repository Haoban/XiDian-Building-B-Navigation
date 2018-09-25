package com.example.navigation;

public class Classroom {
    private static String[][] data={
            {"101","103","106","107"},
            {"201","203","206","207","208","211","000","216","217","218"},
            {"301","303","306","307","308","311","312","314","315","316","318","000","320","321"},
            {"401","403","406","407","408","411","412","414","415","416","418","419","421","422","425","426"},
            {"000","501","000","502","503","506","507","509","510","511","513","514","516","517","520","521"},
            {"000","000","000","000","000","000","601","000","602","603","605","606","608","609","612","613"},
            {"000","000","000","000","000","000","000","000","000","000","000","701","000","703","706","707"},
            {"442","443","437","434","433"},
            {"538","537","532","529","528"}
    };
    public static boolean isClassroom(String temp){
        for(int i = 0;i < data.length;i++){
            for(int j = 0;j < data[i].length; j++){
                if(temp.equals(data[i][j]))
                    return true;
            }
        }
        return false;
    }

    public static int getDoor(String temp){
        if(temp.equals("501"))
            return 2;
        if(temp.equals("218"))
            return 3;
        for(int i = 0;i < data.length;i++){
            for(int j = 0;j < data[i].length; j++){
                if(temp.equals(data[i][j])){
                    if(i<7){
                        switch (j){
                            case 0: case 1:
                                return 1;
                            case 2: case 3:
                                return 2;
                            case 9: case 10: case 11: case 12: case 13:
                                return 4;
                            case 14: case 15:
                                return 5;
                            default:
                                return 3;
                        }
                    }
                    else {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
    public static int getFloor(String temp){
        for(int i = 0;i < data.length;i++){
            for(int j = 0;j < data[i].length; j++){
                if(temp.equals(data[i][j]))
                    return i+1;
            }
        }
        return 0;
    }
    public static int getNumber(String temp){
        for(int i = 0;i < data.length;i++){
            for(int j = 0;j < data[i].length; j++){
                if(temp.equals(data[i][j]))
                    return j+1;
            }
        }
        return 0;
    }
}
