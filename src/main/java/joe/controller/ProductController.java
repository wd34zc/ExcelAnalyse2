package joe.controller;

import joe.service.InitializeService;
import joe.service.ProductService;
import joe.service.ReadingService;
import joe.util.ExcelUtil;
import joe.util.UrlUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Map;

@Controller
@RequestMapping("score")
public class ProductController {
    @Autowired
    private InitializeService initializeService;
    @Autowired
    private ReadingService readingService;
    @Autowired
    private ProductService productService;

    /**
     * 上传成绩单和模板，生成对应的个人成绩单
     * @param scoreExcel
     * @param templetExcel
     * @param session
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "createTable", method = RequestMethod.POST)
    public String createTable(@RequestParam(value = "scoreExcel") MultipartFile scoreExcel,
                              @RequestParam(value = "templetExcel") MultipartFile templetExcel,
                              HttpSession session, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("接受到请求");
        System.out.println("scoreExcel: " + !scoreExcel.isEmpty());
        System.out.println("templetExcel: " + !templetExcel.isEmpty());

        initializeService.initialize(session);
        readingService.updateExcel(scoreExcel, templetExcel, session);
        readingService.produceIntermediateScore(session);
        readingService.recordScore(session);
        Map<String, Cell> map = readingService.analyseTemplet(session);

        productService.produceTables(map, session);
        productService.compress(session);

        String path = UrlUtil.getProjectPath(session) + "/result.zip";
        download(path, response);

        System.out.println("finish");
        return "success";
    }

    @RequestMapping(value = "getScoreTemplet", method = RequestMethod.GET)
    public String getScoreTemplet(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        String path = UrlUtil.getTempletPath(session) + "/score.xlsx";
        download(path, response);
        return "success";
    }

    @RequestMapping(value = "getTempletTemplet", method = RequestMethod.GET)
    public String getTempletTemplet(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        String path = UrlUtil.getTempletPath(session) + "/templet.xlsx";
        download(path, response);
        return "success";
    }

    /**
     * 下载文件
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void download(String path, HttpServletResponse response) throws ServletException, IOException {

        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        return response;
    }

}
