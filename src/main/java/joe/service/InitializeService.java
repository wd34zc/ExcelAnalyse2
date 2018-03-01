package joe.service;

import joe.dao.StudentDao;
import joe.util.ExcelUtil;
import joe.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.File;

@Service
public class InitializeService {

    @Autowired
    private StudentDao studentDao;

    /**
     * 初始化
     */
    @Transactional
    public void initialize(HttpSession session){
        initializeDatabase();
        initializeFile(session);
    }

    /**
     * 初始化数据库
     */
    private void initializeDatabase(){
        studentDao.delete();
    }

    /**
     * 初始化本地文件夹
     */
    private void initializeFile(HttpSession session){
        String originalPath = UrlUtil.getOriginalPath(session);
        String intermediatePath = UrlUtil.getIntermediatePath(session);
        String productPath = UrlUtil.getProductPath(session);
        String result = UrlUtil.getProjectPath(session) + "/result.zip";

        createDirectory(originalPath, intermediatePath, productPath);
        delete(originalPath, intermediatePath, productPath, result);
        return ;
    }

    /**
     * 创建文件夹
     * @param paths
     */
    private void createDirectory(String ... paths ){
        File file = null;
        for (String path : paths){
            file = new File(path);
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
        }
    }

    /**
     * 删除目录下的文件
     * @param paths
     */
    private void delete(String ... paths){
        File dir = null;
        for (String path : paths){
            dir=new File(path);
            if(dir.exists()){
                if (dir.isDirectory()){
                    File[] tmp=dir.listFiles();
                    for(int i=0;i<tmp.length;i++){
                        tmp[i].delete();
                    }
                }else if (dir.isFile()){
                    dir.delete();
                }
            }
        }
    }
}
