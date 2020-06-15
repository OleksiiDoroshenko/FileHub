package io.javaclasses.filehub.web.itemManipulationProcess;

import java.util.List;

/**
 * Provides functionality for list items manipulation process.
 */
public class ItemManipulationProcess {
    /**
     * @param id - item's parent id.
     * @return items list with passed as param parent id.
     */
    public List<Item> getItems(String id) {
        return null;
    }

    /**
     * @param id - folder id.
     * @return folder item by its id.
     */
    public Item getFolder(String id) {
        return null;
    }

    /**
     * Removes item by its id.
     *
     * @param id - item id.
     * @return removed item.
     */
    public Item removeFolder(String id) {
        return null;
    }

    /**
     * Creates new folder in the folder with passed as param id.
     *
     * @param parentId - folder id in which new folder should be created.
     * @return created folder.
     */
    public Item createFolder(String parentId) {
        return null;
    }
}
