/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical_analyzer;

/**
 *
 * @author Ameer Ayman
 */
public class Node {

    int state;  //initalizing variable that has the number of the state 
    Node next;  //initalizing object of the class node that using for refering to the next node

    /* constructor */
    public Node(int state, Node next) {
        this.state = state;
        this.next = next;
    }

    public Node() {
    }

    public boolean machineForIdentifiers(String token) {
        Node startState = new Node(1, null);
        Node acceptState = new Node(2, null);
        Node deadState = new Node(3, null);
        char inputString[] = token.toCharArray();

        boolean bb;
        int current = startState.state;
        /* this for loop to loop all the char of the string that stored in the array*/
        for (int i = 0; i < inputString.length; i++) {
            if (current == 1 && (Character.isLetter(inputString[i]) || inputString[i] == '_')) {
                startState.next = acceptState;
                current = acceptState.state;
            } else if (current == 1 && (!(Character.isLetterOrDigit(inputString[i])) || inputString[i] != '_')) {
                startState.next = deadState;
                current = deadState.state;
            }
            if (current == 2) {
                if (Character.isLetterOrDigit(inputString[i]) || inputString[i] == '_') {
                    acceptState.next = acceptState;
                    current = acceptState.state;
                } else {
                    acceptState.next = deadState;
                    current = deadState.state;
                }
            }
        }
        bb = validationForIdentifiers(current);
        return bb;
    }

    public boolean machineForliterals(String token) {
        Node startState = new Node(1, null);
        Node node2 = new Node(2, null);
        Node node3 = new Node(3, null);
        Node acceptState1 = new Node(4, null);
        Node acceptState2 = new Node(5, null);
        Node deadState = new Node(6, null);
        char inputString[] = token.toCharArray();

        boolean bb;
        int current = startState.state;
        /* this for loop to loop all the char of the string that stored in the array*/
        for (int i = 0; i < inputString.length; i++) {
            if (current == 1) {
                if (inputString[i] == '+' || inputString[i] == '-') {
                    startState.next = node2;
                    current = node2.state;
                } else if (Character.isDigit(inputString[i])) {
                    startState.next = acceptState1;
                    current = acceptState1.state;
                } else if (inputString[i] == '.') {
                    startState.next = node3;
                    current = node3.state;
                } else {
                    startState.next = deadState;
                    current = deadState.state;
                }
                continue;
            }
            if (current == 2) {
                if (Character.isDigit(inputString[i])) {
                    node2.next = acceptState1;
                    current = acceptState1.state;
                } else if (inputString[i] == '.') {
                    startState.next = node3;
                    current = node3.state;
                } else {
                    node2.next = deadState;
                    current = deadState.state;
                }
                continue;
            }
            if (current == 3) {
                if (Character.isDigit(inputString[i])) {
                    node3.next = acceptState2;
                    current = acceptState2.state;
                } else {
                    node3.next = deadState;
                    current = deadState.state;
                }
                continue;
            }
            if (current == 4) {
                if (Character.isDigit(inputString[i])) {

                    acceptState1.next = acceptState1;
                    current = acceptState1.state;

                } else if (inputString[i] == '.') {
                    acceptState1.next = node3;
                    current = node3.state;

                } else {
                    acceptState1.next = deadState;
                    current = deadState.state;
                }
                continue;
            }
            if (current == 5 && Character.isDigit(inputString[i])) {

                acceptState2.next = acceptState2;
                current = acceptState2.state;
            } else {
                acceptState2.next = deadState;
                current = deadState.state;
            }
        }
        bb = validationForLiterals(current);
        return bb;
    }

    public boolean validationForIdentifiers(int state) {
        boolean b;
        if (state == 2) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    public boolean validationForLiterals(int state) {
        boolean b;
        if (state == 4 || state == 5) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

}
