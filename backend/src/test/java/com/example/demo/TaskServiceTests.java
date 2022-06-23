package com.example.demo;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/*
 * Da in der Klasse TaskServiceTest die "Business Logik" steckt, denke ich macht es am meisten sin, hier einen Unint test zu machen.
 */



public class TaskServiceTests {
    Task task1 = new Task("Java Lernen", "Immer fleißig tests schreiben!", Status.OPEN, List.of(new Tag("Programming", "#83f783"), new Tag("Learning", "#467f23")));
    Task task2 = new Task("Backend Programmieren", "bla bla bla", Status.IN_PROGRESS);
    Task task3 = new Task("JavaSript Lernen", "React, Next, node,", Status.OPEN);

    TaskRepo taskRepo = Mockito.mock(TaskRepo.class);

    @Test
    void testAddTask() {
        //GIVEN
        TaskService taskService = new TaskService(taskRepo);
        //when
        taskService.saveTask(task1);
        //then
        Mockito.verify(taskRepo).createOrEdit(task1);
    }

    @Test
    void testDeleteTask() {
        //GIVEN
        TaskService taskService = new TaskService(taskRepo);
        //WHEN
        taskService.deleteTask(task1.getId());
        //then
        Mockito.verify(taskRepo).deleteTask(task1.getId());
    }

    @Test
    void testDemoteTask() {
        //GIVEN
        TaskService taskService = new TaskService(taskRepo);
        Task demotedTask2 = new Task("Backend Programmieren", "bla bla bla", Status.OPEN, task2.getId());
        //WHEN
        taskService.demoteTask(task2);
        //THEN
        Mockito.verify(taskRepo).createOrEdit(demotedTask2);
    }

    @Test
    void testGetTask() {
        //GIVEN
        TaskService taskService = new TaskService(taskRepo);
        //WHEN
        when(taskRepo.getTask(task1.getId())).thenReturn(task1);
        Task res = taskService.getTask(task1.getId());
        //THEN
        Assertions.assertThat(res).isEqualTo(task1);
        
    }

    @Test
    void testGetTasks() {
        //GIVEN
        TaskService taskService = new TaskService(taskRepo);
        //WHEN
        when(taskRepo.getTaskList()).thenReturn(List.of( task1, task2, task3));
        List<Task> res = taskService.getTasks();
        //THEN
        Assertions.assertThat(res).containsExactlyInAnyOrder(task1,task2,task3);
    }

    @Test
    void testPromoteTask() {
        //GIVEN
        TaskService taskService = new TaskService(taskRepo);
        Task promotedTask2 = new Task("Backend Programmieren", "bla bla bla", Status.DONE, task2.getId());
        //WHEN
        taskService.promoteTask(task2);
        //THEN
        Mockito.verify(taskRepo).createOrEdit(promotedTask2);
    }

    @Test
    void testEditTask() {
        //GIVEN
        TaskService taskService = new TaskService(taskRepo);
        Task editedTask2 = new Task("Backend Programmieren", "Ich bin editiert", Status.IN_PROGRESS, task2.getId());
        //WHEN
        taskService.saveTask(editedTask2);
        //THEN
        Mockito.verify(taskRepo).createOrEdit(editedTask2);
    }

    @Test 
    void shouldListTags(){
        
    }
}
