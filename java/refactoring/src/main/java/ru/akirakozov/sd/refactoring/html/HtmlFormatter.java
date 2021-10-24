package ru.akirakozov.sd.refactoring.html;

import lombok.SneakyThrows;
import ru.akirakozov.sd.refactoring.entity.Product;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HtmlFormatter {

    @SneakyThrows
    public void setHtml(HttpServletResponse response, String html) {
        response.getWriter().println(html);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }


    public String productToView(String message, Product product) {
        return "<html><body>\n" +
                "<h1>" + message + "</h1>\n" +
                product.getName() + "\t" + product.getPrice() + "</br>\n" +
                "</body></html>";
    }

    public String emptyProductView(String message) {
        return "<html><body>\n" +
                "<h1>" + message + "</h1>\n" +
                "</body></html>";
    }

    public String productsToView(List<Product> products) {
        StringBuilder result = new StringBuilder();
        result.append("<html><body>\n");
        products.forEach(product -> result.append(product.getName()).append("\t").append(product.getPrice()).append("</br>").append("\n"));
        result.append("</body></html>");
        return result.toString();
    }

    public String dataView(String message, long data) {
        return "<html><body>\n" +
                message + "\n" + data + "\n" +
                "</body></html>";
    }

}
