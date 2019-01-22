package com.admin.nidhi_khushali;

public class Quiz_question {

    public String questions []={"The language spoken by the people by Pakistan is ?",
            "The World's largest desert is?",
            "Country that has the highest in Barley Production ?"};

    public String choices [] [] ={ { "Hindi","Palauan","Nauruan","Sindhi"},
            {"Thar","Sahara","Sonoran","Kalahari"},
            {"China","India","Russia","France"}
    };

    public String answers []={"Palauan",
            "Sahara",
            "Russia"};

    public String getQuestion(int a){
        String question = questions[a];
        return question;
    }

    public String getChoice1(int a){
        String choice = choices[a][0];
        return choice;
    }
    public String getChoice2(int a){
        String choice = choices[a][1];
        return choice;
    }
    public String getChoice3(int a){
        String choice = choices[a][2];
        return choice;
    }

    public String getChoice4(int a){
        String choice = choices[a][3];
        return choice;
    }

    public String getAnswer(int a){
        String answer = answers[a];
        return answer;
    }

    public String setAnswer(int a){
        String answer = answers[a];
        return answer;
    }


}

