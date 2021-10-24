package ru.akirakozov.sd.refactoring.html;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.entity.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HtmlFormatterTest {

    private final HtmlFormatter formatter = new HtmlFormatter();

    @Test
    void mapProduct() {
        assertEquals("<html><body>\n" +
                        "<h1>data: </h1>\n" +
                        "abc\t2</br>\n" +
                        "</body></html>",
                formatter.productToView("data: ", new Product("abc", 2)));
    }

    @Test
    void mapEmptyProduct() {
        assertEquals("<html><body>\n" +
                "<h1>data: </h1>\n" +
                "</body></html>", formatter.emptyProductView("data: "));
    }

    @Test
    void mapInfo() {
        assertEquals("<html><body>\n" +
                "data2: \n" +
                "1\n" +
                "</body></html>", formatter.dataView("data2: ", 1));
    }

    @Test
    void mapAllProducts() {
        List<Product> products = List.of(
                new Product("abc", 2),
                new Product("def", 30));

        assertEquals("<html><body>\n" +
                "abc\t2</br>\n" +
                "def\t30</br>\n" +
                "</body></html>", formatter.productsToView(products));
    }

}