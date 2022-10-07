import com.nawadata.nfunittestlibrary.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;

public class Main {
    // Your program begins with a call to main().
    // Prints "Hello, World" to the terminal window.
    public static void main(String[] args) throws InterruptedException {
        try {
            ChromeDriver driver = new ChromeDriver();
            WebDriverExtended driverExt = new WebDriverExtended(driver);

            driver.navigate().to("http://homeserver.home:4000/");

            driverExt.getElementExtended().byString("User ID").untilElementInteractable();

            ReactMUIGetter MUIGetter = new ReactMUIGetter(driver);

            MUIGetter.getTextboxFromLabel("User ID").sendText("[USER]");
            MUIGetter.getTextboxFromLabel("Password").sendText("[PASSWORD]");

            driverExt.getElementExtended().byString("Login", Enums.ByOption.Text, "span").untilElementInteractable().click();
            driverExt.getElementExtended().byString("custom-searchbar", Enums.ByOption.ID).untilElementInteractable().sendKeys("Menu TestScript");
            driverExt.getElementExtended().byString("View", Enums.ByOption.Text, "div", true).untilElementInteractable().click();

            driverExt.getElementExtended().byString("Add", Enums.ByOption.Text, "span", true).untilElementInteractable().click();

            MUIGetter.getHTMLFromLabel("RichText").clearText().sendRawText("<h1 id=\"autalon-java-driver-demo\">Autalon Java Driver Demo</h1><h3 id=\"made-by-robin-mauritz-languju\">Made by: Robin Mauritz Languju</h3><p>Yeah, this was a bot. Here&#39;s a random string:</p><p>" + Tools.fastRandStr(16) + "</p>");

            Random rand = new Random();

//            for (int i = 0; i < 200; i++) {
//                var randTime = rand.nextInt(24) + ":" + rand.nextInt(60);
//                MUIGetter.getTimeFromLabel("TimeField").sendText(randTime);
//                var currTime = MUIGetter.getTimeFromLabel("TimeField").getText();
//
//                System.out.println(randTime + " == " + currTime);
//
//                assert randTime == currTime;
//            }

            String randTime = rand.nextInt(24) + ":" + rand.nextInt(60);
            MUIGetter.getTimeFromLabel("TimeField").sendText(randTime);

            MUIGetter.getDateFromLabel("Date").sendText(Tools.randomDateString("01-01-1990", "01-01-2030"));
            MUIGetter.getDateFromLabel("Date Field (RequiredUnique)").sendText(Tools.randomDateString("01-01-1990", "01-01-2030"));

            MUIGetter.getTextboxFromLabel("Number Field (INT)").clearText().sendText(rand.nextInt());
            MUIGetter.getTextboxFromLabel("NumberFielddecimal").clearText().sendText(rand.nextFloat());
            MUIGetter.getTextboxFromLabel("NumberField_float").clearText().sendText(rand.nextFloat());
            MUIGetter.getTextboxFromLabel("NumberField_money").clearText().sendText(rand.nextInt(99999999));
            MUIGetter.getTextboxFromLabel("NumberField_real").clearText().sendText(rand.nextFloat());
            MUIGetter.getTextboxFromLabel("TextFieldRegex").clearText().sendText(rand.nextInt(999999));
            MUIGetter.getTextboxFromLabel("TextField").clearText().sendText("Bot test " + Tools.randomString(8));
            MUIGetter.getTextboxFromLabel("TextFieldUniqeReuquired").clearText().sendText("Bot test " + Tools.randomString(8));
            String randPass = "N@$12WAdda" + Tools.randomString(5, Enums.RandomStringOption.AlphanumericWithSymbols);
            System.out.println("Random Password: " + randPass);
            MUIGetter.getTextboxFromLabel("PasswordField").clearText().sendText(randPass);

            MUIGetter.getRadioFromLabel("Radio").selectRandomElement();

            MUIGetter.getDropdownFromLabel("Dropdown").selectElementFromText("(1) - Banten");
            MUIGetter.getDropdownFromLabel("Dropdown (RequiredUnique)").selectRandomElement();

            driverExt.getElementExtended().byString("Save", Enums.ByOption.Text, "*", true).untilElementInteractable().click();
            driverExt.getElementExtended().byString("Insert", Enums.ByOption.Text, "*", true).untilElementInteractable().click();

            driver.wait(5000);

            driver.quit();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
