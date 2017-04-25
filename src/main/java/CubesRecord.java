/**
 * Created by samagbeh on 4/25/17.
 */
public class CubesRecord {

    String name;
    double recordTime;

    CubesRecord(String n, double rt) {
        name = n;
        recordTime = rt;
    }

    @Override
    public String toString() { return "[" + "Name: " + name + "]" + " [" + " Solve Time: " + recordTime + "]";
    }

}
