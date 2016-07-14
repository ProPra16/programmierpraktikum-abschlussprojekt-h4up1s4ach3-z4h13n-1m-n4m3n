package de.hzin.tddt.util;

/**
 * Created by julius on 14.07.16.
 */
public class ErrorCounter {
    private int syntax = 0;
    private int identifiers = 0;
    private int computation = 0;
    private int returnStatements = 0;
    private int accessToStaticEntities = 0;

    public void addErrorCounter(int n_syntax, int n_identifiers, int n_computation, int n_returnStatements, int n_accessToStaticEntities){
        syntax += n_syntax;
        identifiers += n_identifiers;
        computation += n_computation;
        returnStatements += n_returnStatements;
        accessToStaticEntities += n_accessToStaticEntities;
    }

    public void countError(String error) {
        if (error.contains("expected") ||
            error.contains("unclosed string literal") ||
            error.contains("illegal start of expression") ||
            error.contains("not a statement")) {
            syntax++;
        } else if ( error.contains("cannot find symbol") ||
                    error.contains("is already defined in") ||
                    error.contains("array required but") ||
                    error.contains("has private access in")) {
            identifiers++;
        } else if(  error.contains("might not have been initialized") ||
                    error.contains("cannot be applied to") ||
                    error.contains("possible loss of precision") ||
                    error.contains("incompatible types") ||
                    error.contains("inconvertible types")){
            computation++;
        } else if(  error.contains("missing return statement") ||
                    error.contains("missing return value") ||
                    error.contains("cannot return a value from a method whose result type is void") ||
                    error.contains("invalid method declaration; return type required") ||
                    error.contains("unreachable statement")){
            returnStatements++;
        } else if(  error.contains("non-static variable cannot be referenced from a static context") ||
                    error.contains("non-static method cannot be referenced from a static context")) {
            accessToStaticEntities++;
        }

    }

    public int getAccessToStaticEntities() {
        return accessToStaticEntities;
    }

    public int getComputation() {
        return computation;
    }

    public int getIdentifiers() {
        return identifiers;
    }

    public int getSyntax() {
        return syntax;
    }

    public int getReturnStatements() {
        return returnStatements;
    }
}