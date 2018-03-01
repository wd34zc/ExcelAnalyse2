package joe.activity;

import joe.service.InitializeService;
import joe.service.ProductService;
import joe.service.ReadingService;
import joe.util.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class Activity extends AbstractTest{
    @Autowired
    ReadingService readingService;

    @Autowired
    private ProductService productionService;

    @Autowired
    private InitializeService initializeService;

    @Test
    public void demo() throws Exception {
//        initializeService.initialize();
//        readingService.produceIntermediateScore();
//        readingService.recordScore();
//        productionService.produceTables(readingService.analyseModel());
//        productionService.compress();
    }

    @Test
    public void demo2() throws IOException {
//        readingService.checkOriginalScore();
//        readingService.produceIntermediateScore();
    }
}
