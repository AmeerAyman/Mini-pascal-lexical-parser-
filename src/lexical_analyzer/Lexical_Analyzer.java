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
public class Lexical_Analyzer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tokenizer to = new Tokenizer();
        to.readFromFile_RemoveSingleComments_RemoveSpaces();
        to.removeMultiComments();
        to.putSpacesBetweenWordsAndSymbols();
        to.tokenization();
        to.specifyTypeOfEachToken();
        //to.WriteInFileAndDisplayTableOfTokensWithLineNumberAndType();
        to.WriteTokensInFile();
        to.WriteLineNumberInFile();
        to.WriteTypesInFile();
        //to.displayAllLinesOftheSourceCode();

        
    }

}
 /*

PROGRAM Sort(input, output);
    CONST
        { Max array size. }
        MaxElts = 50;
    TYPE 
        { Type of the element array. }
        IntArrType = ARRAY [1..MaxElts] OF Integer;

    VAR
        { Indexes, exchange temp, array size. }
        i, j, tmp, size: integer;

        { Array of ints }
        arr: IntArrType;

    { Read in the integers. }
    PROCEDURE ReadArr(VAR size: Integer; VAR a: IntArrType);
        BEGIN
            size := 1;
            WHILE NOT eof DO BEGIN
                readln(a[size]);
                IF NOT eof THEN 
                    size = size + 1
            END
        END;

    { Use quicksort to sort the array of integers. }
    PROCEDURE Quicksort(size: Integer; VAR arr: IntArrType);
        
        PROCEDURE QuicksortRecur(start, stop: integer);
            VAR
                m: integer;

                { The location separating the high and low parts. }
                splitpt: integer;

            {* The quicksort split algorithm.  Takes the range, and
              returns the split point. *}
            FUNCTION Split(start, stop: integer): integer;
                VAR
                    left, right: integer;       
			{ Scan pointers. }
                    pivot: integer;             
			{ Pivot value. }

                { Interchange the parameters. }*/