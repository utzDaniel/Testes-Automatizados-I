package com.ada.springtestfilmes.ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class HomeTest {

    private WebDriver webDriver;

    @BeforeAll
    public static void setUpWebDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    }

    @Test
    @DisplayName("Deve ser possível inserir 3 atores e cadastrar um filme")
    public void inserirFilme() {
        webDriver = new ChromeDriver();
        

        webDriver.get("http://localhost:8080/create-ator");

        WebElement nome = webDriver.findElement(By.name("nome"));
        nome.sendKeys("ator0");


        WebElement submit = webDriver.findElement(By.className("btn"));
        submit.click();

        webDriver.get("http://localhost:8080/create-ator");

        nome = webDriver.findElement(By.name("nome"));
        nome.sendKeys("ator1");

        submit = webDriver.findElement(By.className("btn"));
        submit.click();

        webDriver.get("http://localhost:8080/create-ator");

        nome = webDriver.findElement(By.name("nome"));
        nome.sendKeys("ator2");

        submit = webDriver.findElement(By.className("btn"));
        submit.click();

        webDriver.get("http://localhost:8080/create");

        nome = webDriver.findElement(By.id("nome"));
        nome.sendKeys("filme1");

        WebElement genero = webDriver.findElement(By.id("genero"));
        Select selectObject = new Select(genero);
        selectObject.selectByVisibleText("Drama");

        WebElement ano = webDriver.findElement(By.id("ano"));
        ano.sendKeys("2000");

        WebElement ator0 = webDriver.findElement(By.id("atores0"));
        selectObject = new Select(ator0);
        selectObject.selectByVisibleText("ator0");

        WebElement ator1 = webDriver.findElement(By.id("atores1"));
        selectObject = new Select(ator1);
        selectObject.selectByVisibleText("ator1");

        WebElement ator2 = webDriver.findElement(By.id("atores2"));
        selectObject = new Select(ator2);
        selectObject.selectByVisibleText("ator2");

        submit = webDriver.findElement(By.className("btn"));
        submit.click();

        String titulo = webDriver.getTitle();

        Assertions.assertEquals("Lista de Filmes", titulo);

        nome = webDriver.findElement(By.name("nome"));
        String valorNome = nome.getText();

        genero = webDriver.findElement(By.name("genero"));
        String valorGenero = genero.getText();

        ano = webDriver.findElement(By.name("ano"));
        String valorAno = ano.getText();

        ator0 = webDriver.findElement(By.xpath("//td[@name='atores']//span[@name='0']"));
        String valorAtor0 = ator0.getText();

        ator1 = webDriver.findElement(By.xpath("//td[@name='atores']//span[@name='1']"));
        String valorAtor1 = ator1.getText();

        ator2 = webDriver.findElement(By.xpath("//td[@name='atores']//span[@name='2']"));
        String valorAtor2 = ator2.getText();

        Assertions.assertEquals("filme1", valorNome);
        Assertions.assertEquals("Drama", valorGenero);
        Assertions.assertEquals("2000", valorAno);
        Assertions.assertEquals("ator0", valorAtor0);
        Assertions.assertEquals("ator1", valorAtor1);
        Assertions.assertEquals("ator2", valorAtor2);

        webDriver.quit();
    }
}
