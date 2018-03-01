package joe.service;

import joe.dao.StudentDao;
import joe.entities.Student;
import joe.util.ExcelUtil;
import joe.util.UrlUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReadingService {
    @Autowired
    private StudentDao studentDao;

    int rowNum;
    int columnNum;

    /**
     * 获取路径，保存文件
     * @param scoreExcel
     * @param templetExcel
     * @param session
     * @throws IOException
     */
    public void updateExcel(MultipartFile scoreExcel, MultipartFile templetExcel, HttpSession session) throws IOException {
        String updatePath = UrlUtil.getOriginalPath(session);
        updateFile(scoreExcel, updatePath, "score.xlsx");
        updateFile(templetExcel, updatePath, "templet.xlsx");
        return;
    }

    /**
     * 上传文件具体操作
     * @param file
     * @param path
     * @param fileName
     * @throws IOException
     */
    private void updateFile(MultipartFile file, String path, String fileName) throws IOException {
        if (file == null || file.isEmpty()){
            return;
        }
        byte[] fileByte = ExcelUtil.MultipartToByte(file);

        FileOutputStream out = new FileOutputStream(path + "//" + fileName);
        out.write(fileByte);
        out.close();
    }

    /**
     * 生成中间表
     * @throws IOException
     */
    public void produceIntermediateScore(HttpSession session) throws IOException {
        String scorePath = UrlUtil.getOriginalPath(session) + "/score.xlsx";
        String intermediatePath = UrlUtil.getIntermediatePath(session) + "/score.xlsx";
        ExcelUtil.copyFile(scorePath, intermediatePath);
        scorePath = intermediatePath;
        scanTheSource(scorePath);
        return;
    }

    /**
     * 处理原始的源数据
     *
     * @param scorePath
     * @throws IOException
     */
    private void scanTheSource(String scorePath) throws IOException {
        FileInputStream in = new FileInputStream(scorePath);
        Workbook workbook = new XSSFWorkbook(in);
        setRowNumAndColomnNum(workbook);

        eraseBlankCell(workbook);
        eraseSheetFormula(workbook);

        FileOutputStream out = new FileOutputStream(scorePath);
        workbook.write(out);
        out.close();
        workbook.close();
    }

    /**
     * 去除表中的公式，仅保留值
     * @throws IOException
     */
    private void eraseSheetFormula(Workbook workbook) throws IOException {
        Sheet sheet = workbook.getSheet("score");
        Row row = null;
        Cell cell = null;

        for (int i=2; i<rowNum+1; i++){
            row = sheet.getRow(i);
            for (int j=2; j<columnNum; j++){
                cell = row.getCell(j);
                if (cell == null){
                    continue;
                }else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                    ExcelUtil.calculateAgain(workbook, cell);
                    ExcelUtil.eraseFormula(workbook, cell);
                }
            }
        }
    }

    /**
     * 去掉空格
     * @param workbook
     * @throws IOException
     */
    private void eraseBlankCell(Workbook workbook) throws IOException {
        Sheet sheet = workbook.getSheet("score");
        Row row = null;
        Cell cell = null;

        for (int i=2; i < rowNum; i++){
            row = sheet.getRow(i);
            for (int j = 2; j< columnNum; j++){
                cell = row.getCell(j);
                if (cell == null)
                    cell = row.createCell(j);
                switch (cell.getCellTypeEnum()){
                    case BLANK:
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(0);
                        break;
                    case ERROR:
                        System.out.println("这里可能有一个错误" + cell.toString() + " " +  cell.getRowIndex() + " " + cell.getColumnIndex());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 获取excel的行数量和列数量
     * @param workbook
     * @throws IOException
     */
    private void setRowNumAndColomnNum(Workbook workbook) throws IOException {
        Sheet sheet = workbook.getSheet("score");
        columnNum = sheet.getRow(1).getLastCellNum();
        rowNum = sheet.getLastRowNum();
    }

    /**
     * 分析excel，映射到实体类并存进数据库
     * @throws IOException
     */
    @Transactional
    public void recordScore(HttpSession session) throws IOException {
        List<Student> students = analyseMap(readExcel(session));
        students.forEach(s->studentDao.save(s));
    }

    /**
     * 读取中间表成绩
     * @return
     * @throws IOException
     */
    private Map readExcel(HttpSession session) throws IOException {
        String path = UrlUtil.getIntermediatePath(session) + "/score.xlsx";
        Map map = readScore(path, 0);
        System.out.println(map);
        return map;
    }

    /**
     * 读取中间表成绩
     * @param dataPath
     * @param sheetNum
     * @return
     * @throws IOException
     */
    private Map<String, Object> readScore(String dataPath, Integer sheetNum) throws IOException {
        InputStream is = new FileInputStream(dataPath);
        Workbook wb = new XSSFWorkbook(is);
        Sheet sheet = wb.getSheet("score"); // 获取第一个sheet表

        Map<String, Object> resultData = new HashMap<>();
        List<List<Cell>> datas = new ArrayList<>();
        List<Cell> cells = null;

        Row row = null;
        Cell cell = null;
        for (int i = 0; i < rowNum; i++) {
            cells = new ArrayList<>();
            row = sheet.getRow(i);
            for (int j = 0; j < columnNum; j++){
                if (i == 0){
                    resultData.put("title", sheet.getRow(i).getCell(0).toString());
                    break;
                }else if (i == 1){
                    break;
                }else{
                    cell = row.getCell(j);
                    cells.add(cell);
                }
            }
            if (!cells.isEmpty()){
                datas.add(cells);
            }
        }
        resultData.put("data", datas);
        is.close();

        return resultData;
    }

    /**
     * 分析数据，并存入数据库
     * @param excelMap
     * @return
     */
    private List<Student> analyseMap(Map excelMap){
        String title = (String) excelMap.get("title");
        List list = (List) excelMap.get("data");
        List<Student> students = new ArrayList<Student>();
        Student student = null;

        for (Object o : list){
            mappedToStudent((List) o, students);
        }
        return students;
    }

    /**
     * Excel映射到实体类Student
     * @param l
     * @param students
     */
    private void mappedToStudent(List<Cell> l, List<Student> students){
        int i = 0;
        Cell studentIdCell = (Cell) l.get(0);
        Cell nameCell = (Cell) l.get(1);
        //若学号栏和名字栏为空白，则跳过
        if (studentIdCell.getCellTypeEnum().equals(CellType.BLANK) || nameCell.getCellTypeEnum().equals(CellType.BLANK)){
            return;
        }else if (studentIdCell.toString().trim().equals("") || nameCell.toString().trim().equals("")){
            return;
        }
        Student student = new Student();
        student.setStudentId(String.valueOf((int) l.get(i++).getNumericCellValue()));
        student.setName(l.get(i++).toString());

        student.setChinese(l.get(i++).getNumericCellValue());
        student.setChineseClassRanking((int) l.get(i++).getNumericCellValue());
        student.setChineseGradeRanking((int) l.get(i++).getNumericCellValue());

        student.setMaths(l.get(i++).getNumericCellValue());
        student.setMathsClassRanking((int) l.get(i++).getNumericCellValue());
        student.setMathsGradeRanking((int) l.get(i++).getNumericCellValue());

        student.setEnglish(l.get(i++).getNumericCellValue());
        student.setEnglishClassRanking((int) l.get(i++).getNumericCellValue());
        student.setEnglishGradeRanking((int) l.get(i++).getNumericCellValue());

        student.setPhysics(l.get(i++).getNumericCellValue());
        student.setPhysicsClassRanking((int) l.get(i++).getNumericCellValue());
        student.setPhysicsGradeRanking((int) l.get(i++).getNumericCellValue());

        student.setChemistry(l.get(i++).getNumericCellValue());
        student.setChemistryClassRanking((int) l.get(i++).getNumericCellValue());
        student.setChemistryGradeRanking((int) l.get(i++).getNumericCellValue());

        student.setBiology(l.get(i++).getNumericCellValue());
        student.setBiologyClassRanking((int) l.get(i++).getNumericCellValue());
        student.setBiologyGradeRanking((int) l.get(i++).getNumericCellValue());

        student.setSum(l.get(i++).getNumericCellValue());
        student.setSumClassRanking((int) l.get(i++).getNumericCellValue());
        student.setSumGradeRanking((int) l.get(i++).getNumericCellValue());
        students.add(student);
    }

    /**
     * 分析模板中数据填充的位置
     * @return
     * @throws IOException
     */
    public Map<String, Cell> analyseTemplet(HttpSession session) throws IOException {
        String fileName = "templet.xlsx";
        String originalPath = UrlUtil.getOriginalPath(session) + "/" + fileName;
        String interMediatePath = UrlUtil.getIntermediatePath(session) + "/" + fileName;
        ExcelUtil.copyFile(originalPath, interMediatePath);

        Workbook workbook = new XSSFWorkbook(interMediatePath);
        Sheet sheet = workbook.getSheet("templet");
        Map<String, Cell> cellMap = new HashMap<>();
        for (int i = sheet.getTopRow(); i < sheet.getLastRowNum()+1; i++){
            for (Cell cell : sheet.getRow(i)){
                switch (cell.toString()){
                    case "#studentId#":
                        cellMap.put("studentId", cell);
                        break;
                    case "#name#":
                        cellMap.put("name", cell);
                        break;
                    case "#chinese#":
                        cellMap.put("chinese", cell);
                        break;
                    case "#chineseCR#":
                        cellMap.put("chineseCR", cell);
                        break;
                    case "#chineseGR#":
                        cellMap.put("chineseGR", cell);
                        break;
                    case "#maths#":
                        cellMap.put("maths", cell);
                        break;
                    case "#mathsGR#":
                        cellMap.put("mathsGR", cell);
                        break;
                    case "#mathsCR#":
                        cellMap.put("mathsCR", cell);
                        break;
                    case "#english#":
                        cellMap.put("english", cell);
                        break;
                    case "#englishGR#":
                        cellMap.put("englishGR", cell);
                        break;
                    case "#englishCR#":
                        cellMap.put("englishCR", cell);
                        break;
                    case "#physics#":
                        cellMap.put("physics", cell);
                        break;
                    case "#physicsGR#":
                        cellMap.put("physicsGR", cell);
                        break;
                    case "#physicsCR#":
                        cellMap.put("physicsCR", cell);
                        break;
                    case "#chemistry#":
                        cellMap.put("chemistry", cell);
                        break;
                    case "#chemistryGR#":
                        cellMap.put("chemistryGR", cell);
                        break;
                    case "#chemistryCR#":
                        cellMap.put("chemistryCR", cell);
                        break;
                    case "#biology#":
                        cellMap.put("biology", cell);
                        break;
                    case "#biologyGR#":
                        cellMap.put("biologyGR", cell);
                        break;
                    case "#biologyCR#":
                        cellMap.put("biologyCR", cell);
                        break;
                    case "#sum#":
                        cellMap.put("sum", cell);
                        break;
                    case "#sumGR#":
                        cellMap.put("sumGR", cell);
                        break;
                    case "#sumCR#":
                        cellMap.put("sumCR", cell);
                        break;
                    default:
                        break;
                }
            }
        }
        workbook.close();
        return cellMap;
    }
}
