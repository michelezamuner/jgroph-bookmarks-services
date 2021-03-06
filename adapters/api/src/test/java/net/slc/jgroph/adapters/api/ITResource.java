package net.slc.jgroph.adapters.api;

import net.sourceforge.jwebunit.api.HttpHeader;
import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;

@SuppressWarnings("initialization")
public class ITResource
{
    private WebTester tester;

    @Before
    public void setUp()
    {
        tester = new WebTester();
        // TODO: get URL and port from the environment
        tester.setBaseUrl("http://localhost:8080");
    }

    @Test
    public void requestedResourceIsCorrectlyRetrieved()
    {
        tester.beginAt("/resources/1");
        List<HttpHeader> headers = tester.getResponseHeaders();
        System.out.println("########## HEADERS #########");
        for (HttpHeader header : headers) {
            System.out.println(header.getName() + ": " + header.getValue());
        }
        System.out.println("###################");
        // TODO: use enumeration for status codes
        tester.assertResponseCode(200);
        // TODO: use enumeration for content types
        tester.assertHeaderEquals("Content-Type", "application/json");
        assertEquals("{\n  \"id\": 1,\n  \"title\": \"Title 1\"\n}", tester.getPageSource());
    }
}