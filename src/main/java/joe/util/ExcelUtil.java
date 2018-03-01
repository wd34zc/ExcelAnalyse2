package joe.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
//    public static final String projectPath = (new File("excel/")).getAbsolutePath();
//    public static final String originalPath = (new File("excel/original/")).getAbsolutePath();
//    public static final String intermediatePath = (new File("excel/intermediate/")).getAbsolutePath();
//    public static final String productPath = (new File("excel/product/")).getAbsolutePath();
    public static final String projectPath = ExcelUtil.getProjectPath();
    public static final String originalPath = ExcelUtil.projectPath + "/excel/original/";
    public static final String intermediatePath = ExcelUtil.projectPath + "excel/intermediate/";
    public static final String productPath = ExcelUtil.projectPath + "excel/product/";
    public static HttpSession session = null;


    private static String getProjectPath(){
        System.out.println("3:" + ExcelUtil.session);
        if (session == null){
            return new File("excel").getAbsolutePath();
        }else{
            return ExcelUtil.session.getServletContext().getRealPath("/WEB-INF/excel");
        }
    }

    /**
     * 复制文件
     * @param fromPath
     * @param newPath
     */
    public static void copyFile(String fromPath, String newPath) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(fromPath).getChannel();
            outputChannel = new FileOutputStream(newPath).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /**
     * 去除Excel公式保留值
     * @param wb
     * @param cell
     * @throws IOException
     */
    public static void eraseFormula(Workbook wb, Cell cell) throws IOException {
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(cell);

        switch (cellValue.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                cell.setCellValue(cellValue.getBooleanValue());
                cell.setCellType(CellType.BOOLEAN);
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellValue(cellValue.getNumberValue());
                cell.setCellType(CellType.NUMERIC);
                break;
            case Cell.CELL_TYPE_STRING:
                cell.setCellValue(cellValue.getStringValue());
                cell.setCellType(CellType.STRING);
                break;
            case Cell.CELL_TYPE_BLANK:
                cell.setCellType(CellType.BLANK);
                break;
            case Cell.CELL_TYPE_ERROR:
                cell.setCellType(CellType.ERROR);
                break;

            // CELL_TYPE_FORMULA will never happen
            case Cell.CELL_TYPE_FORMULA:
                break;
        }
    }

    /**
     * 重新计算公式
     * @param workbook
     * @param cell
     */
    public static void calculateAgain(Workbook workbook, Cell cell){
        FormulaEvaluator evaluator = null;
        if (workbook instanceof XSSFWorkbook){
            evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
        }
        evaluator.evaluateFormulaCellEnum(cell);
    }

    /**
     * MultipartFile转化为byte[]流
     * @param file
     * @return
     */
    public static byte[] MultipartToByte(MultipartFile file){
        try {
            return file.getBytes();
        } catch (IOException e) {
            System.out.println("文件转化异常！");
            e.printStackTrace();
            return null;
        }
    }
}
