package com.knucse.knugra.DM_package;

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
import java.util.HashMap;
import java.util.Iterator;

import static com.knucse.knugra.DM_package.DAPATH.COMPUTPER_ABEEK;
import static com.knucse.knugra.DM_package.DAPATH.GLOBAL_SOFTWARE_DOUBLE_MAJOR;
import static com.knucse.knugra.DM_package.DAPATH.GLOBAL_SOFTWARE_MASTERS_CHAINING;
import static com.knucse.knugra.DM_package.DAPATH.GLOBAL_SOFTWARE_OVERSEAS_UNIV;

public class Database { // 데이터베이스 접근 객체

    private static volatile Database instance;

    private static HashMap<String, SubjectList> requiredSubjectLists;
    private static SubjectList designSubjectList;
    private static SubjectList startupSubjectList;


    private Database() {

    }


    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public static void load() {
        loadGraduationInfoList_temp();
        designSubjectList = loadDesignSubjectList();
        requiredSubjectLists = loadRequiredSubjectLists();
        startupSubjectList = loadStartupSubjectList();
    }

    public static void destroy() {
        instance = null;
    }


    private static void loadGraduationInfoList_temp() {
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
    }

    private static String getCellToString(Cell cell) {

        String cellToString = "";

        switch (cell.getCellTypeEnum()) {
            case _NONE:
                break;
            case NUMERIC:
                cellToString = String.valueOf(Math.round(cell.getNumericCellValue())).trim();
                break;
            case STRING:
                cellToString = cell.getStringCellValue().trim();
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
    private static SubjectList loadStartupSubjectList() { // 창업과목 가져오기
        return getSubjectList(R.raw.startup_subject_list);
    }

    private static HashMap<String, SubjectList> loadRequiredSubjectLists() {// 필수과목 가져오기
        return getSubjectLists(R.raw.required_subject_list);
    }

    private static HashMap<String, SubjectList> getSubjectLists(int resourceId) {
        HashMap<String, SubjectList> subjectListHashMap = new HashMap<>();
        XSSFWorkbook workbook = null;
        Row row;
        Iterator<Cell> cellIterator;
        Cell cell;
        Subject subject;


        InputStream is = LoginActivity.loginActivity.getResources().openRawResource(resourceId);

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
            SubjectList subjectList = new SubjectList();


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
                cellIterator = row.iterator();
                subject = new Subject();
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();

                    String cellToString = getCellToString(cell);
                    // if empty then don't put in
                    if (cellToString.equals("")) {
                        continue;
                    }

                    String key = getCellToString(
                            sheet
                                    .getRow(0)
                                    .getCell(
                                            cell
                                                    .getColumnIndex()));
                    subject.put(key, cellToString);
                }

                // after subject is done fill subject list


                // 과목코드는 키값의 0 번쨰 인덱스에 있음
                String subjectCode = subject.get(DAPATH.SUBJECT_CODE);


                // 과목코드를 키값으로 설계과목목록에 설계과목 해시테이블을 집어넣음
                subjectList.put(subjectCode, subject);
            }

            subjectListHashMap.put(sheet.getSheetName(), subjectList);
        }
        return subjectListHashMap;
    }

    private static SubjectList getSubjectList(int resourceId) {
        SubjectList subjectList = new SubjectList();
        XSSFWorkbook workbook = null;
        Row row;
        Iterator<Cell> cellIterator;
        Cell cell;
        Subject subject;


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
        } else {
            // handle error
        }


        // from the second it's data
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            cellIterator = row.iterator();
            subject = new Subject();
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();

                String cellToString = getCellToString(cell);
                // if empty then don't put in
                if (cellToString.equals("")) {
                    continue;
                }

                String key = getCellToString(
                        sheet
                                .getRow(0)
                                .getCell(
                                        cell
                                                .getColumnIndex()));
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

    public static SubjectList getRequiredSubjectList(String name) {

        if (isMajorName(name)) {
            return requiredSubjectLists.get(name);
        } else {
            return null;
        }

    }

    public static SubjectList getStartupSubjectList(){ return startupSubjectList; }

    private static boolean isMajorName(String name) {
        switch (name) {
            case COMPUTPER_ABEEK:
            case GLOBAL_SOFTWARE_DOUBLE_MAJOR:
            case GLOBAL_SOFTWARE_MASTERS_CHAINING:
            case GLOBAL_SOFTWARE_OVERSEAS_UNIV:
                return true;

                default: return false;

        }
    }


}
