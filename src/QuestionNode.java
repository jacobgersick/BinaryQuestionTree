//Programmer: Jacob Gersick
//Class: CS 145
//Date: 3/10/2023
//Assignment: Lab 6 - 20 questions
//Reference: Chapter 17
//Purpose: This program plays the game twenty questions by building
// a binary tree of questions and answers.
// It gets ‘smarter’ every round by asking the user for feedback.
// It can also save games as text and import text files to construct trees for a game.

public class QuestionNode {
    public String data;
    public QuestionNode left; //YES side
    public QuestionNode right; //NO side

    //basic constructor, represents a 'leaf' node
    public QuestionNode(String data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }


}
