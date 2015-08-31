import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Task firstTask = new Task("Mow the lawn");
    Task secondTask = new Task("Mow the lawn");
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Task myTask = new Task("Mow the lawn");
    myTask.save();
    assertTrue(Task.all().get(0).equals(myTask));
  }

  @Test
  public void all_savesIntoDatabase_true() {
    Task myTask = new Task("Mow the lawn");
    myTask.save();
    assertEquals(Task.all().get(0).getDescription(), "Mow the lawn");
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("Mow the lawn");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(savedTask.getDescription(), "Mow the lawn");
  }

  @Test
  public void addCategory_addsCategoryToTask() {
    Category myCategory = new Category ("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myTask.addCategory(myCategory);
    Category savedCategory = myTask.getCategories().get(0);
    assertTrue(myCategory.equals(savedCategory));
  }

  @Test
  public void getCategories_returnsAllCategories_ArrayList() {
    Category myCategory = new Category("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myTask.addCategory(myCategory);
    List savedCategories = myTask.getCategories();
    assertEquals(savedCategories.size(), 1);
  }

  @Test
  public void delete_deletesAllTasksAndListsAssociations() {
    Category myCategory = new Category ("Household chores");
    myCategory.save();

    Task myTask = new Task ("Mow the lawn");
    myTask.save();

    myTask.addCategory(myCategory);
    myTask.delete();
    assertEquals(myCategory.getTasks().size(), 0);
  }

  @Test
  public void edit_editTaskinCategoryInDatabase() {
    Category myCategory = new Category ("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myTask.addCategory(myCategory);

    myTask.update("Wash the dishes");
    assertEquals("Wash the dishes", Task.all().get(0).getDescription());
  }

  @Test
  public void markTaskAsDone() {
    Task myTask = new Task("Mow the lawn");
    myTask.save();
    myTask.completed(true);
    assertEquals(myTask.completed(), true);
  }

}
