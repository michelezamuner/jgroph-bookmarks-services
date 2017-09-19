package net.slc.jgroph.adapters.web;

import net.slc.jgroph.infrastructure.container.Container;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ResourceServlet extends HttpServlet
{
    private final Container container;

    ResourceServlet(final Container container)
    {
        this.container = container;
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException
    {
        final ResourcePresenter presenter = container.make(ResourcePresenter.class, response);
        container.bind(net.slc.jgroph.application.ResourcePresenter.class, presenter);
        container.bind(ErrorPresenter.class, container.make(ErrorPresenter.class, response));

        final String path = request.getPathInfo();
        if (path != null) {
            container.make(ResourceController.class).show(path.substring(1));
        }
    }
}