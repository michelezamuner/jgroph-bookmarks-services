package net.slc.jgroph.application;

import net.slc.jgroph.domain.ResourceId;

public class ShowResource
{
    private final ResourcePresenter presenter;
    private final ResourceRepository repository;

    public ShowResource(final ResourcePresenter presenter, final ResourceRepository repository)
    {
        this.presenter = presenter;
        this.repository = repository;
    }

    public void call(final String resourceId)
            throws InvalidResourceIdFormatException, ResourceNotFoundException
    {
        this.presenter.show(this.repository.get(new ResourceId(resourceId)));
    }
}