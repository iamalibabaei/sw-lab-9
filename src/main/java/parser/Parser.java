package parser;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Log.Log;
import codeGenerator.CodeGeneratorFacade;
import errorHandler.ErrorHandler;
import scanner.LexicalAnalyzer;
import scanner.token.Token;


public class Parser {
    private final List<Rule> rules;
    private final Stack<Integer> parsStack;
    private ParseTable parseTable;
    private final CodeGeneratorFacade codeGeneratorFacade;

    public Parser() {
        parsStack = new Stack<>();
        parsStack.push(0);
        try {
            parseTable = new ParseTable(Files.readAllLines(Paths.get("src/main/resources/parseTable")).get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rules = new ArrayList<>();
        try {
            for (String stringRule : Files.readAllLines(Paths.get("src/main/resources/Rules"))) {
                rules.add(new Rule(stringRule));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        codeGeneratorFacade = new CodeGeneratorFacade();
    }

    public void startParse(java.util.Scanner sc) {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sc);
        Token lookAhead = lexicalAnalyzer.getNextToken();
        boolean finish = false;
        Action currentAction;
        while (!finish) {
            try {
                Log.print( lookAhead.toString() + "\t" + parsStack.peek());
                currentAction = parseTable.getActionTable(parsStack.peek(), lookAhead);
                Log.print(currentAction.toString());

                switch (currentAction.getAction()) {
                    case SHIFT:
                        parsStack.push(currentAction.getNumber());
                        lookAhead = lexicalAnalyzer.getNextToken();

                        break;
                    case REDUCE:
                        Rule rule = rules.get(currentAction.getNumber());
                        for (int i = 0; i < rule.getRHS().size(); i++) {
                            parsStack.pop();
                        }

                        Log.print(parsStack.peek() + "\t" + rule.getLHS());
                        parsStack.push(parseTable.getGotoTable(parsStack.peek(), rule.getLHS()));
                        Log.print(parsStack.peek() + "");
                        try {
                            codeGeneratorFacade.semanticFunction(rule.getSemanticAction(), lookAhead);
                        } catch (Exception e) {
                            Log.print("Code Genetator Error");
                        }
                        break;
                    case ACCEPT:
                        finish = true;
                        break;
                }
                Log.print("");

            } catch (Exception exception) {

                exception.printStackTrace();
            }


        }
        if (!ErrorHandler.hasError)
            codeGeneratorFacade.printMemory();


    }


}
