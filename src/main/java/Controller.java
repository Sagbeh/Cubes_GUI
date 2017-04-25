import java.util.ArrayList;

/**
 * Created by samagbeh on 4/25/17.
 */
public class Controller {

    static GUI gui;
    static DB db;

    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.startApp();

    }

    private void startApp() {

        db = new DB();
        db.createTable();
        ArrayList<CubesRecord> allData = db.fetchAllRecords();
        gui = new GUI(this);
        gui.setListData(allData);
    }


    ArrayList<CubesRecord> getAllData() {
        return db.fetchAllRecords();
    }

    void addRecordToDatabase(CubesRecord cubesRecord) {
        db.addRecord(cubesRecord);
    }

    void delete(CubesRecord cubesRecord) {
        db.delete(cubesRecord);
    }

    void updateRecordInDatabase(CubesRecord cubesRecord) { db.updateRecord(cubesRecord);}

}
