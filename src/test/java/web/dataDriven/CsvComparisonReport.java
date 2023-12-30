package web.dataDriven;

import com.github.ngoanh2n.Resources;
import com.github.ngoanh2n.csv.CsvComparator;
import com.github.ngoanh2n.csv.CsvComparisonOptions;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.io.File;

public class CsvComparisonReport {
    @Test
    @Feature("CSV Comparison: Has Diff")
    void hasDiff() {
        File exp = Resources.getPath("CSVFile.csv").toFile();
        File act = Resources.getFile("CSVFile1.csv");

        CsvComparisonOptions options = CsvComparisonOptions
                .builder()
                .selectColumnId(1)
                .build();
        CsvComparator.compare(exp, act, options);
    }

}
