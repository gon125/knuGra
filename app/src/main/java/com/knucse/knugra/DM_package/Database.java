package com.knucse.knugra.DM_package;

import android.content.Context;
import android.content.res.Resources;

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


import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.common.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class Database { // 데이터베이스 접근 객체

    private static volatile Database instance;

    private Database() {

    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public static Graduation_Info_List getGraduationInfoList() { // 졸업정보목록을 데이터베이스로부터 가져옴

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection(DAPATH.GRADUATION_INFO_LIST)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Graduation_Info_List gilist = Graduation_Info_List.getInstance();
                                Map a = document.getData();

                                Iterator<String> it = a.keySet().iterator();

                                Graduation_Info gi = new Graduation_Info();
                                gi.setInfo_track(document.getId());
                                while(it.hasNext()) {
                                    String key = it.next();
                                    String value = (String)a.get(key);
                                    Graduation_Info_Item git = new Graduation_Info_Item();
                                    git.setName(key);
                                    git.setContent(value);

                                    gi.add(git);
                                }

                                gilist.add(gi);
                            }
                        }
                    }
                });

        return Graduation_Info_List.getInstance();
    }

    public static SubjectList getDesignSubjectList(Context context) { // 설계과목 가져오기
        return getSubjectList(R.raw.design_subject_list, context);
    }

    public static SubjectList getRequiredSubjectList(Context context) {// 필수과목 가져오기
        return getSubjectList(R.raw.required_subject_list, context);
    }

    private static SubjectList getSubjectList(int resourceId, Context context) {
        SubjectList subjectList = new SubjectList();
        XSSFWorkbook workbook = null;
        Row row;
        Iterator<Cell> cellIterator;
        ArrayList<String> keys = new ArrayList<>();
        Cell cell;
        Subject subject = null;


        InputStream is = context.getResources().openRawResource(resourceId);

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
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();

                String cellToString = "";
                switch (cell.getCellTypeEnum()) {
                    case _NONE:
                        break;
                    case NUMERIC:
                        cellToString = String.valueOf(cell.getNumericCellValue());
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
                String key = keysIterator.next();
                subject = new Subject();
                subject.put(key, cellToString);
            }

            // after subject is done fill subject list


            // 과목코드는 키값의 0 번쨰 인덱스에 있음
            String subjectCode = subject.get(keys.get(0));


            // 과목코드를 키값으로 설계과목목록에 설계과목 해시테이블을 집어넣음
            subjectList.put(subjectCode, subject);
        }

        return subjectList;
    }
}
