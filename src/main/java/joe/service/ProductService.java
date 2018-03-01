package joe.service;

import joe.dao.StudentDao;
import joe.entities.Student;
import joe.util.ExcelUtil;
import joe.util.FileToZip;
import joe.util.UrlUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private StudentDao studentDao;

    /**
     * 生成个人成绩单
     * @param cellMap
     * @throws IOException
     */
    @Transactional
    public void produceTables(Map<String, Cell> cellMap, HttpSession session) throws IOException {
        String templetPath = UrlUtil.getIntermediatePath(session) + "/templet.xlsx";
        String productionPath = UrlUtil.getProductPath(session);
        String path = null;
        XSSFWorkbook workbook = new XSSFWorkbook(templetPath);
        XSSFSheet sheet = workbook.getSheet("score");

        List<Student> students = studentDao.getStudents();
        for (Student student : students){
            path = productionPath + "/" + student.getName() + ".xlsx";

            workbook.setSheetName(0, student.getName());

            setCell(cellMap, sheet, student);

            FileOutputStream stream = new FileOutputStream(path);
            workbook.write(stream);
            stream.close();
        }
        workbook.close();
    }

    /**
     * 替换模板单元格的内容 。
     * @param cellMap
     * @param sheet
     * @param student
     */
    private void setCell(Map<String, Cell> cellMap, XSSFSheet sheet, Student student){
        int row;
        int column;
        Cell cell = null;

        cell = cellMap.get("studentId");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getStudentId());
        }

        cell = cellMap.get("name");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getName());
        }

        cell = cellMap.get("chinese");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getChinese());
        }

        cell = cellMap.get("maths");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getMaths());
        }

        cell = cellMap.get("english");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getEnglish());
        }

        cell = cellMap.get("physics");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getPhysics());
        }

        cell = cellMap.get("chemistry");
        if (cell != null){
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getChemistry());
        }

        cell = cellMap.get("biology");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getBiology());
        }

        cell = cellMap.get("sum");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getSum());
        }

        cell = cellMap.get("chineseCR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getChineseClassRanking());
        }

        cell = cellMap.get("chineseGR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getChineseGradeRanking());
        }

        cell = cellMap.get("mathsCR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getMathsClassRanking());
        }

        cell = cellMap.get("mathsGR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getMathsGradeRanking());
        }

        cell = cellMap.get("englishCR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getEnglishClassRanking());
        }

        cell = cellMap.get("englishGR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getEnglishGradeRanking());
        }

        cell = cellMap.get("physicsCR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getPhysicsClassRanking());
        }

        cell = cellMap.get("physicsGR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getPhysicsGradeRanking());
        }

        cell = cellMap.get("chemistryCR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getChemistryClassRanking());
        }

        cell = cellMap.get("chemistryGR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getChemistryGradeRanking());
        }

        cell = cellMap.get("biologyCR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getBiologyClassRanking());
        }

        cell = cellMap.get("biologyGR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getBiologyGradeRanking());
        }

        cell = cellMap.get("sumCR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getSumClassRanking());
        }

        cell = cellMap.get("sumGR");
        if (cell != null) {
            row = cell.getRowIndex();
            column = cell.getColumnIndex();
            sheet.getRow(row).getCell(column).setCellValue(student.getSumGradeRanking());
        }
    }

    /**
     * 压缩打包
     * @throws Exception
     */
    public void compress(HttpSession session) {
        String dir = UrlUtil.getProductPath(session);
        String zip = UrlUtil.getProjectPath(session);
        String path = "result";
        FileToZip.fileToZip(dir, zip, path);
    }

}
