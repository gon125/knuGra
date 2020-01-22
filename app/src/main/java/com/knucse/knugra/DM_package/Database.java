package com.knucse.knugra.DM_package;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info;
import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_Item;
import com.knucse.knugra.PD_package.Graduation_Info_package.Graduation_Info_List;
import com.knucse.knugra.PD_package.Subject_package.Subject;
import com.knucse.knugra.PD_package.Subject_package.SubjectList;
import com.knucse.knugra.R;
import com.knucse.knugra.UI_package.login.LoginActivity;


import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.common.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class Database { // 데이터베이스 접근 객체

    private static volatile Database instance;

    private static Graduation_Info_List graduationInfoList = Graduation_Info_List.getInstance();
    private static SubjectList requiredSubjectList;
    private static SubjectList designSubjectList;


    private Database() {

    }


    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public static void load() {
        graduationInfoList = loadGraduationInfoList_temp();
        designSubjectList = loadDesignSubjectList();
        requiredSubjectList = loadRequiredSubjectList();
    }

    private static Graduation_Info_List loadGraduationInfoList_temp() {
        XSSFWorkbook workbook = null;
        Row row;
        Cell cell;
        Graduation_Info_List graduation_info_list = Graduation_Info_List.getInstance();

        InputStream is = LoginActivity.loginActivity.getResources().openRawResource(R.raw.graduation_info_list);

        try {
            File file = File.createTempFile("temp", ".tmp");
            IOUtil.copyCompletely(is , new FileOutputStream(file));
            OPCPackage opcPackage = OPCPackage.open(file);
            workbook = new XSSFWorkbook(opcPackage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator<Sheet> sheetIterator = workbook.sheetIterator();

        while(sheetIterator.hasNext()) {

            Sheet sheet = sheetIterator.next();
            Graduation_Info gi = new Graduation_Info();
            gi.setInfo_track(sheet.getSheetName());

            Iterator<Row> rowIterator = sheet.iterator();


            // first row is key values

            if (rowIterator.hasNext()) {

                // get first row
                row = rowIterator.next();
            } else {
                // handle error
            }

            // from the second it's data
            while (rowIterator.hasNext()) {
                row = rowIterator.next();

                    cell = row.getCell(0);

                    String key = getCellToString(cell);


                    cell = row.getCell(1);

                    String value = getCellToString(cell);

                    Graduation_Info_Item git = new Graduation_Info_Item();

                    git.setName(key);
                    git.setContent(value);
                    gi.add(git);
            }

            // after graduation_info is done fill subject list
            graduation_info_list.add(gi);
        }


        return Graduation_Info_List.getInstance();
    }

    private static String getCellToString(Cell cell) {

        String cellToString = "";

        switch (cell.getCellTypeEnum()) {
            case _NONE:
                break;
            case NUMERIC:
                cellToString = String.valueOf(Math.round(cell.getNumericCellValue()));
                break;
            case STRING:
                cellToString = cell.getStringCellValue();
                break;
            case FORMULA:
                break;
            case BLANK:
                break;
            case BOOLEAN:
                break;
            case ERROR:
                break;
        }

        return cellToString;
    }

    private static SubjectList loadDesignSubjectList() { // 설계과목 가져오기
        return getSubjectList(R.raw.design_subject_list);
    }

    private static SubjectList loadRequiredSubjectList() {// 필수과목 가져오기
        return getSubjectList(R.raw.required_subject_list);
    }

    private static SubjectList getSubjectList(int resourceId) {
        SubjectList subjectList = new SubjectList();
        XSSFWorkbook workbook = null;
        Row row;
        Iterator<Cell> cellIterator;
        ArrayList<String> keys = new ArrayList<>();
        Cell cell;
        Subject subject = null;


        InputStream is = LoginActivity.loginActivity.getResources().openRawResource(resourceId);

        try {
            File file = File.createTempFile("temp", ".tmp");
            IOUtil.copyCompletely(is , new FileOutputStream(file));
            OPCPackage opcPackage = OPCPackage.open(file);
            workbook = new XSSFWorkbook(opcPackage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();


        // first row is key values

        if (rowIterator.hasNext()) {

            // get first row
            row = rowIterator.next();
            cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                keys.add(cell.getStringCellValue());
            }
        } else {
            // handle error
        }


        // from the second it's data
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            cellIterator = row.iterator();
            Iterator<String> keysIterator = keys.iterator();
            subject = new Subject();
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();

                String cellToString = getCellToString(cell);

                String key = keysIterator.next();
                subject.put(key, cellToString);
            }

            // after subject is done fill subject list


            // 과목코드는 키값의 0 번쨰 인덱스에 있음
            String subjectCode = subject.get(DAPATH.SUBJECT_CODE);


            // 과목코드를 키값으로 설계과목목록에 설계과목 해시테이블을 집어넣음
            subjectList.put(subjectCode, subject);
        }

        return subjectList;
    }

    public static SubjectList getDesignSubjectList() {
        return designSubjectList;
    }

    public static SubjectList getRequiredSubjectList() {
        return requiredSubjectList;
    }

    public static Graduation_Info_List getGraduationInfoList() {
        return graduationInfoList;
    }
}
