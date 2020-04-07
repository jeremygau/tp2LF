import java.util.*;

public class Grammar {
 private NonTerminal axiom;
 private HashMap<NonTerminal, ArrayList<RightHandSide>> Rules;


 /**
  * construit une grammaire d'axiome ax et sans règle de production
  */
 public Grammar(NonTerminal ax) {
    this.axiom = ax;
    this.Rules = new HashMap<NonTerminal, ArrayList<RightHandSide>>();
 }

    /**
     * ajoute une nouvelle règle de production à la grammaire avec n pour membre gauche et r pout membre droit
     */
/* public void addRule(NonTerminal n, RightHandSide r) {
     ArrayList<RightHandSide> RhsList;
     if (Rules.containsKey(n)) {
         RhsList = Rules.get(n);
         Rules.remove(n);
     } else {
         RhsList = new ArrayList<RightHandSide>();
     }
     RhsList.add(r);
     Rules.put(n, RhsList);
     }*/

    public void addRule(NonTerminal n, RightHandSide r) {
        if (Rules.containsKey(n)) {
            Rules.get(n).add(r);
        } else {
            ArrayList<RightHandSide> RhsList = new ArrayList<>();
            RhsList.add(r);
            Rules.put(n, RhsList);
        }
    }


    /**
     * retourne l'axiome de la grammaire
     * @return le NonTerminal axiome de la grammaire
     */
    public NonTerminal getAxiom(){
        return axiom;
    }



    /**
     * modifie l'axiome de la grammaire
     */
    public void setAxiom(NonTerminal n){
     axiom = n;
 }



 /**
  * teste si un non-terminal peut s'effacer, c'est-à-dire se dériver en epsilon
  * @return vrai (true) si le non-terminal peut se dériver en epsilon
  */
 public Boolean ErasableNonTerminal(NonTerminal N){
     for (RightHandSide rightHandSide : Rules.get(N)) {
        if (ErasableRhs(rightHandSide)) return true;
     }
     return false;
 }



    /**
     * teste si une suite de Symbol formant un membre droit peut s'effacer, c'est-à-dire se dériver en epsilon
     * @return vrai (true) si un membre droit peut se dériver en epsilon
     */
 public Boolean ErasableRhs(RightHandSide r){
     if (r.isEpsilon()) return true;
     if (r.isTerminal()) return false;
     if (r.isSingleNonTerminal()) return ErasableNonTerminal((NonTerminal)r.getSymbols().get(0));
     for (Symbol symbol : r.getSymbols()) {
         if (symbol.getClass() == Terminal.class) {
             return false;
         }
         if (!ErasableNonTerminal((NonTerminal)symbol)){
             return false;
         }
     }
    return true;


 }

    public String toString() {
        StringBuilder grammarString = new StringBuilder();
        for (NonTerminal nonTerminal : Rules.keySet()){
            grammarString.append(nonTerminal).append(" -> ");
            for (RightHandSide rightHandSide : Rules.get(nonTerminal)){
                grammarString.append(rightHandSide).append(" | ");
            }
            grammarString.replace(grammarString.length() - 3, grammarString.length(), "\n");
        }
        return grammarString.toString();
    }



    public static void main(String args[])
    {
        String output;

        Terminal ta = new Terminal("a");
        Terminal tb = new Terminal("b");
        NonTerminal s = new NonTerminal("S");
        NonTerminal a = new NonTerminal("A");

        NonTerminal t = new NonTerminal("T");

        RightHandSide r1 = new RightHandSide();
        r1.addLastSymbol(s);
        r1.addLastSymbol(s);

        RightHandSide r2 = new RightHandSide();
        r2.addLastSymbol(a);
        r2.addLastSymbol(s);

        RightHandSide r = new RightHandSide();
        r.addLastSymbol(ta);
        r.addLastSymbol(s);
        r.addLastSymbol(tb);

        RightHandSide rb = new RightHandSide();

        RightHandSide rc = new RightHandSide();
        rc.addLastSymbol(a);
        rc.addLastSymbol(tb);

        RightHandSide rd = new RightHandSide();
        rd.addLastSymbol(ta);


        Grammar g = new Grammar(s);
        g.addRule(s,r);
        g.addRule(s,rb);
        g.addRule(s,rc);

        g.addRule(a,rd);

        g.addRule(t, r1);
        g.addRule(t, r2);

        output = g.toString();

        System.out.println(output);

        for (NonTerminal nonterminal : g.Rules.keySet()) {
            System.out.println(nonterminal + " is Erasable ? " + g.ErasableNonTerminal(nonterminal));
        }

    }



}
