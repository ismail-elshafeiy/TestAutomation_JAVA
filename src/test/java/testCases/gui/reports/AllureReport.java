package testCases.gui.reports;

public class AllureReport {

    /*
     * Allure Report Installation **
     *
     * download the latest release
     * https://github.com/allure-framework/allure2/releases
     * extract the zip
     * and set the system envronment as allure  to path of the \bin
     * and set the path
     * type in cmd allure --version
     *
     * we add the dependancy from that URL
     * https://docs.qameta.io/allure/#_testng
     *
     *  Run the test cases to generate the allure-results folder or update it
     *
     * go to the path of the allure-results on our project and run the following command
     * allure.bat serve allure-results
     *
     * allure.bat serve - to open the allure server on the web
     *
     *
     * Allure Report Annotations **
     *
     * - To Set the Severity of the TC -
     * @Severity(SeverityLevel.MINOR)
     * @Severity(SeverityLevel.NORMAL)
     * @Severity(SeverityLevel.CRITICAL)
     * @Severity(SeverityLevel.Trivial)
     * @Severity(SeverityLevel.Blocker)
     *
     * - Display the Desc of the TC
     *
     *  @Description("Registration Test Case")
     *
     * - To set the EPIC and User Story Name -
     *
     * @Epic("E2E Scenario")
     * @Story("User Can Order a product successfully")
     *
     * - To set the Link of Whatever -
     *
     * @Link(name = "Website" ,url ="https://demo.nopcommerce.com/")
     * It takes The Name of Something and the URL to it
     * So whenever the User Clicks on it in the Report it Redirect The User to That Something
     *
     *  - To set a Test Body in the execution In The Report
     *
     * @Step ("")
     *
     * - To upload a Attachment to the report
     *
     *  @Attachment
     *
     *
     */
}
