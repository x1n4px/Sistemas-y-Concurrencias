package Amigos;
public class Amigos {
    private long a, b;
    private int pos;

    public Amigos(long a, long b, int pos){
        this.a=a;
        this.b=b;
        this.pos=pos;
    }

    public int pos(){
        return pos;
    }

    public String toString(){
        return pos+":("+a+","+b+")";
    }
}
