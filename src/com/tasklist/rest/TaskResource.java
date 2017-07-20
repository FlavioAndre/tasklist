package com.tasklist.rest;

import com.tasklist.data.Task;
import com.tasklist.pagination.PaginatedListWrapper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * REST Service para expor os dados para IU grid.
 *
 * @author Flávio André
 */
@Stateless
@ApplicationPath("/resources")
@Path("tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource extends Application {
    @PersistenceContext
    private EntityManager entityManager;

    private Integer countTasks() {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM Task p");
        return ((Long) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    private List<Task> findTasks(int startPosition, int maxResults, String sortFields, String sortDirections) {
        Query query =
                entityManager.createQuery("SELECT p FROM Task p ORDER BY p." + sortFields + " " + sortDirections);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    private PaginatedListWrapper findTasks(PaginatedListWrapper wrapper) {
        wrapper.setTotalResults(countTasks());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(findTasks(start,
                                    wrapper.getPageSize(),
                                    wrapper.getSortFields(),
                                    wrapper.getSortDirections()));
        return wrapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedListWrapper listTasks(@DefaultValue("1")
                                            @QueryParam("page")
                                            Integer page,
                                            @DefaultValue("id")
                                            @QueryParam("sortFields")
                                            String sortFields,
                                            @DefaultValue("asc")
                                            @QueryParam("sortDirections")
                                            String sortDirections) {
        PaginatedListWrapper paginatedListWrapper = new PaginatedListWrapper();
        paginatedListWrapper.setCurrentPage(page);
        paginatedListWrapper.setSortFields(sortFields);
        paginatedListWrapper.setSortDirections(sortDirections);
        paginatedListWrapper.setPageSize(10);
        return findTasks(paginatedListWrapper);
    }

    @GET
    @Path("{id}")
    public Task getTask(@PathParam("id") Long id) {
        return entityManager.find(Task.class, id);
    }

    @POST
    public Task saveTask(Task Task) {
        if (Task.getId() == null) {
            Task TaskToSave = new Task();
            TaskToSave.setName(Task.getName());
            TaskToSave.setDescription(Task.getDescription());
            TaskToSave.setImageUrl(Task.getImageUrl());
            entityManager.persist(Task);
        } else {
            Task TaskToUpdate = getTask(Task.getId());
            TaskToUpdate.setName(Task.getName());
            TaskToUpdate.setDescription(Task.getDescription());
            TaskToUpdate.setImageUrl(Task.getImageUrl());
            Task = entityManager.merge(TaskToUpdate);
        }

        return Task;
    }

    @DELETE
    @Path("{id}")
    public void deleteTask(@PathParam("id") Long id) {
        entityManager.remove(getTask(id));
    }
}
