package net.slc.jgroph.bookmarks_services.adapters.http;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("initialization")
@RunWith(MockitoJUnitRunner.class)
public class ResponseTest
{
    @Rule public final ExpectedException expectedException = ExpectedException.none();

    private final Faker faker = new Faker();
    @Mock private HttpServletResponse servletResponse;
    @Mock private PrintWriter responseWriter;
    @InjectMocks private Response response;

    @Before
    public void setUp()
            throws IOException
    {
        when(servletResponse.getWriter()).thenReturn(responseWriter);
    }

    @Test
    public void correctlySetsContentType()
    {
        response.setContentType(Response.CT_APPLICATION_JSON);
        verify(servletResponse).setHeader("Content-Type", Response.CT_APPLICATION_JSON);
    }

    @Test
    public void correctlyWriteContents()
    {
        final String content = faker.lorem().sentence();
        response.write(content);
        verify(responseWriter).print(content);
    }

    @Test
    public void failsIfCannotGetWriter()
            throws IOException
    {
        expectedException.expect(ResponseException.class);
        when(servletResponse.getWriter()).thenThrow(new IOException());
        response.write(faker.lorem().sentence());
    }
}