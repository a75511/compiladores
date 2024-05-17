package CodeGen;

public class ErrorHandler {
    private String message;

    public ErrorHandler(String message) {
        this.message = message;
        print();
    }

    public void print() {
        // ANSI escape code para meter as mensagens com cor vermelha
        System.out.print("\u001B[31m");
        System.out.println(message);
        System.exit(1);
    }
}
