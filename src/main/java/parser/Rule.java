package parser;

import scanner.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammad hosein on 6/25/2015.
 */
public class Rule {
    public Rule(String stringRule) {
        int index = stringRule.indexOf("#");
        if (index != -1) {
            try {
                semanticAction = Integer.parseInt(stringRule.substring(index + 1));
            } catch (NumberFormatException ex) {
                semanticAction = 0;
            }
            stringRule = stringRule.substring(0, index);
        } else {
            semanticAction = 0;
        }
        String[] splitted = stringRule.split("->");
        LHS = NonTerminal.valueOf(splitted[0]);
        RHS = new ArrayList<>();
        if (splitted.length > 1) {
            String[] RHSs = splitted[1].split(" ");
            for (String s : RHSs) {
                try {
                    RHS.add(new GrammarSymbol(NonTerminal.valueOf(s)));
                } catch (Exception e) {
                    RHS.add(new GrammarSymbol(new Token(Token.getTypeFormString(s), s)));
                }
            }
        }
    }

    private final NonTerminal LHS;
    private final List<GrammarSymbol> RHS;
    private int semanticAction;

    public NonTerminal getLHS() {
        return LHS;
    }

    public List<GrammarSymbol> getRHS() {
        return RHS;
    }

    public int getSemanticAction() {
        return semanticAction;
    }
}

class GrammarSymbol {
    private final boolean isTerminal;

    public GrammarSymbol(NonTerminal nonTerminal) {
        isTerminal = false;
    }

    public GrammarSymbol(Token terminal) {
        isTerminal = true;
    }
}