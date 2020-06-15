package io.javaclasses.fileHub.web;

import io.javaclasses.filehub.web.itemManipulationProcess.Item;
import io.javaclasses.filehub.web.itemManipulationProcess.ItemManipulationProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ExecutorTest should ")
class ItemManipulationProcessTest {

    private final ItemManipulationProcess process = new ItemManipulationProcess();

    @DisplayName("returns items list when folder id is correct.")
    @Test
    private void getItemsTest() {
        String id = "0";
        List<Item> items = process.getItems(id);
    }

    @DisplayName("not returns items list when folder id is invalid")
    @Test
    private void getItemsWithInvalidIdTest() {
        String id = "-1";
        assertThrows(IllegalArgumentException.class, () -> {
            process.getItems(id);
        }, "should throw exception when id is invalid.");
    }

    @DisplayName("returns folder when folder id is correct.")
    @Test
    private void getFolderTest() {
        String id = "0";
        Item item = process.getFolder(id);
    }

    @DisplayName("not returns folder when folder id is invalid")
    @Test
    private void getFolderWithInvalidIdTest() {
        String id = "-1";
        assertThrows(IllegalArgumentException.class, () -> {
            process.getFolder(id);
        }, "should throw exception when id is invalid.");
    }

    @DisplayName("returns folder that was deleted")
    @Test
    private void removeFolderTest() {
        String id = "0";
        Item item = process.removeFolder(id);
        assertEquals(id, item.id());
    }

    @DisplayName("throws exception if can not remove folder with incorrect id")
    @Test
    private void removeFolderWithInvalidIdTest() {
        String id = "-1";
        assertThrows(IllegalArgumentException.class, () -> {
            process.removeFolder(id);
        }, "should throw exception when id is invalid.");
    }

    @DisplayName("returns folder that was created")
    @Test
    private void createFolderTest() {
        String parentId = "0";
        Item item = process.createFolder(parentId);
        assertEquals(parentId, item.id());
    }

    @DisplayName("throws exception if can not create folder with")
    @Test
    private void createFolderWithInvalidIdTest() {
        String parentId = "-1";
        assertThrows(IllegalArgumentException.class, () -> {
            process.createFolder(parentId);
        }, "should throw exception when id is invalid.");
    }
}
