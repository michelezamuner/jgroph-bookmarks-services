package net.slc.jgroph.adapters.web;

import com.github.javafaker.Faker;
import net.slc.jgroph.infrastructure.container.Container;
import net.slc.jgroph.infrastructure.container.ContainerException;
import net.slc.jgroph.adapters.inmemorystorage.ResourceRepository;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class ResourceServletTest
{
    private Faker faker;

    @Before
    public void setUp()
    {
        faker = new Faker();
    }

    @Test
    public void resourceRequestIsRoutedToShowResource()
            throws ServletException, IOException, ContainerException
    {
        final String id = String.valueOf(faker.number().randomNumber());

        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getPathInfo()).thenReturn("/" + id);

        final HttpServletResponse response = mock(HttpServletResponse.class);
        final ResourceController controller = mock(ResourceController.class);

        final Container container = mock(Container.class);
        when(container.make(ResourceController.class)).thenReturn(controller);

        final ResourceServlet servlet = new ResourceServlet(container);
        servlet.service(request, response);
        verify(controller).show(eq(id));
    }

    @Test
    public void resourcePresenterIsBuiltFromTheRealResponse()
            throws ServletException, IOException, ContainerException
    {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getPathInfo()).thenReturn("/");

        final HttpServletResponse response = mock(HttpServletResponse.class);
        final ResourcePresenter presenter = mock(ResourcePresenter.class);

        final Container container = mock(Container.class);
        when(container.make(ResourcePresenter.class, response)).thenReturn(presenter);
        when(container.make(ResourceController.class)).thenReturn(mock(ResourceController.class));

        final ResourceServlet servlet = new ResourceServlet(container);
        servlet.service(request, response);
        verify(container).bind(eq(net.slc.jgroph.application.ResourcePresenter.class), eq(presenter));
    }

    @Test
    public void errorPresenterIsBoundToTheRealResponse()
            throws ServletException, IOException, ContainerException
    {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getPathInfo()).thenReturn("/");

        final HttpServletResponse response = mock(HttpServletResponse.class);
        final ErrorPresenter presenter = mock(ErrorPresenter.class);

        final Container container = mock(Container.class);
        when(container.make(ErrorPresenter.class, response)).thenReturn(presenter);
        when(container.make(ResourceController.class)).thenReturn(mock(ResourceController.class));

        final ResourceServlet servlet = new ResourceServlet(container);
        servlet.service(request, response);
        verify(container).bind(eq(ErrorPresenter.class), eq(presenter));
    }

    @Test
    public void resourceRepositoryIsBound()
            throws ServletException, IOException, ContainerException
    {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getPathInfo()).thenReturn("/");

        final ResourceRepository repository = mock(ResourceRepository.class);
        final ResourceRepositoryData data = mock(ResourceRepositoryData.class);

        final Container container = mock(Container.class);
        when(container.make(ResourceRepositoryData.class)).thenReturn(data);
        when(container.make(ResourceRepository.class, data)).thenReturn(repository);
        when(container.make(ResourceController.class)).thenReturn(mock(ResourceController.class));

        final ResourceServlet servlet = new ResourceServlet(container);
        servlet.service(request, mock(HttpServletResponse.class));
        verify(container).bind(eq(net.slc.jgroph.application.ResourceRepository.class), eq(repository));
    }

    @Test(expected = ServletException.class)
    public void appExceptionsAreConvertedToServletExceptions()
            throws ServletException, IOException
    {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getPathInfo()).thenReturn("/");

        final Container container = mock(Container.class);
        doThrow(ContainerException.class).when(container).bind(any(), any());

        final ResourceServlet servlet = new ResourceServlet(container);
        servlet.service(request, mock(HttpServletResponse.class));
    }
}