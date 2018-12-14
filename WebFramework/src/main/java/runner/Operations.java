package runner;

import util.JavaHttpUrlConnectionReader;

import java.util.List;

public class Operations {

    private static int amountOfCommands;
    private static int failed;
    private static int passed;


    public String open(List<String> value) throws Exception {
        JavaHttpUrlConnectionReader httpUrlConnectionReader = new JavaHttpUrlConnectionReader();
        //Получаем html код страницы в строку
        String html = httpUrlConnectionReader.doHttpUrlConnectionAction(value.get(0), Integer.parseInt(value.get(1)));
        if (httpUrlConnectionReader.getConnection().getResponseCode() == 200) {
            passed++;
        } else failed++;
        amountOfCommands++;
        return html;
    }

    private String getPageBody(String page) {
        //Строка для хранения тела страницы, так как ссылку мы ищем именно там, а не в head страницы
        String body;
        //Длина "<body>", чтоб в строку body попал текст между тегами
        int tagLength = "<body>".length();
        //Получаем тело странциы
        body = page.substring(page.indexOf("<body>") + tagLength, page.indexOf("</body>"));
        return body;
    }

    public boolean checkLinkPresentByHref(String page, List<String> value) {

        amountOfCommands++;
        //Строка для хранения тела страницы, так как ссылку мы ищем именно там, а не в head страницы
        String body = getPageBody(page);
        if (body.contains("href=" + "\"" + value.get(0) + "\"")) {
            passed++;
            return true;
        } else {
            failed++;
            return false;
        }
    }

    public boolean checkLinkPresentByName(String page, List<String> value) {

        amountOfCommands++;
        //Строка для хранения тела страницы, так как ссылку мы ищем именно там, а не в head страницы
        String body = getPageBody(page);
        String beginTag = "<a";
        String endTag = "</a>";
        int startIndex = 0;
        int lastIndex = 0;
        //Перебираем все ссылки и проверем
        while (startIndex != -1 && lastIndex != -1) {

            //Получаем индекс начала тега <a>
            startIndex = body.indexOf(beginTag, lastIndex + endTag.length() - 1);
            //Получаем индекс окончания тега </a>
            lastIndex = body.indexOf(endTag, lastIndex + endTag.length() - 1);

            if (startIndex != -1 && lastIndex != -1) {
                //Проверяем, содержит ли <a...> запрашиваемый текст </a>
                if (body.substring(startIndex, lastIndex).contains(">" + value.get(0))) ;
                {
                    passed++;
                    return true;
                }
            }
        }
        failed++;
        return false;
    }

    public boolean checkPageTitle(String page, List<String> value) {

        amountOfCommands++;
        String title;
        int tagLength = "<title>".length();
        //Получаем title страницы
        title = page.substring(page.indexOf("<title>") + tagLength, page.indexOf("</title>"));
        if (title.equals(value.get(0))) {
            passed++;
            return true;
        } else {
            failed++;
            return false;
        }
    }

    public boolean checkPageContains(String page, List<String> value) {

        amountOfCommands++;
        //Строка для хранения тела страницы, так как ссылку мы ищем именно там, а не в head страницы
        String body = getPageBody(page);
        if (body.contains(value.get(0))) {
            passed++;
            return true;
        } else {
            failed++;
            return false;
        }
    }

    public int getAmountOfCommands() {
        return amountOfCommands;
    }

    public int getPassedTests() {
        return passed;
    }

    public int getFailedTests() {
        return failed;
    }
}

