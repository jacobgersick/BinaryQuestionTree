//Programmer: Jacob Gersick
//Class: CS 145
//Date: 3/10/2023
//Assignment: Lab 6 - 20 questions
//Reference: Chapter 17
//Purpose: This program plays the game twenty questions by building
// a binary tree of questions and answers.
// It gets ‘smarter’ every round by asking the user for feedback.
// It can also save games as text and import text files to construct trees for a game.




import java.io.PrintStream;
import java.util.Scanner;

public class QuestionTree {

    private int gameCount;
    private int winCount;
    private QuestionNode overallRoot;
    private UserInterface ui;
    public QuestionTree (UserInterface ui) {
    overallRoot = new QuestionNode("computer");
    this.ui = ui;
    }

    public void play() {
        gameCount++;
        QuestionNode currentNode = overallRoot;
        QuestionNode parentNode = currentNode;

        //if it's not a leaf, it's a question, so print
        // it out
        while (!isLeaf(currentNode)) {
            ui.println(currentNode.data + "y/n");

        //go left if yes, right if no, keeps track of
            // parents
            if (ui.nextBoolean()) {
                parentNode = currentNode;
                currentNode = currentNode.left;

            } else {
                parentNode = currentNode;
                currentNode = currentNode.right;
            }
        }
        //once at a leaf, ask if this is the object
        ui.println("Is your object a : " + currentNode.data +
                "? y/n ");
        //if yes, end game and increment wins
        if (ui.nextBoolean()) {
            ui.println("I win!");
            winCount++;
        } else {
            //get user's correct object
            ui.println("I lose. What is your object? ");
            String usersObject = ui.nextLine();

            ui.println("Please give me a question that" +
                    " distinguishes a " +
                    currentNode.data + " from a " +
                    usersObject + ":");
            String newQuestion = ui.nextLine();
            //creates a new node with user's question as data
            QuestionNode newNode = new QuestionNode(newQuestion);

            ui.println("What is the answer to your " +
                    "question for a " +
                    usersObject + " y/n");

            //creates new leaf node with user's object as data
            // attaches left or right to current
            if (ui.nextBoolean()) {
                newNode.left = new QuestionNode(usersObject);
                newNode.right = currentNode;
            } else {
                newNode.right = new QuestionNode(usersObject);
                newNode.left = currentNode;
            }
            //replaces overallRoot if it's a leaf
            if (currentNode == overallRoot) {
                overallRoot = newNode;
                // replaces current node with new node
            } else {
                replaceNode(parentNode, currentNode, newNode);
            }
        }
    }
//replaces one node with another
    public void replaceNode(QuestionNode parentNode,
                            QuestionNode oldNode,
                            QuestionNode newNode) {
        if (parentNode.left == oldNode) {
            parentNode.left = newNode;
        } else {
            parentNode.right = newNode;
        }
    }

//uses private method to save
    public void save(PrintStream output) {

        printPreorder(overallRoot, output);

}

//uses private method to load
    public void load(Scanner input) {

            overallRoot = loadPreorder(input);

    }

    //creates a new tree from an input text file
    private QuestionNode loadPreorder(Scanner input) {
        if (input.hasNextLine()) {
            //makes a node and sets it's data to the line,
            // minus the Q/A prefix
            String line = input.nextLine();
            QuestionNode newNode = new
                    QuestionNode(line.substring(2));

            //If it's a Q, then it's a root and not a leaf,
            // so doSomething
            if (line.startsWith("Q:")) {
                newNode.left = loadPreorder(input);
                newNode.right = loadPreorder(input);
            }
            //places the node into whatever called it
            return newNode;

            //if it's not a Q, then it's a leaf and do nothing
            // more
            } else {
            return null;
            }
    }

    //creates the 'save game' text file. Starts at
    // overall root and reads preorder
    private void printPreorder(QuestionNode root,
                               PrintStream output) {
        //if there's something in the node
        if (root != null) {
            //and if the node is a lef, then output an A:
            if (isLeaf(root)) {
                output.println("A:" + root.data);

                //if it's not a leaf then it's a Q:
            } else {
                output.println("Q:" + root.data);
            }
            //preorder recursive calls to traverse
            //the tree
            printPreorder(root.left, output);
            printPreorder(root.right, output);
        }
    }

    public int totalGames() {
        return gameCount;
    }

    public int gamesWon() {
        return winCount;
    }

    //checks if QuestionNode is a leaf
    //(no left or right)
    private boolean isLeaf(QuestionNode node)
    {
        return (node.left == null && node.right == null);
    }
}

