import CodeGen.*;
import Sol.SolLexer;
import Sol.SolParser;
import SymbolTable.Scope;
import SymbolTable.Symbol;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.io.*;
import java.security.Key;
import java.util.List;
import java.util.Map;


public class solCompiler {
    public static void main(String[] args) {
        String inputFile = args.length > 0 ? args[0] : null;
        boolean debug = args.length > 1 ? args[1].equals("-d") : false;
        try (InputStream is = (inputFile != null) ? new FileInputStream(inputFile) : System.in) {
            if (inputFile != null && !inputFile.endsWith(".sol"))
                throw new IOException();
            CharStream input = CharStreams.fromStream(is);
            SolLexer lexer = new SolLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SolParser parser = new SolParser(tokens);
            ParseTree tree = parser.prog();
            //Error handling
            if (parser.getNumberOfSyntaxErrors() > 0)
                System.exit(1);
            //
            FunctionsAnnotator Fannotator = new FunctionsAnnotator();
            Fannotator.visit(tree);
            //System.out.println("Functions analysis successful");
            Scope Fscope = Fannotator.getCurrentScope();
            Annotator annotator = new Annotator(Fscope);
            annotator.visit(tree);
            ParseTreeProperty<Type> values = annotator.getValues();
            Map<String, Object> vars = annotator.getVars();
            Scope scope = annotator.getCurrentScope();
            Map<String, Symbol> scopes = scope.getSymbols();
            List<Scope> children = scope.getChildScopes();
            /*System.out.println(scope.toString());
            for (Map.Entry<String, Symbol> entry : scopes.entrySet()) {
                System.out.println(entry.getValue().toString());
            }
            System.out.println();
            for (Scope child : children) {
                System.out.println(child.toString());
                scopes = child.getSymbols();
                for (Map.Entry<String, Symbol> entry : scopes.entrySet()) {
                    System.out.println(entry.getValue().toString());
                }
                System.out.println();
            }*/
            //System.out.println("Semantic analysis successful");
            FunctionSemantics functionSemantics = new FunctionSemantics(scope, values);
            functionSemantics.visit(tree);
            scope = functionSemantics.getCurrentScope();
            values = functionSemantics.getValues();
            CodeGenVisitor assembler = new CodeGenVisitor(values, scope);
            assembler.visit(tree);
            String outputFile = inputFile != null ? inputFile.substring(0, inputFile.lastIndexOf(".")).concat(".tbc") : "output.tbc";
            String outputFileTASM = inputFile != null ? inputFile.substring(0, inputFile.lastIndexOf(".")).concat(".tasm") : "output.tasm";
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputFile));
            assembler.write(dos);
            assembler.writeTASM(outputFileTASM);
            System.out.println("Compilation successful");
            System.out.println("TBC output file: " + outputFile);
            System.out.println("Tasm utput file: " + outputFileTASM);
            if (debug){
                assembler.print();
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
