package com.example.student.homemade;

public class CancelItem {

    private String mText1;
    private String mText2;
    private String mText3;

    public CancelItem(String text1,String text2,String text3){
        mText1=text1;
        mText2=text2;
        mText3=text3;

    }
    public void changeText1(String text){
        mText1=text;
    }
    public String getmText1(){
        return mText1;
    }
    public String getmText2(){
        return mText2;
    }
    public String getmText3(){
        return mText3;
    }

}