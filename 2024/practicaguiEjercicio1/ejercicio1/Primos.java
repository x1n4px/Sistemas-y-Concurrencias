package ejercicio1;

public class Primos {
    private int primo1, primo2;

    public Primos(int primo1, int primo2) {
        this.primo1 = primo1;
        this.primo2 = primo2;
    }

    public int getPrimo1() {
        return primo1;
    }

    public int getPrimo2() {
        return primo2;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Primos) {
            Primos p = (Primos) obj;
            return (primo1 == p.primo1 && primo2 == p.primo2) || (primo1 == p.primo2 && primo2 == p.primo1);
        }
        return false;
    }

    public String toString() {
        return "(" + primo1 + ", " + primo2 + ")";
    }
}
