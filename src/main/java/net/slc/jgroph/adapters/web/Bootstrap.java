package net.slc.jgroph.adapters.web;

import net.slc.jgroph.infrastructure.container.Container;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Bootstrap implements ServletContextListener
{
    @Override
    public void contextInitialized(final ServletContextEvent event)
    {
        final ResourceServlet servlet = new ResourceServlet(new Container());
        final ServletContext context = event.getServletContext();
        context.addServlet("resources", servlet).addMapping("/resources/*");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent event)
    {

    }
}